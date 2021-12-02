package cn.stylefeng.guns.modular.joey.mycase.workflow.controller;

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * 用例工作流管理界面
 *
 * @author ycc
 */
@Controller
@ApiResource(name = "用例工作流管理界面")
public class CaseWorkflowViewController {

    @GetResource(name = "用例工作流管理-首页", path = "/view/workflow")
    public String caseWorkflowIndex() {
        return "/modular/joey/case/workflow/workflow.html";
    }

    @GetResource(name = "用例工作流管理-新增", path = "/view/workflow/add")
    public String caseWorkflowAdd() {
        return "/modular/joey/case/workflow/workflow_add.html";
    }

    @GetResource(name = "用例工作流管理-编辑", path = "/view/workflow/edit")
    public String caseWorkflowEdit() {
        return "/modular/joey/case/workflow/workflow_edit.html";
    }

    @GetResource(name = "用例工作流-复制", path = "/view/workflow/copy")
    public String caseWorkflowCopy() {
        return "/modular/joey/case/workflow/workflow_copy.html";
    }

    @GetResource(name = "用例工作流-添加用例",path = "/view/workflow/execution/add")
    public String executionAdd() {
        return "/modular/joey/case/workflow/execution_add.html";
    }
}
