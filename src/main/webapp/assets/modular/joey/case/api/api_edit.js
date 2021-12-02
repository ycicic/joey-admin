layui.use(['form', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let id = Feng.getUrlParam("id");
    let detailRequest = new HttpRequest(Feng.ctxPath + "/api/detail?id=" + id, 'get');
    let detail = detailRequest.start();

    form.val('hostForm', detail.data);

    let hostId = detail.data.hostId;
    let hostRequest = new HttpRequest(Feng.ctxPath + "/api/host/detail?id=" + hostId, 'get');
    let host = hostRequest.start().data;

    let url = host.protocol + "://" + host.host;
    if (host.port) {
        url += ":" + host.port;
    }
    $("#url").val(url);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/api/edit", 'post', function () {
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