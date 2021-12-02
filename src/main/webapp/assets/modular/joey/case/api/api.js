layui.use(['table', 'HttpRequest', 'func', 'form'], function () {

    let $ = layui.$;
    let table = layui.table;
    let HttpRequest = layui.HttpRequest;
    let func = layui.func;
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
     * 添加HOST对话框
     */
    HostTable.openAddDlg = function () {
        func.open({
            title: '添加HOST',
            content: Feng.ctxPath + '/view/api/host/add',
            tableId: HostTable.tableId
        });
    };

    /**
     * 编辑HOST对话框
     *
     * @param data 点击按钮时候的行数据
     */
    HostTable.openEditDlg = function (data) {
        func.open({
            title: '修改HOST',
            content: Feng.ctxPath + '/view/api/host/edit?id=' + data.id,
            tableId: HostTable.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    HostTable.onDeleteItem = function (data) {
        let operation = function () {
            let request = new HttpRequest(Feng.ctxPath + "/api/host/delete", 'post', function () {
                Feng.success("删除成功!");
                table.reload(HostTable.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            request.set("id", data.id);
            request.start(true);
        };
        Feng.confirm("是否删除?", operation);
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

    table.on('toolbar(hostTable)', function (obj) {
        if (obj.event === 'add') { // 添加
            HostTable.openAddDlg();
        } else {
            if (!hostObj) {
                Feng.error('未选择要操作的HOST!')
            }
            let data = hostObj.data;
            if (obj.event === 'edit') { // 修改
                HostTable.openEditDlg(data);
            } else if (obj.event === 'del') { // 删除
                HostTable.onDeleteItem(data);
            }
        }
    })

    // 渲染表格
    let hostTable = table.render({
        elem: '#' + HostTable.tableId,
        url: Feng.ctxPath + '/api/host/findPage',
        page: true,
        height: "full-110",
        toolbar: '#hostHtbBar',
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

    ApiGroup.openAddDlg = function (data) {
        func.open({
            title: '添加API',
            content: Feng.ctxPath + '/view/api/add?hostId=' + data.id,
            tableId: ApiGroup.tableId
        });
    };

    ApiGroup.openEditDlg = function (data) {
        func.open({
            title: '修改用例详情',
            content: Feng.ctxPath + '/view/api/edit?id=' + data.id,
            tableId: ApiGroup.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ApiGroup.onDeleteItem = function (data) {
        let operation = function () {
            let request = new HttpRequest(Feng.ctxPath + "/api/delete", 'post', function () {
                Feng.success("删除成功!");
                table.reload(ApiGroup.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            request.set("id", data.id);
            request.start(true);
        };
        Feng.confirm("是否删除?", operation);
    };

    /* 用例详情-点击搜索 */
    form.on("submit(apiTbSearch)", function (data) {
        data.field.hostId = hostObj.data.id;
        apiTable.reload({where: data.field, page: {curr: 1}})
        return false;
    });

    table.on('tool(apiTable)', function (obj) {
        let data = obj.data;
        if (obj.event === 'edit') { // 修改
            ApiGroup.openEditDlg(data);
        } else if (obj.event === 'del') { // 删除
            ApiGroup.onDeleteItem(data);
        } else if (obj.event === "requestData") {
            ApiGroup.openRequestDataDlg(data);
        }
    });

    $('#apiTbAdd').click(function () {
        ApiGroup.openAddDlg(hostObj.data);
        return false;
    })

    let apiTable = table.render({
        elem: '#apiTable',
        data: [],
        height: 'full-100',
        page: true,
        cellMinWidth: 100,
        cols: [[
            {field: 'id', align: 'center', sort: true, title: 'ID'},
            {field: 'api', title: 'API', minWidth: 350},
            {field: 'requestMethod', title: '请求方式'},
            {field: 'requestDataType', title: '数据类型'},
            {field: 'comment', title: '备注'},
            {align: 'center', toolbar: '#apiTbBar', title: '操作',minWidth: 120}
        ]],
        parseData: Feng.parseData,
        request: Feng.pageRequest
    });

});