/**
 * 添加用例组
 */
layui.use(['jquery'], function () {

    let $ = layui.$;

    $(function(){
        $.fn.autoHeight = function(){
            function autoHeight(elem){
                elem.style.height = 'auto';
                elem.scrollTop = 0; //防抖动
                elem.style.height = elem.scrollHeight + 2 + 'px';
            }
            this.each(function(){
                autoHeight(this);
                $(this).on('keyup', function(){
                    autoHeight(this);
                });
            });
        }
        $('textarea[autoHeight]').autoHeight();
    })

    let requestData = JSON.parse(decodeURIComponent(Feng.getUrlParam('requestData')));
    let preResult = decodeURIComponent(Feng.getUrlParam('preResult'));

    let requestDataElement = document.getElementById('requestData');
    let preResultElement = document.getElementById('preResult');

    preResultElement.value = preResult;
    requestDataElement.value =  JSON.stringify(requestData,null,2);
});