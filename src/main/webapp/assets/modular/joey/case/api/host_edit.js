layui.use(['form', 'admin', 'HttpRequest'], function () {
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let id = Feng.getUrlParam("id");
    let detailRequest = new HttpRequest(Feng.ctxPath + "/api/host/detail?id=" + id, 'get');
    let detail = detailRequest.start();

    form.val('hostForm', detail.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/api/host/edit", 'post', function () {
            admin.closeThisDialog();
            Feng.success("修改成功！");
            admin.putTempData('formOk', true);
        }, function (data) {
            Feng.error("修改失败！" + data.message);
        });
        request.set(data.field);
        request.start(true);
    });

});