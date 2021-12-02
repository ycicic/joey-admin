package cn.stylefeng.guns.modular.joey.exec.workflow.service.impl;

import cn.stylefeng.guns.modular.joey.exec.workflow.entity.enums.ExecStatus;
import cn.stylefeng.guns.modular.joey.exec.workflow.entity.CaseWorkflowExec;
import cn.stylefeng.guns.modular.joey.exec.workflow.mapper.CaseWorkflowExecMapper;
import cn.stylefeng.guns.modular.joey.exec.workflow.pojo.ExecParamRequest;
import cn.stylefeng.guns.modular.joey.exec.workflow.pojo.ExecWorkflowRequest;
import cn.stylefeng.guns.modular.joey.exec.workflow.service.CaseExecWorkflowService;
import cn.stylefeng.guns.utils.jenkins.JenkinsUtils;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行用例工作流服务实现类
 *
 * @author ycc
 */
@Service
public class CaseExecWorkflowServiceImpl extends ServiceImpl<CaseWorkflowExecMapper, CaseWorkflowExec> implements CaseExecWorkflowService {

    private JenkinsUtils jenkinsUtils;

    @Autowired
    public void setJenkinsUtils(JenkinsUtils jenkinsUtils) {
        this.jenkinsUtils = jenkinsUtils;
    }

    @Override
    public CaseWorkflowExec buildByJenkins(ExecWorkflowRequest execWorkflow) {
        Long id = execWorkflow.getId();
        String branch = execWorkflow.getBranches();
        String params = execWorkflow.getParams();
        List<ExecParamRequest> execParamRequests = JSONArray.parseArray(params, ExecParamRequest.class);
        Map<String, Object> map = new HashMap<>(3);
        map.put("branch", branch);
        map.put("workflowId", id);
        StringBuilder param = new StringBuilder();
        execParamRequests.forEach(execParamRequest -> {
            param.append("-D").append(execParamRequest.getKey()).append("=\"").append(execParamRequest.getValue()).append("\" ");
        });
        map.put("param", param.toString());

        Integer lastBuildNumber = jenkinsUtils.getLastBuildNumber();

        jenkinsUtils.buildWithParameters(map);

        Integer newBuildNumber = null;
        while (null == newBuildNumber) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            Integer number = jenkinsUtils.getLastBuildNumber();
            if (0 != number.compareTo(lastBuildNumber)) {
                newBuildNumber = number;
            }
        }

        return initCaseWorkflowExec(execWorkflow, newBuildNumber);
    }

    @Override
    public List<String> getGitBranches() {
        return jenkinsUtils.getGitBranches();
    }

    public CaseWorkflowExec initCaseWorkflowExec(ExecWorkflowRequest execWorkflow, Integer buildNumber) {
        CaseWorkflowExec caseWorkflowExec = new CaseWorkflowExec();
        caseWorkflowExec.setWorkflowId(execWorkflow.getId());
        caseWorkflowExec.setStatus(ExecStatus.EXECUTION.getValue());
        caseWorkflowExec.setBuildNumber(buildNumber);
        save(caseWorkflowExec);
        return caseWorkflowExec;
    }

    @Override
    @Async
    public void buildMonitor(CaseWorkflowExec caseWorkflowExec) {
        while (ExecStatus.EXECUTION.getValue().equals(caseWorkflowExec.getStatus())) {
            sleepOneSec();
            String resultByJobNumber = jenkinsUtils.getResultByJobNumber(caseWorkflowExec.getBuildNumber());
            ExecStatus execStatus;
            try {
                execStatus = ExecStatus.valueOf(resultByJobNumber);
                caseWorkflowExec.setStatus(execStatus.getValue());
            } catch (Exception ignored) {
            }
        }
        updateById(caseWorkflowExec);
    }

    @Override
    public CaseWorkflowExec getCaseWorkflowExecByNumber(Long number) {
        LambdaQueryWrapper<CaseWorkflowExec> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseWorkflowExec::getBuildNumber, number);
        return getOne(wrapper);
    }

    @Override
    public String getBuildTimeTrendReqUrl() {
        return jenkinsUtils.getBuildTimeTrendReqUrl();
    }

    private void sleepOneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
