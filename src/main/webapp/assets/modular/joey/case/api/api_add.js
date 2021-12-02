layui.use(['form', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let hostId = Feng.getUrlParam("hostId");
    $("#hostId").val(hostId);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/api/add", 'post', function () {
            admin.closeThisDialog();
            Feng.success("添加成功！");
            admin.putTempData('formOk', true);
        }, function (data) {
            Feng.error("添加失败！" + data.message);
        });
        request.set(data.field);
        request.start(true);
    });

});