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
        request.set(data.field);
        request.start(true);
    });

});