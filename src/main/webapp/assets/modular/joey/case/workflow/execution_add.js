layui.use(['form', 'table', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let table = layui.table;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let CaseExecution = {
        tableId: "caseExecutionTable"
    };

    let id = Feng.getUrlParam('id');
    let max = Feng.getUrlParam('max');

    let workflowReq = new HttpRequest(Feng.ctxPath + "/caseWorkflow/detail?id=" + id, 'get');
    let workflow = workflowReq.start();

    let groupId = workflow.data.groupId;
    form.val('caseExecutionForm', workflow.data);

    let groupReq = new HttpRequest(Feng.ctxPath + "/caseGroup/detail?id=" + groupId, 'get');
    let group = groupReq.start();

    $('#groupComment').val(group.data.comment);
    $('#groupModule').val('[' + groupId + '] ' + group.data.module);

    let columns = [[
        {type: 'checkbox'},
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
        }
    ]];

    let caseExecutionTable = table.render({
        elem: '#caseExecutionTable',
        cols: columns,
        height: 'full-100',
        page: false,
        cellMinWidth: 100,
        data: [],
        drag: false,
        totalRow: true
    });

    let executableReq = new HttpRequest(Feng.ctxPath + "/caseWorkflow/executable/list?id=" + id + "&groupId=" + groupId, 'get');
    let executable = executableReq.start();
    table.reload('caseExecutionTable', {
        data: executable.data
    })

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {

        let checkStatus = table.checkStatus(CaseExecution.tableId);
        let executionOrder = [];
        checkStatus.data.forEach((d) => {
            max = parseInt(max) + 1;
            executionOrder.push({
                order: max + '',
                detailId: d.id
            })
        })

        let executionOrderEditRequest = new HttpRequest(Feng.ctxPath + '/caseWorkflow/executable/add', 'post', function () {
            admin.closeThisDialog();
            Feng.success("保存成功！");
            admin.putTempData('formOk', true);
        }, function (data) {
            admin.closeThisDialog();
            Feng.error("保存失败!" + data.message);
        });
        executionOrderEditRequest.set({
            id: id,
            groupId: groupId,
            executionOrder: JSON.stringify(executionOrder)
        });
        executionOrderEditRequest.start(true);
    });

});