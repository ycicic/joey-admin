layui.use(['table', 'HttpRequest', 'admin', 'func', 'form'], function () {

    let $ = layui.$;
    let table = layui.table;
    let admin = layui.admin;
    let form = layui.form;
    let hostObj;

    /**
     * 初始化参数
     */
    let HostTable = {
        tableId: "hostTable"
    };

    let ApiGroup = {
        tableId: "apiTable"
    };

    /**
     * 初始化表格的列
     */
    HostTable.initColumn = function () {
        return [[
            {field: 'id', sort: true, align: 'center', title: 'ID'},
            {field: 'protocol', title: '协议'},
            {field: 'host', title: 'HOST', minWidth: 147},
            {field: 'port', title: '端口'},
            {field: 'comment', title: '备注'}
        ]];
    };

    form.on("submit(hostTbSearch)", function (data) {
        hostTable.reload({where: data.field, page: {curr: 1}})
        return false;
    })

    // 渲染表格
    let hostTable = table.render({
        elem: '#' + HostTable.tableId,
        url: Feng.ctxPath + '/api/host/findPage',
        page: true,
        height: "full-110",
        defaultToolbar: [], //默认表格头部右侧工具栏
        cols: HostTable.initColumn(),
        done: function () {
            //加载后自动选中第一行并出发click事件
            $('#hostTable+.layui-table-view .layui-table-body tbody>tr:first').trigger('click');
        },
        parseData: Feng.parseData
    });

    table.on('row(hostTable)', function (obj) {
        hostObj = obj;
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        apiTable.reload({
            where: {hostId: obj.data.id},
            page: {curr: 1},
            url: Feng.ctxPath + '/api/findPage'
        })
    });

    ApiGroup.onSelected = function (data) {
        admin.closeThisDialog();
        let url = hostObj.data.protocol + "://" + hostObj.data.host;
        if (hostObj.data.port) {
            url += ":" + hostObj.data.port;
        }
        admin.putTempData('formOk', {
            hostId: data.id,
            url: url + "/" + data.api
        });
    };

    /* 用例详情-点击搜索 */
    form.on("submit(apiTbSearch)", function (data) {
        data.field.hostId = hostObj.data.id;
        apiTable.reload({where: data.field, page: {curr: 1}})
        return false;
    });

    table.on('tool(apiTable)', function (obj) {
        let data = obj.data;
        if (obj.event === 'select') { // 修改
            ApiGroup.onSelected(data);
        }
    });

    let apiTable = table.render({
        elem: '#apiTable',
        data: [],
        height: 'full-100',
        page: true,
        cellMinWidth: 100,
        cols: [[
            {field: 'id', align: 'center', sort: true, title: 'ID'},
            {field: 'api', title: 'API', minWidth: 330},
            {field: 'requestMethod', title: '请求方式'},
            {field: 'requestDataType', title: '数据类型'},
            {field: 'comment', title: '备注'},
            {align: 'center', toolbar: '#apiTbBar'}
        ]],
        parseData: Feng.parseData,
        request: Feng.pageRequest
    });

});