package cn.stylefeng.guns.modular.joey.exec.workflow.controller;

import cn.stylefeng.guns.modular.joey.exec.workflow.entity.CaseWorkflowExec;
import cn.stylefeng.guns.modular.joey.exec.workflow.pojo.ExecWorkflowRequest;
import cn.stylefeng.guns.modular.joey.exec.workflow.service.CaseExecWorkflowService;
import cn.stylefeng.guns.modular.joey.exec.workflow.service.CaseWorkflowExecViewService;
import cn.stylefeng.guns.modular.joey.mycase.workflow.pojo.CaseWorkflowRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 执行用例流
 *
 * @author ycc
 */
@Slf4j
@RestController
@ApiResource(name = "执行用例流")
public class ExecWorkflowController {

    private CaseExecWorkflowService caseExecWorkflowService;

    private CaseWorkflowExecViewService caseWorkflowExecViewService;

    @Autowired
    public void setCaseExecWorkflowService(CaseExecWorkflowService caseExecWorkflowService) {
        this.caseExecWorkflowService = caseExecWorkflowService;
    }

    @Autowired
    public void setCaseWorkflowExecViewService(CaseWorkflowExecViewService caseWorkflowExecViewService) {
        this.caseWorkflowExecViewService = caseWorkflowExecViewService;
    }

    @GetResource(name = "获取git分支", path = "/git/branches")
    public ResponseData getGitBranches() {
        return new SuccessResponseData(caseExecWorkflowService.getGitBranches());
    }

    @SneakyThrows
    @PostResource(name = "执行用例流-Jenkins", path = "/workflow/exec/jenkins")
    public ResponseData exec(@RequestBody @Validated ExecWorkflowRequest execWorkflow) {
        CaseWorkflowExec caseWorkflowExec = caseExecWorkflowService.buildByJenkins(execWorkflow);
        caseExecWorkflowService.buildMonitor(caseWorkflowExec);
        return new SuccessResponseData();
    }

    @GetResource(name = "分页查询执行用例工作流", path = "/workflow/exec/findPage")
    public ResponseData pageCaseWorkflow(CaseWorkflowRequest caseWorkflowRequest) {
        return new SuccessResponseData(caseWorkflowExecViewService.findPage(caseWorkflowRequest));
    }

    @GetResource(name = "根据构建号获取用例工作流执行记录",path = "/workflow/exec/getByNumber")
    public ResponseData getCaseWorkflowExecByNumber(Long number){
        return new SuccessResponseData(caseExecWorkflowService.getCaseWorkflowExecByNumber(number));
    }

    @GetResource(name = "获取构建历史URL",path = "/workflow/exec/getBuildTimeTrendReqUrl")
    public ResponseData getBuildTimeTrendReqUrl(){
        return new SuccessResponseData(caseExecWorkflowService.getBuildTimeTrendReqUrl());
    }

}
