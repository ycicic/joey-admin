layui.use(['form', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let branchesReq = new HttpRequest(Feng.ctxPath + "/git/branches", 'get');
    let branchesList = branchesReq.start().data;

    branchesList.forEach(branches => {
        let option = document.createElement("option");
        option.setAttribute("value", branches);
        option.innerText = branches;
        $('#branches').get(0).appendChild(option);
    });
    form.render('select');

    let id = Feng.getUrlParam("workflowId");
    let workflowReq = new HttpRequest(Feng.ctxPath + "/caseWorkflow/detail?id=" + id, 'get');
    let workflow = workflowReq.start();

    let params = JSON.parse(workflow.data.params);

    $.each(params, (i, json) => {
        let paramStr = "<div class=\"layui-inline layui-col-md12\">\n" +
            "                            <label class=\"layui-form-label\">" + json.key + "</label>\n" +
            "                            <input name=\"keys\" value='" + json.key + "' hidden/>\n" +
            "                            <div class=\"layui-input-block\">\n" +
            "                                <input id=\"values\" value='" + json.value + "' name=\"values\" type=\"text\" class=\"layui-input\"\n" +
            "                                       autocomplete=\"off\"/>\n" +
            "                            </div>\n" +
            "                        </div>";
        $("#joey-params").append(paramStr);
    })

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let loading = top.layer.msg("正在推送至Jenkins构建执行", {icon: 16, shade: 0.3, time: 0});

        let request = new HttpRequest(Feng.ctxPath + "/workflow/exec/jenkins", 'post', function () {
            top.layer.close(loading);
            admin.closeThisDialog();
            Feng.success("推送成功！");
            admin.putTempData('formOk', true);
        }, function (data) {
            top.layer.close(loading);
            admin.closeThisDialog();
            Feng.error("执行失败！" + data.message);
        });

        let keys = $("[name='keys']");
        let values = $("[name='values']");

        let params = [];
        for (let i = 0; i < keys.length; i++) {
            if (values[i].value) {
                params.push({
                    key: keys[i].value,
                    value: values[i].value
                })
            }
        }

        data.field.id = id;
        data.field.params = JSON.stringify(params);

        request.set(data.field);
        request.async = true;
        request.start(true)
        return false;
    });

});