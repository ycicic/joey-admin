package cn.stylefeng.guns.modular.joey.exec.workflow.controller;

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * 执行工作流页面
 *
 * @author ycc
 */
@Controller
@ApiResource(name = "执行工作流页面")
public class ExecWorkflowViewController {

    @GetResource(name = "执行工作流页面-首页", path = "/view/workflow/exec")
    public String execWorkflowIndex() {
        return "/modular/joey/exec/workflow/workflow_exec.html";
    }

    @GetResource(name = "执行工作流页面-执行", path = "/view/workflow/exec/start")
    public String execWorkflowStart() {
        return "/modular/joey/exec/workflow/workflow_exec_start.html";
    }

}
