/**
 * 编辑用例组
 */
layui.use(['form', 'admin', 'HttpRequest'], function () {
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let id = Feng.getUrlParam('id');
    let groupReq = new HttpRequest(Feng.ctxPath + "/caseGroup/detail?id=" + id, 'get');
    let group = groupReq.start();

    form.val('caseGroupForm', group.data);

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/caseGroup/edit", 'post', function () {
            Feng.success("修改成功!");
            admin.putTempData('formOk', true);
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.message);
        });
        request.set(data.field);
        request.start(true);
    });

});