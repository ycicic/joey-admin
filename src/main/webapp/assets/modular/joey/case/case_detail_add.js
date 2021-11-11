/**
 * 添加用例组
 */
layui.use(['form', 'admin', 'HttpRequest'], function () {
    let $ = layui.$;
    let form = layui.form;
    let admin = layui.admin;
    let HttpRequest = layui.HttpRequest;

    $(function () {
        $.fn.autoHeight = function () {
            function autoHeight(elem) {
                elem.style.height = 'auto';
                elem.scrollTop = 0; //防抖动
                elem.style.height = elem.scrollHeight + 2 + 'px';
            }

            this.each(function () {
                autoHeight(this);
                $(this).on('keyup', function () {
                    autoHeight(this);
                });
            });
        }
        $('div[autoHeight]').autoHeight();
    })

    $('.ace-jsoneditor').eq(0).attr('autoHeight', true);

    let groupId = Feng.getUrlParam('groupId');

    $('#groupId').val(groupId);

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

    let defaultJson = {};
    new JSONEditor(requestDataInputElement, inputOptions, defaultJson);
    let requestDataViewEditor = new JSONEditor(requestDataViewElement, viewOptions, defaultJson);

    $(".jsoneditor-mode-view").addClass("layui-textarea");
    $(".jsoneditor-mode-code").addClass("layui-textarea");

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let request = new HttpRequest(Feng.ctxPath + "/caseDetail/add", 'post', function () {
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