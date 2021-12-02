layui.use(['table', 'HttpRequest', 'func', 'form'], function () {

    let $ = layui.$;
    let table = layui.table;
    let HttpRequest = layui.HttpRequest;
    let func = layui.func;
    let form = layui.form;
    let caseGroupObj;

    /**
     * 初始化参数
     */
    let CaseGroup = {
        tableId: "caseGroupTable"
    };

    let CaseDetail = {
        tableId: "caseDetailTable"
    };

    /**
     * 添加用例组对话框
     */
    CaseGroup.openAddDlg = function () {
        func.open({
            title: '添加用例组',
            content: Feng.ctxPath + '/view/caseGroup/add',
            tableId: CaseGroup.tableId
        });
    };

    /**
     * 编辑用例组对话框
     *
     * @param data 点击按钮时候的行数据
     */
    CaseGroup.openEditDlg = function (data) {
        func.open({
            title: '修改用例组',
            content: Feng.ctxPath + '/view/caseGroup/edit?id=' + data.id,
            tableId: CaseGroup.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CaseGroup.onDeleteItem = function (data) {
        let operation = function () {
            let request = new HttpRequest(Feng.ctxPath + "/caseGroup/delete", 'post', function () {
                Feng.success("删除成功!");
                table.reload(CaseGroup.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            request.set("id", data.id);
            request.start(true);
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 初始化表格的列
     */
    CaseGroup.initColumn = function () {
        return [[
            {field: 'id', sort: true, align: 'center', title: '组ID'},
            {field: 'module', sort: true, title: '模块'},
            // {field: 'comment', title: '备注'},
            {
                field: 'isRun', title: '状态', align: 'center', templet: function (data) {
                    if (data.isRun === 1) {
                        return '<span class="layui-badge layui-badge-green">启用</span>';
                    }
                    return '<span class="layui-badge layui-badge-red">禁用</span>';
                }
            }
        ]];
    };

    /* 用例组-点击搜索 */
    form.on("submit(caseGroupTbSearch)", function (data) {
        caseGroupTable.reload({where: data.field, page: {curr: 1}})
        return false;
    })

    table.on('toolbar(caseGroupTable)', function (obj) {
        if (obj.event === 'add') { // 添加
            CaseGroup.openAddDlg();
        } else {
            if (!caseGroupObj) {
                Feng.error('未选择要操作的用例组!')
            }
            let data = caseGroupObj.data;
            if (obj.event === 'edit') { // 修改
                CaseGroup.openEditDlg(data);
            } else if (obj.event === 'del') { // 删除
                CaseGroup.onDeleteItem(data);
            }
        }
    })

    // 渲染表格
    let caseGroupTable = table.render({
        elem: '#' + CaseGroup.tableId,
        url: Feng.ctxPath + '/caseGroup/findPage',
        page: true,
        height: "full-110",
        toolbar: '#caseGroupHtbBar',
        defaultToolbar: [], //默认表格头部右侧工具栏
        cols: CaseGroup.initColumn(),
        done: function () {
            //加载后自动选中第一行并出发click事件
            $('#caseGroupTable+.layui-table-view .layui-table-body tbody>tr:first').trigger('click');
        },
        parseData: Feng.parseData
    });

    table.on('row(caseGroupTable)', function (obj) {
        caseGroupObj = obj;
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        caseDetailTable.reload({
            where: {groupId: obj.data.id},
            page: {curr: 1},
            url: Feng.ctxPath + '/caseDetail/findPage'
        })
    });

    /**
     * 添加用例组对话框
     */
    CaseDetail.openAddDlg = function (data) {
        func.open({
            title: '添加用例详情',
            content: Feng.ctxPath + '/view/caseDetail/add?groupId=' + data.id,
            tableId: CaseDetail.tableId
        });
    };

    /**
     * 编辑用例组对话框
     *
     * @param data 点击按钮时候的行数据
     */
    CaseDetail.openEditDlg = function (data) {
        func.open({
            title: '修改用例详情',
            content: Feng.ctxPath + '/view/caseDetail/edit?id=' + data.id,
            tableId: CaseDetail.tableId
        });
    };

    /**
     * 查看数据
     *
     * @param data 点击按钮时候的行数据
     */
    CaseDetail.openRequestDataDlg = function (data) {
        func.open({
            title: '查看用例执行数据',
            content: Feng.ctxPath + '/view/caseDetail/requestData?id=' + data.id,
            tableId: CaseDetail.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CaseDetail.onDeleteItem = function (data) {
        let operation = function () {
            let request = new HttpRequest(Feng.ctxPath + "/caseDetail/delete", 'post', function () {
                Feng.success("删除成功!");
                table.reload(CaseDetail.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            request.set("id", data.id);
            request.start(true);
        };
        Feng.confirm("是否删除?", operation);
    };

    /* 用例详情-点击搜索 */
    form.on("submit(caseDetailTbSearch)", function (data) {
        data.field.groupId = caseGroupObj.data.id;
        caseDetailTable.reload({where: data.field, page: {curr: 1}})
        return false;
    });

    table.on('tool(caseDetailTable)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') { // 修改
            CaseDetail.openEditDlg(data);
        } else if (obj.event === 'del') { // 删除
            CaseDetail.onDeleteItem(data);
        } else if (obj.event === "requestData") {
            CaseDetail.openRequestDataDlg(data);
        }
    });

    $('#caseDetailTbAdd').click(function () {
        CaseDetail.openAddDlg(caseGroupObj.data);
        return false;
    })

    let caseDetailTable = table.render({
        elem: '#caseDetailTable',
        data: [],
        height: 'full-100',
        page: true,
        cellMinWidth: 100,
        cols: [[
            {field: 'id', align: 'center', sort: true, title: '用例ID'},
            {field: 'comment', title: '备注说明'},
            {
                field: 'isRun', title: '启用状态', align: 'center', templet: function (data) {
                    if (data.isRun === 1) {
                        return '<span class="layui-badge layui-badge-green">启用</span>';
                    }
                    return '<span class="layui-badge layui-badge-red">禁用</span>';
                }
            },
            {field: 'protocolType', sort: true, title: '执行渠道'},
            {align: 'center', title: '相关数据', toolbar: '#caseDetailRequestDataTbBar'},
            {
                field: 'isCache', title: '是否缓存', align: 'center', templet: function (data) {
                    if (data.isCache === 1) {
                        return '<span class="layui-badge layui-badge-green">是</span>';
                    }
                    return '<span class="layui-badge layui-badge-red">否</span>';
                }
            },
            {align: 'center', toolbar: '#caseDetailTbBar', title: '操作'}
        ]],
        parseData: Feng.parseData,
        request: Feng.pageRequest
    })

});