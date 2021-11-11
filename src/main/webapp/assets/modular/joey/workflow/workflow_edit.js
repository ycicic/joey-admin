/**
 * 添加用例组
 */
layui.use(['form', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let id = Feng.getUrlParam('id');

    let workflowReq = new HttpRequest(Feng.ctxPath + "/caseWorkflow/detail?id=" + id, 'get');
    let workflow = workflowReq.start();

    let groupId = workflow.data.groupId;
    form.val('caseWorkflowForm', workflow.data);

    let groupReq = new HttpRequest(Feng.ctxPath + "/caseGroup/detail?id=" + groupId, 'get');
    let group = groupReq.start();

    $('#groupComment').val(group.data.comment);
    $('#groupModule').val('[' + groupId + '] ' + group.data.module);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/caseWorkflow/edit", 'post', function () {
            admin.closeThisDialog();
            Feng.success("修改成功！");
            admin.putTempData('formOk', true);
        }, function (data) {
            admin.closeThisDialog();
            Feng.error("添加失败！" + data.message);
        });
        request.set(data.field);
        request.start(true);
    });

});