layui.use(['table', 'HttpRequest', 'func', 'form', 'soulTable'], function () {

    let $ = layui.$;
    let table = layui.table;
    let HttpRequest = layui.HttpRequest;
    let func = layui.func;
    let form = layui.form;
    let soulTable = layui.soulTable;

    let caseWorkflowObj;

    let executionOrderObj;
    let originalExecutionOrderObj;

    /**
     * 初始化参数
     */
    let CaseWorkflow = {
        tableId: "caseWorkflowTable"
    };

    let ExecutionOrder = {
        tableId: "executionOrderTable"
    };

    /**
     * 添加用例工作流对话框
     */
    CaseWorkflow.openAddDlg = function () {
        func.open({
            title: '添加用例工作流',
            content: Feng.ctxPath + '/view/workflow/add',
            tableId: CaseWorkflow.tableId
        });
    };

    /**
     * 编辑用例工作流对话框
     *
     * @param data 点击按钮时候的行数据
     */
    CaseWorkflow.openEditDlg = function (data) {
        func.open({
            title: '修改用例工作流',
            content: Feng.ctxPath + '/view/workflow/edit?id=' + data.id,
            tableId: CaseWorkflow.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CaseWorkflow.onDeleteItem = function (data) {
        let operation = function () {
            let request = new HttpRequest(Feng.ctxPath + "/caseWorkflow/delete", 'post', function () {
                Feng.success("删除成功!");
                table.reload(CaseWorkflow.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            request.set("id", data.id);
            request.start(true);
        };
        Feng.confirm("是否删除?", operation);
    };

    CaseWorkflow.onCopyItem = function (data) {
        func.open({
            title: '复制用例工作流',
            content: Feng.ctxPath + '/view/workflow/copy?id=' + data.id + '&groupId=' + data.groupId,
            tableId: CaseWorkflow.tableId
        });
    }

    /**
     * 初始化表格的列
     */
    CaseWorkflow.initColumn = function () {
        return [[
            {field: 'id', sort: true, align: 'center', title: 'ID'},
            {field: 'groupId', sort: true, align: 'center', title: '用例组'},
            {field: 'comment', title: '备注'}
        ]];
    };

    /* 用例工作流-点击搜索 */
    form.on("submit(caseWorkflowTbSearch)", function (data) {
        caseWorkflowTable.reload({where: data.field, page: {curr: 1}})
        return false;
    })

    table.on('toolbar(caseWorkflowTable)', function (obj) {
        if (obj.event === 'add') { // 添加
            CaseWorkflow.openAddDlg();
        } else {
            if (!caseWorkflowObj) {
                Feng.error('未选择要操作的用例流!')
            }
            let data = caseWorkflowObj.data;
            if (obj.event === 'edit') { // 修改
                CaseWorkflow.openEditDlg(data);
            } else if (obj.event === 'del') { // 删除
                CaseWorkflow.onDeleteItem(data);
            } else if (obj.event === "copy") { //复制
                CaseWorkflow.onCopyItem(data);
            }
        }
    })

    // 渲染表格
    let caseWorkflowTable = table.render({
        elem: '#' + CaseWorkflow.tableId,
        url: Feng.ctxPath + '/caseWorkflow/findPage',
        page: true,
        height: "full-110",
        toolbar: '#caseWorkflowHtbBar',
        defaultToolbar: [], //默认表格头部右侧工具栏
        cols: CaseWorkflow.initColumn(),
        done: function () {
            //加载后自动选中第一行并出发click事件
            $('#caseWorkflowTable+.layui-table-view .layui-table-body tbody>tr:first').trigger('click');
        },
        parseData: Feng.parseData
    });

    table.on('row(caseWorkflowTable)', function (obj) {
        caseWorkflowObj = obj;
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        ExecutionOrder.loadTable();
    });

    ExecutionOrder.loadTable = function () {
        let executionOrderRequest = new HttpRequest(Feng.ctxPath + '/caseWorkflow/executionOrder/detail?id=' + caseWorkflowObj.data.id + '&groupId=' + caseWorkflowObj.data.groupId, 'get', function (data) {
        }, function (data) {
            Feng.error("查询失败!" + data.message);
        });
        let executionOrderResult = executionOrderRequest.start(false);

        executionOrderResult.data.forEach((data) => {
            data.originalOrder = data.order
        });

        executionOrderResult.data.sort((a, b) => {
            return a.order - b.order
        });

        originalExecutionOrderObj = JSON.parse(JSON.stringify(executionOrderResult.data));
        executionOrderObj = executionOrderResult.data;

        table.reload('executionOrderTable', {
            data: executionOrderResult.data
        });

    }

    let columns = [[
        {
            field: 'order', align: 'center', title: '执行顺序&emsp;/&emsp;原顺序', width: 160, templet: function (data) {
                if (data.order !== data.originalOrder) {
                    return '<span class="layui-badge layui-badge-red">' + data.order + '</span>&emsp;' + '<span class="layui-badge layui-badge-gray">' + data.originalOrder + '</span>';
                } else {
                    return '<span class="layui-badge layui-badge-green">' + data.order + '</span>&emsp;' + '<span class="layui-badge layui-badge-gray">' + data.originalOrder + '</span>';
                }
            }
        },
        {field: 'id', align: 'center', title: '用例ID'},
        {field: 'comment', title: '备注说明'},
        {
            field: 'isRun', title: '启用状态', align: 'center', templet: function (data) {
                if (data.isRun === 1) {
                    return '<span class="layui-badge layui-badge-green">启用</span>';
                }
                return '<span class="layui-badge layui-badge-red">禁用</span>';
            }
        },
        {field: 'protocolType', title: '执行渠道'},
        {
            field: 'isCache', title: '是否缓存', align: 'center', templet: function (data) {
                if (data.isCache === 1) {
                    return '<span class="layui-badge layui-badge-green">是</span>';
                }
                return '<span class="layui-badge layui-badge-red">否</span>';
            }
        },
        {align: 'center', toolbar: '#caseDetailTbBar', title: '操作'}
    ]];

    table.render({
        elem: '#executionOrderTable',
        cols: columns,
        height: 'full-63',
        page: false,
        cellMinWidth: 100,
        data: [],
        toolbar: '#caseWorkflowOrderHtbBar',
        drag: false,
        rowDrag: {
            done: function (obj) {
                let data = obj.cache;
                for (let i = 0; i < data.length; i++) {
                    data[i].order = i + 1 + '';
                }
                executionOrderObj = data;
                table.reload('executionOrderTable', {
                    data: data
                });
            }
        },
        totalRow: true,
        done: function () {
            soulTable.render(this)
        }
    });

    ExecutionOrder.onAddItem = function () {
        func.open({
            title: '用例流-添加用例',
            content: Feng.ctxPath + '/view/workflow/execution/add?id=' + caseWorkflowObj.data.id + '&max=' + executionOrderObj.length,
            endCallback: function () {
                ExecutionOrder.loadTable();
            }
        });
    }

    ExecutionOrder.onSaveOrder = function (data) {
        let executionOrder = [];
        data.forEach(d => {
            executionOrder.push({
                order: d.order,
                detailId: d.id
            })
        });

        let executionOrderEditRequest = new HttpRequest(Feng.ctxPath + '/caseWorkflow/edit', 'post', function () {
            Feng.success("保存成功！");
            ExecutionOrder.loadTable();
        }, function (data) {
            Feng.error("保存失败!" + data.message);
        });
        executionOrderEditRequest.set({
            id: caseWorkflowObj.data.id,
            groupId: caseWorkflowObj.data.groupId,
            executionOrder: JSON.stringify(executionOrder)
        });
        executionOrderEditRequest.start(true);
    };

    table.on('toolbar(executionOrderTable)', function (obj) {
        let data = executionOrderObj;

        let nowData = JSON.parse(JSON.stringify(data));
        nowData.forEach(d => {
            delete d['LAY_TABLE_INDEX'];
        })

        let nowStr = JSON.stringify(nowData)
        let originalExecutionOrderStr = JSON.stringify(originalExecutionOrderObj)
        if (nowStr === originalExecutionOrderStr) {
            if (obj.event === 'save') {
                Feng.error("列表未发生变动！无需保存！");
            } else if (obj.event === 'add') {
                ExecutionOrder.onAddItem(data);
            }
        } else {
            if (obj.event === 'save') {
                ExecutionOrder.onSaveOrder(data);
            } else if (obj.event === 'add') {
                Feng.error("列表发生变动！请先保存或刷新列表！");
            }
        }
    });

    table.on('tool(executionOrderTable)', function (obj) {
        if (obj.event === 'del') {
            let index;
            for (let i = 0; i < executionOrderObj.length; i++) {
                if (executionOrderObj[i].id === obj.data.id) {
                    index = i;
                }
            }
            if (!index) {
                throw new Error("index为空")
            }
            executionOrderObj.splice(index, 1);
            table.reload('executionOrderTable', {
                data: executionOrderObj
            });
        }
    });

});