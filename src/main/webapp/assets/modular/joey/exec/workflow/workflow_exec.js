layui.use(['table', 'HttpRequest', 'func', 'form', 'soulTable', 'element'], function () {

    let $ = layui.$;
    let table = layui.table;
    let element = layui.element;
    let func = layui.func;
    let form = layui.form;
    let HttpRequest = layui.HttpRequest;

    let buildTimeTrendReq = new HttpRequest(Feng.ctxPath + "/workflow/exec/getBuildTimeTrendReqUrl", 'get');
    let buildTimeTrend = buildTimeTrendReq.start();
    $("#buildTimeTrend").append("<a class='layui-btn layui-btn-primary icon-btn' target='_blank' href='" + buildTimeTrend.data + "'><i class='layui-icon layui-icon-website'></i>构建历史</a>");

    /**
     * 初始化参数
     */
    let CaseWorkflow = {
        tableId: "caseWorkflowTable"
    };

    /**
     * 初始化表格的列
     */
    CaseWorkflow.initColumn = function () {
        return [[
            {field: 'workflowId', sort: true, align: 'center', title: '用例流ID'},
            {field: 'groupId', sort: true, align: 'center', title: '用例组'},
            {field: 'comment', title: '用例流备注', minWidth: 300},
            {
                title: '上次执行', templet: function (d) {
                    if (1 === d.lastStatus) {
                        return "<a target='_blank' href='" + d.lastBuildUrl + "/console'>控制台输出</a>";
                    }
                    if (d.lastBuildNumber) {
                        return "<a target='_blank' href='" + d.lastBuildUrl + "'># " + d.lastBuildNumber + "：</a><a target='_blank' href='" + d.lastBuildUrl + "/allure'>Allure</a>";
                    }
                    return "";
                }
            },
            {
                title: '上次通过', templet: function (d) {
                    if (d.lastSuccessBuildNumber) {
                        return "<a target='_blank' href='" + d.lastSuccessBuildUrl + "'># " + d.lastSuccessBuildNumber + "：</a><a target='_blank' href='" + d.lastSuccessBuildUrl + "/allure'>Allure</a>";
                    }
                    return "";
                }
            },
            {field: 'successCount', title: '通过次数'},
            {field: 'sumCount', title: '执行次数'},
            {align: 'center', toolbar: '#caseWorkflowHtbBar', title: '操作'}
        ]];
    };

    CaseWorkflow.openExecDlg = function (data) {
        func.open({
            title: '执行用例工作流',
            content: Feng.ctxPath + '/view/workflow/exec/start?workflowId=' + data.workflowId,
            tableId: CaseWorkflow.tableId
        });
    }

    /* 用例工作流-点击搜索 */
    form.on("submit(btnSearch)", function (data) {
        console.log(data)
        caseWorkflowTable.reload({where: data.field, page: {curr: 1}})
        return false;
    })

    table.on('tool(caseWorkflowTable)', function (obj) {
        if (obj.event === 'exec') { // 执行
            CaseWorkflow.openExecDlg(obj.data);
        }
    })

    // 渲染表格
    let caseWorkflowTable = table.render({
        elem: '#' + CaseWorkflow.tableId,
        url: Feng.ctxPath + '/workflow/exec/findPage',
        page: true,
        height: "full-110",
        defaultToolbar: [], //默认表格头部右侧工具栏
        cols: CaseWorkflow.initColumn(),
        parseData: Feng.parseData,
        done: function () {
            element.render('progress');
            $(".layui-progress").each(function () {
                let filter = $(this).attr("lay-filter");
                let lastBuildNumber = filter.split('_')[1];
                lastBuildNumberStatus(lastBuildNumber, filter, 0);
            })
        }
    });

    let lastBuildNumberStatus = function (number, filter, percent) {
        let workflowExecReq = new HttpRequest(Feng.ctxPath + "/workflow/exec/getByNumber?number=" + number, 'get', (data) => {
            if (1 === data.data.status) {
                if (percent > 99) {
                    percent = 99;
                } else {
                    if (percent < 30) {
                        percent += Math.floor((Math.random() * 25) + 1);
                    } else if (percent < 50) {
                        percent += Math.floor((Math.random() * 20) + 1);
                    } else if (percent > 90) {
                        percent += Math.floor((Math.random() * 5) + 1);
                    } else if (percent > 70) {
                        percent += Math.floor((Math.random() * 10) + 1);
                    } else if (percent >= 50) {
                        percent += Math.floor((Math.random() * 15) + 1);
                    }
                }
                if (percent > 99) {
                    percent = 99;
                }
                element.progress(filter, percent + '%');
                lastBuildNumberStatus(number, filter, percent);
            } else {
                element.progress(filter, '100%');
                setTimeout(() => {
                    caseWorkflowTable.reload();
                }, 500);
            }
        });
        setTimeout(() => {
            workflowExecReq.start();
        }, 2000);
    }

});