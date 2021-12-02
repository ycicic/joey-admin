/**
 * 添加用例组
 */
layui.use(['jquery', 'HttpRequest'], function () {

    let $ = layui.$;
    let HttpRequest = layui.HttpRequest;

    let id = Feng.getUrlParam('id');
    let detailReq = new HttpRequest(Feng.ctxPath + "/caseDetail/detail?id=" + id, 'get');
    let detail = detailReq.start();

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
        $('textarea[autoHeight]').autoHeight();
    })

    let requestData = JSON.parse(detail.data.requestData);
    let preResult = detail.data.preResult;

    let requestDataElement = document.getElementById('requestData');
    let preResultElement = document.getElementById('preResult');

    preResultElement.value = preResult;
    requestDataElement.value = JSON.stringify(requestData, null, 2);
});