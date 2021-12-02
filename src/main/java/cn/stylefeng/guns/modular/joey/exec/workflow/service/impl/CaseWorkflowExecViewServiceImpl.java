package cn.stylefeng.guns.modular.joey.exec.workflow.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.joey.exec.workflow.entity.CaseWorkflowExecView;
import cn.stylefeng.guns.modular.joey.exec.workflow.mapper.CaseWorkflowExecViewMapper;
import cn.stylefeng.guns.modular.joey.exec.workflow.service.CaseWorkflowExecViewService;
import cn.stylefeng.guns.modular.joey.mycase.workflow.pojo.CaseWorkflowRequest;
import cn.stylefeng.guns.utils.jenkins.JenkinsUtils;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用例工作流执行记录服务实现类
 *
 * @author ycc
 */
@Service
public class CaseWorkflowExecViewServiceImpl extends ServiceImpl<CaseWorkflowExecViewMapper, CaseWorkflowExecView> implements CaseWorkflowExecViewService {

    private JenkinsUtils jenkinsUtils;

    @Autowired
    public void setJenkinsUtils(JenkinsUtils jenkinsUtils) {
        this.jenkinsUtils = jenkinsUtils;
    }

    @Override
    public PageResult<CaseWorkflowExecView> findPage(CaseWorkflowRequest caseWorkflowRequest) {
        LambdaQueryWrapper<CaseWorkflowExecView> wrapper = createWrapper(caseWorkflowRequest);
        Page<CaseWorkflowExecView> page = page(PageFactory.defaultPage(), wrapper);
        page.getRecords().forEach(caseWorkflowExecView -> {
            Integer lastBuildNumber = caseWorkflowExecView.getLastBuildNumber();
            Integer lastSuccessBuildNumber = caseWorkflowExecView.getLastSuccessBuildNumber();

            caseWorkflowExecView.setLastBuildUrl(jenkinsUtils.getUrlByJobNumber(lastBuildNumber));
            caseWorkflowExecView.setLastSuccessBuildUrl(jenkinsUtils.getUrlByJobNumber(lastSuccessBuildNumber));
        });
        return PageResultFactory.createPageResult(page);
    }

    private LambdaQueryWrapper<CaseWorkflowExecView> createWrapper(CaseWorkflowRequest caseWorkflowRequest) {
        LambdaQueryWrapper<CaseWorkflowExecView> queryWrapper = new LambdaQueryWrapper<>();

        Long id = caseWorkflowRequest.getId();
        Long groupId = caseWorkflowRequest.getGroupId();
        String comment = caseWorkflowRequest.getComment();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id), CaseWorkflowExecView::getWorkflowId, id);
        queryWrapper.eq(ObjectUtil.isNotEmpty(groupId), CaseWorkflowExecView::getGroupId, groupId);
        queryWrapper.like(ObjectUtil.isNotEmpty(comment), CaseWorkflowExecView::getComment, comment);

        queryWrapper.orderByDesc(CaseWorkflowExecView::getLastBuildNumber);

        return queryWrapper;
    }
}
