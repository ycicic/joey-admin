/**
 * 添加用例组
 */
layui.use(['form', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    let groupReq = new HttpRequest(Feng.ctxPath + "/caseGroup/list", 'get');
    let group = groupReq.start();
    let groupList = group.data;

    let groupIdAndCommentMap = {}

    let max = 10;
    let x = 1;

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

    groupList.forEach(group => {
        let option = document.createElement("option");
        option.setAttribute("value", group.id);
        option.innerText = '[' + group.id + '] ' + group.module;
        groupIdAndCommentMap[group.id] = group.comment;
        $('#groupId').get(0).appendChild(option);
    })
    form.render('select');

    form.on('select(groupId)', function (data) {
        $('#groupComment').val(groupIdAndCommentMap[data.value]);
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/caseWorkflow/add", 'post', function () {
            admin.closeThisDialog();
            Feng.success("添加成功！");
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