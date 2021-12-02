package cn.stylefeng.guns.modular.joey.mycase.workflow.controller;

import cn.stylefeng.guns.modular.joey.mycase.workflow.pojo.CaseWorkflowRequest;
import cn.stylefeng.guns.modular.joey.mycase.workflow.service.CaseWorkflowService;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用例工作流管理
 *
 * @author ycc
 */
@RestController
@ApiResource(name = "用例工作流管理")
public class CaseWorkflowController {

    private CaseWorkflowService caseWorkflowService;

    @Autowired
    public void setCaseWorkflowService(CaseWorkflowService caseWorkflowService) {
        this.caseWorkflowService = caseWorkflowService;
    }

    /**
     * 添加用例工作流
     *
     * @param caseWorkflowRequest 用例工作流请求
     * @return 返回结果
     */
    @PostResource(name = "添加用例工作流", path = "/caseWorkflow/add")
    public ResponseData addCaseWorkflow(@RequestBody @Validated(BaseRequest.add.class) CaseWorkflowRequest caseWorkflowRequest) {
        caseWorkflowService.add(caseWorkflowRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除用例工作流
     *
     * @param caseWorkflowRequest 用例工作流请求
     * @return 返回结果
     */
    @PostResource(name = "删除用例工作流", path = "/caseWorkflow/delete")
    public ResponseData deleteCaseWorkflow(@RequestBody @Validated(BaseRequest.delete.class) CaseWorkflowRequest caseWorkflowRequest) {
        caseWorkflowService.del(caseWorkflowRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑用例工作流
     *
     * @param caseWorkflowRequest 用例工作流请求
     * @return 返回结果
     */
    @PostResource(name = "编辑用例工作流", path = "/caseWorkflow/edit")
    public ResponseData editCaseWorkflow(@RequestBody @Validated(BaseRequest.edit.class) CaseWorkflowRequest caseWorkflowRequest) {
        caseWorkflowService.edit(caseWorkflowRequest);
        return new SuccessResponseData();
    }

    @PostResource(name = "复制用例工作流", path = "/caseWorkflow/copy")
    public ResponseData copyCaseWorkflow(@RequestBody @Validated(BaseRequest.edit.class) CaseWorkflowRequest caseWorkflowRequest) {
        caseWorkflowService.copy(caseWorkflowRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看用例工作流详情
     *
     * @param caseWorkflowRequest 用例工作流请求
     * @return 返回结果
     */
    @GetResource(name = "查看用例工作流详情", path = "/caseWorkflow/detail")
    public ResponseData detailCaseWorkflow(@Validated(BaseRequest.detail.class) CaseWorkflowRequest caseWorkflowRequest) {
        return new SuccessResponseData(caseWorkflowService.detail(caseWorkflowRequest));
    }

    /**
     * 查看用例工作流执行顺序详情
     *
     * @param caseWorkflowRequest 用例工作流请求
     * @return 返回结果
     */
    @GetResource(name = "查看用例工作流执行顺序详情", path = "/caseWorkflow/executionOrder/detail")
    public ResponseData detailExecutionOrder(@Validated(BaseRequest.detail.class) CaseWorkflowRequest caseWorkflowRequest) {
        return new SuccessResponseData(caseWorkflowService.detailExecutionOrder(caseWorkflowRequest));
    }

    /**
     * 分页查询用例工作流
     *
     * @param caseWorkflowRequest 用例工作流请求
     * @return 返回结果
     */
    @GetResource(name = "分页查询用例工作流", path = "/caseWorkflow/findPage")
    public ResponseData pageCaseWorkflow(CaseWorkflowRequest caseWorkflowRequest) {
        return new SuccessResponseData(caseWorkflowService.findPage(caseWorkflowRequest));
    }

    @GetResource(name = "查询可增加的用例详情", path = "/caseWorkflow/executable/list")
    public ResponseData listExecutable(CaseWorkflowRequest caseWorkflowRequest) {
        return new SuccessResponseData(caseWorkflowService.listExecutable(caseWorkflowRequest));
    }

    @PostResource(name = "增加用例详情", path = "/caseWorkflow/executable/add")
    public ResponseData addExecutable(@RequestBody @Validated(BaseRequest.edit.class) CaseWorkflowRequest caseWorkflowRequest) {
        caseWorkflowService.addExecutable(caseWorkflowRequest);
        return new SuccessResponseData();
    }
}
