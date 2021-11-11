package cn.stylefeng.guns.modular.joey.workflow.service;

import cn.stylefeng.guns.modular.joey.testcase.entity.CaseDetail;
import cn.stylefeng.guns.modular.joey.workflow.entity.CaseWorkflow;
import cn.stylefeng.guns.modular.joey.workflow.pojo.CaseWorkflowRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用例工作流服务接口
 *
 * @author ycc
 */
public interface CaseWorkflowService extends IService<CaseWorkflow> {

    /**
     * 添加用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     */
    void add(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 删除用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     */
    void del(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 修改用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     */
    void edit(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 复制用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     */
    void copy(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 分页查询用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     * @return 分页-用例工作流
     */
    PageResult<CaseWorkflow> findPage(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 查询用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     * @return 用例工作流
     */
    CaseWorkflow detail(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 查看用例工作流执行顺序详情
     *
     * @param caseWorkflowRequest 用例详情请求
     * @return 用例详情集合
     */
    List<CaseDetail> detailExecutionOrder(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 查询可增加的用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     * @return 用例详情集合
     */
    List<CaseDetail> listExecutable(CaseWorkflowRequest caseWorkflowRequest);

    /**
     * 增加用例详情
     *
     * @param caseWorkflowRequest 用例详情请求
     */
    void addExecutable(CaseWorkflowRequest caseWorkflowRequest);
}
