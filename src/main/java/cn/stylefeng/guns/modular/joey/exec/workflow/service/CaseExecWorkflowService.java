package cn.stylefeng.guns.modular.joey.exec.workflow.service;

import cn.stylefeng.guns.modular.joey.exec.workflow.entity.CaseWorkflowExec;
import cn.stylefeng.guns.modular.joey.exec.workflow.pojo.ExecWorkflowRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 执行用例流服务
 *
 * @author ycc
 */
public interface CaseExecWorkflowService extends IService<CaseWorkflowExec> {

    /**
     * jenkins构建
     *
     * @param execWorkflow 执行工作流参数
     * @return CaseWorkflowExec
     */
    CaseWorkflowExec buildByJenkins(ExecWorkflowRequest execWorkflow);

    /**
     * 获取GIT分支
     *
     * @return git分支
     */
    List<String> getGitBranches();

    /**
     * 构建监视
     *
     * @param caseWorkflowExec caseWorkflowExec
     */
    void buildMonitor(CaseWorkflowExec caseWorkflowExec);

    /**
     * 根据构建号获取用例工作流执行记录
     *
     * @param number 构建号
     * @return 用例工作流执行记录
     */
    CaseWorkflowExec getCaseWorkflowExecByNumber(Long number);

    /**
     * 获取构建历史URL
     *
     * @return 构建历史URL
     */
    String getBuildTimeTrendReqUrl();
}
