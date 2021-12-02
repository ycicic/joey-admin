package cn.stylefeng.guns.modular.joey.exec.workflow.service;

import cn.stylefeng.guns.modular.joey.exec.workflow.entity.CaseWorkflowExecView;
import cn.stylefeng.guns.modular.joey.mycase.workflow.pojo.CaseWorkflowRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用例工作流执行记录服务
 *
 * @author ycc
 */
public interface CaseWorkflowExecViewService extends IService<CaseWorkflowExecView> {

    /**
     * 分页查询用例工作流执行记录
     *
     * @param caseWorkflowRequest 用例详情请求
     * @return 分页-用例工作流执行记录
     */
    PageResult<CaseWorkflowExecView> findPage(CaseWorkflowRequest caseWorkflowRequest);


}
