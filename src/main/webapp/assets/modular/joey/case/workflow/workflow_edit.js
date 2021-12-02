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

    let params = JSON.parse(workflow.data.params);

    $.each(params, (i, json) => {
        if (i === 0) {
            $("#keys").val(json.key)
            $("#values").val(json.value)
        }

        if (i > 0) {
            let paramStr = "<div class=\"layui-inline layui-col-md12\">\n" +
                "                        <div class=\"layui-inline layui-col-md4\">\n" +
                "                            <div class=\"layui-input-block\">\n" +
                "                                <input name=\"keys\" placeholder=\"请输入变量名\" type=\"text\" class=\"layui-input\"\n" +
                "                                       autocomplete=\"off\" value='" + json.key + "'/>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"layui-inline layui-col-md7\">\n" +
                "                            <input name=\"values\" placeholder=\"请输入变量默认值\" type=\"text\" class=\"layui-input\"\n" +
                "                                   autocomplete=\"off\" value='" + json.value + "'/>\n" +
                "                        </div>\n" +
                "                        <div class=\"layui-inline layui-col-md1\">\n" +
                "                            <button class=\"layui-btn layui-btn-danger icon-btn removeParam\">\n" +
                "                                <i class=\"layui-icon layui-icon-reduce-circle\"></i>删除\n" +
                "                            </button>\n" +
                "                        </div>\n" +
                "                    </div>";
            $("#joey-params").append(paramStr);
        }
    })

    let max = 10;
    let x = params.length;

    let paramStr = "<div class=\"layui-inline layui-col-md12\">\n" +
        "                        <div class=\"layui-inline layui-col-md4\">\n" +
        "                            <div class=\"layui-input-block\">\n" +
        "                                <input name=\"keys\" placeholder=\"请输入变量名\" type=\"text\" class=\"layui-input\"\n" +
        "                                       autocomplete=\"off\"/>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "                        <div class=\"layui-inline layui-col-md7\">\n" +
        "                            <input name=\"values\" placeholder=\"请输入变量默认值\" type=\"text\" class=\"layui-input\"\n" +
        "                                   autocomplete=\"off\"/>\n" +
        "                        </div>\n" +
        "                        <div class=\"layui-inline layui-col-md1\">\n" +
        "                            <button class=\"layui-btn layui-btn-danger icon-btn removeParam\">\n" +
        "                                <i class=\"layui-icon layui-icon-reduce-circle\"></i>删除\n" +
        "                            </button>\n" +
        "                        </div>\n" +
        "                    </div>";

    $("#addParam").click(() => {
        if (x < max) {
            $("#joey-params").append(paramStr);
            x++;
        } else {
            Feng.error("最多输入10个变量")
        }
        return false;
    });

    $("body").on("click", ".removeParam", function () {
        if (x > 1) {
            let parentEle = $(this).parent().parent();
            parentEle.remove();
            x--;
        }
        return false;
    });

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

        let keys = $("[name='keys']");
        let values = $("[name='values']");

        let params = [];
        for (let i = 0; i < keys.length; i++) {
            if (values[i].value) {
                params.push({
                    key: keys[i].value,
                    value : values[i].value
                })
            }
        }

        data.field.params = JSON.stringify(params);

        request.set(data.field);
        request.start(true);
    });

});