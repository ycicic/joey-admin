/**
 * 编辑用例详情
 */
layui.use(['form', 'admin', 'func', 'HttpRequest'], function () {
    let form = layui.form;
    let admin = layui.admin;
    let func = layui.func;
    let HttpRequest = layui.HttpRequest;

    let $ = layui.$;

    let id = Feng.getUrlParam('id');
    let detailReq = new HttpRequest(Feng.ctxPath + "/caseDetail/detail?id=" + id, 'get');
    let detail = detailReq.start();

    form.val('caseDetailForm', detail.data);

    let apiRequest = new HttpRequest(Feng.ctxPath + "/api/detail?id=" + detail.data.hostId, 'get');
    let api = apiRequest.start();
    let hostRequest = new HttpRequest(Feng.ctxPath + "/api/host/detail?id=" + api.data.hostId, 'get');
    let host = hostRequest.start();

    let url = host.data.protocol + "://" + host.data.host;
    if (host.data.port) {
        url += ":" + host.data.port;
    }
    url += "/" + api.data.api;

    $("#url").val(url);

    $("#checkHostBtn").click(() => {
        func.open({
            width: '900px',
            height: '500px',
            title: '选择调用接口',
            content: Feng.ctxPath + '/view/api/select',
            endCallback: function () {
                let data = admin.getTempData('formOk');
                $("#hostId").val(data.hostId);
                $("#url").val(data.url);
            }
        });
        return false;
    })

    let requestDataInputElement = document.getElementById("requestDataInput");
    let requestDataViewElement = document.getElementById("requestDataView");
    const inputOptions = {
        mode: "code",
        history: false,
        mainMenuBar: false,
        navigationBar: false,
        statusBar: true,
        onChangeText(text) {
            try {
                let json = JSON.parse(text);
                requestDataViewEditor.set(json);
                $('#requestData').val(text)
            } catch (e) {

            }
        }
    }
    const viewOptions = {
        mode: "view",
        history: false,
        mainMenuBar: false,
        navigationBar: false,
        statusBar: false,
    }

    let defaultJson = JSON.parse(detail.data.requestData);
    new JSONEditor(requestDataInputElement, inputOptions, defaultJson);
    let requestDataViewEditor = new JSONEditor(requestDataViewElement, viewOptions, defaultJson);

    $(".jsoneditor-mode-view").addClass("layui-textarea");
    $(".jsoneditor-mode-code").addClass("layui-textarea");


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/caseDetail/edit", 'post', function () {
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