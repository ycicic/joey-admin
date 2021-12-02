package cn.stylefeng.guns.modular.joey.mycase.workflow.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.exception.BusinessException;
import cn.stylefeng.guns.modular.joey.mycase.testcase.entity.CaseDetail;
import cn.stylefeng.guns.modular.joey.mycase.testcase.pojo.CaseDetailRequest;
import cn.stylefeng.guns.modular.joey.mycase.testcase.service.CaseDetailService;
import cn.stylefeng.guns.modular.joey.mycase.workflow.entity.CaseWorkflow;
import cn.stylefeng.guns.modular.joey.mycase.workflow.entity.ExecutionOrder;
import cn.stylefeng.guns.modular.joey.mycase.workflow.exception.CaseWorkflowExceptionEnum;
import cn.stylefeng.guns.modular.joey.mycase.workflow.mapper.CaseWorkflowMapper;
import cn.stylefeng.guns.modular.joey.mycase.workflow.pojo.CaseWorkflowRequest;
import cn.stylefeng.guns.modular.joey.mycase.workflow.service.CaseWorkflowService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用例工作流接口实现类
 *
 * @author ycc
 */
@Service
public class CaseWorkflowServiceImpl extends ServiceImpl<CaseWorkflowMapper, CaseWorkflow> implements CaseWorkflowService {

    private CaseDetailService caseDetailService;

    @Autowired
    public void setCaseDetailService(CaseDetailService caseDetailService) {
        this.caseDetailService = caseDetailService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CaseWorkflowRequest caseWorkflowRequest) {
        CaseWorkflow caseWorkflow = new CaseWorkflow();
        BeanUtils.copyProperties(caseWorkflowRequest, caseWorkflow);

        String executionOrderStr = caseWorkflow.getExecutionOrder();
        if (StringUtils.isEmpty(executionOrderStr)) {
            CaseDetailRequest caseDetailRequest = new CaseDetailRequest();
            caseDetailRequest.setGroupId(caseWorkflowRequest.getGroupId());
            List<CaseDetail> detailList = caseDetailService.findList(caseDetailRequest);
            if (ObjectUtil.isEmpty(detailList)) {
                throw new BusinessException(CaseWorkflowExceptionEnum.CASE_GROUP_HASE_NOT_DETAIL);
            }
            List<ExecutionOrder> executionOrderList = detailList.stream().map(detail -> {
                ExecutionOrder executionOrder = new ExecutionOrder();
                executionOrder.setDetailId(detail.getId());
                executionOrder.setOrder(detailList.indexOf(detail) + 1L);
                return executionOrder;
            }).collect(Collectors.toList());
            caseWorkflow.setExecutionOrder(JSONObject.toJSONString(executionOrderList));
        }

        save(caseWorkflow);
    }

    @Override
    public void del(CaseWorkflowRequest caseWorkflowRequest) {
        CaseWorkflow caseWorkflow = getCaseWorkflow(caseWorkflowRequest);
        removeById(caseWorkflow);
    }

    @Override
    public void edit(CaseWorkflowRequest caseWorkflowRequest) {
        CaseWorkflow caseWorkflow = getCaseWorkflow(caseWorkflowRequest);
        BeanUtils.copyProperties(caseWorkflowRequest, caseWorkflow);
        updateById(caseWorkflow);
    }

    @Override
    public void copy(CaseWorkflowRequest caseWorkflowRequest) {
        CaseWorkflow caseWorkflow = getCaseWorkflow(caseWorkflowRequest);
        caseWorkflow.setComment(caseWorkflowRequest.getComment());
        save(caseWorkflow);
    }

    @Override
    public PageResult<CaseWorkflow> findPage(CaseWorkflowRequest caseWorkflowRequest) {
        LambdaQueryWrapper<CaseWorkflow> wrapper = createWrapper(caseWorkflowRequest);
        Page<CaseWorkflow> page = page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public CaseWorkflow detail(CaseWorkflowRequest caseWorkflowRequest) {
        return getCaseWorkflow(caseWorkflowRequest);
    }

    @Override
    public List<CaseDetail> detailExecutionOrder(CaseWorkflowRequest caseWorkflowRequest) {
        List<Long> ids = new ArrayList<>();
        Map<Long, Long> idAndOrderMap = new HashMap<>(64);
        parseIdsAndIdAndOrderMap(caseWorkflowRequest, ids, idAndOrderMap);
        List<CaseDetail> caseDetails = caseDetailService.listByIds(ids);
        caseDetails.forEach(caseDetail -> caseDetail.setOrder(idAndOrderMap.get(caseDetail.getId())));
        return caseDetails;
    }

    @Override
    public List<CaseDetail> listExecutable(CaseWorkflowRequest caseWorkflowRequest) {
        Long groupId = caseWorkflowRequest.getGroupId();
        List<Long> ids = new ArrayList<>();
        Map<Long, Long> idAndOrderMap = new HashMap<>(64);
        parseIdsAndIdAndOrderMap(caseWorkflowRequest, ids, idAndOrderMap);
        List<CaseDetail> caseDetails = caseDetailService.listExecutable(groupId, ids);
        caseDetails.forEach(caseDetail -> caseDetail.setOrder(idAndOrderMap.get(caseDetail.getId())));
        return caseDetails;
    }

    @Override
    public void addExecutable(CaseWorkflowRequest caseWorkflowRequest) {
        CaseWorkflow caseWorkflow = getCaseWorkflow(caseWorkflowRequest);
        List<ExecutionOrder> executionOrders = JSONArray.parseArray(caseWorkflow.getExecutionOrder(), ExecutionOrder.class);
        List<ExecutionOrder> newExecutionOrders = JSONArray.parseArray(caseWorkflowRequest.getExecutionOrder(), ExecutionOrder.class);
        executionOrders.addAll(newExecutionOrders);
        BeanUtils.copyProperties(caseWorkflowRequest, caseWorkflow);
        caseWorkflow.setExecutionOrder(JSONArray.toJSONString(executionOrders));
        updateById(caseWorkflow);
    }

    private void parseIdsAndIdAndOrderMap(CaseWorkflowRequest caseWorkflowRequest, List<Long> ids, Map<Long, Long> idAndOrderMap) {
        CaseWorkflow caseWorkflow = getCaseWorkflow(caseWorkflowRequest);
        String executionOrderStr = caseWorkflow.getExecutionOrder();
        List<ExecutionOrder> executionOrderList = JSONArray.parseArray(executionOrderStr, ExecutionOrder.class);
        ids.addAll(executionOrderList.stream().map(ExecutionOrder::getDetailId).collect(Collectors.toList()));
        idAndOrderMap.putAll(executionOrderList.stream().collect(Collectors.toMap(ExecutionOrder::getDetailId, ExecutionOrder::getOrder)));
    }

    private CaseWorkflow getCaseWorkflow(CaseWorkflowRequest caseWorkflowRequest) {
        CaseWorkflow caseWorkflow = this.getById(caseWorkflowRequest.getId());
        if (ObjectUtil.isEmpty(caseWorkflow)) {
            throw new BusinessException(CaseWorkflowExceptionEnum.CASE_WORKFLOW_NOT_EXISTED);
        }
        return caseWorkflow;
    }

    private LambdaQueryWrapper<CaseWorkflow> createWrapper(CaseWorkflowRequest caseWorkflowRequest) {
        LambdaQueryWrapper<CaseWorkflow> queryWrapper = new LambdaQueryWrapper<>();

        Long groupId = caseWorkflowRequest.getGroupId();
        queryWrapper.eq(ObjectUtil.isNotEmpty(groupId), CaseWorkflow::getGroupId, groupId);

        queryWrapper.orderByAsc(CaseWorkflow::getId);

        return queryWrapper;
    }
}
