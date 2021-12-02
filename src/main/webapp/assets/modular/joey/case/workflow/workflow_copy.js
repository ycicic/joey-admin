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
                "                                <input name=\"keys\" type=\"text\" class=\"layui-input\"\n" +
                "                                       autocomplete=\"off\" value='" + json.key + "' disabled/>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"layui-inline layui-col-md8\">\n" +
                "                            <input name=\"values\" type=\"text\" class=\"layui-input\"\n" +
                "                                   autocomplete=\"off\" value='" + json.value + "' disabled/>\n" +
                "                        </div>\n" +
                "                    </div>";
            $("#joey-params").append(paramStr);
        }
    })

    let groupReq = new HttpRequest(Feng.ctxPath + "/caseGroup/detail?id=" + groupId, 'get');
    let group = groupReq.start();

    $('#groupComment').val(group.data.comment);
    $('#groupModule').val('[' + groupId + '] ' + group.data.module);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/caseWorkflow/copy", 'post', function () {
            admin.closeThisDialog();
            Feng.success("复制成功！");
            admin.putTempData('formOk', true);
        }, function (data) {
            admin.closeThisDialog();
            Feng.error("添加失败！" + data.message);
        });
        request.set(data.field);
        request.start(true);
    });

});