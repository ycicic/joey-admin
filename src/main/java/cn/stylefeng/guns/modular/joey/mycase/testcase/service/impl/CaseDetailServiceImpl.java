package cn.stylefeng.guns.modular.joey.mycase.testcase.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.exception.BusinessException;
import cn.stylefeng.guns.modular.joey.mycase.testcase.exception.CaseExceptionEnum;
import cn.stylefeng.guns.modular.joey.mycase.testcase.entity.CaseDetail;
import cn.stylefeng.guns.modular.joey.mycase.testcase.enums.ProtocolType;
import cn.stylefeng.guns.modular.joey.mycase.testcase.mapper.CaseDetailMapper;
import cn.stylefeng.guns.modular.joey.mycase.testcase.pojo.CaseDetailRequest;
import cn.stylefeng.guns.modular.joey.mycase.testcase.service.CaseDetailService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用例详情服务实现类
 *
 * @author ycc
 */
@Service
public class CaseDetailServiceImpl extends ServiceImpl<CaseDetailMapper, CaseDetail> implements CaseDetailService {


    @Override
    public void add(CaseDetailRequest caseDetailRequest) {
        CaseDetail caseDetail = new CaseDetail();
        BeanUtils.copyProperties(caseDetailRequest, caseDetail);
        save(caseDetail);
    }

    @Override
    public void del(CaseDetailRequest caseDetailRequest) {
        CaseDetail caseDetail = getCaseDetail(caseDetailRequest);
        removeById(caseDetail.getId());
    }

    @Override
    public void edit(CaseDetailRequest caseDetailRequest) {
        CaseDetail caseDetail = getCaseDetail(caseDetailRequest);
        BeanUtils.copyProperties(caseDetailRequest, caseDetail);
        updateById(caseDetail);
    }

    @Override
    public PageResult<CaseDetail> findPage(CaseDetailRequest caseDetailRequest) {
        LambdaQueryWrapper<CaseDetail> wrapper = createWrapper(caseDetailRequest);
        Page<CaseDetail> caseDetailPage = page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(caseDetailPage);
    }

    @Override
    public List<CaseDetail> findList(CaseDetailRequest caseDetailRequest) {
        LambdaQueryWrapper<CaseDetail> wrapper = createWrapper(caseDetailRequest);
        return list(wrapper);
    }

    @Override
    public CaseDetail detail(CaseDetailRequest caseDetailRequest) {
        return getCaseDetail(caseDetailRequest);
    }

    @Override
    public List<CaseDetail> listByIds(List<Long> ids) {
        LambdaQueryWrapper<CaseDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CaseDetail::getId,ids);
        return list(queryWrapper);
    }

    @Override
    public List<CaseDetail> listExecutable(Long groupId, List<Long> ids) {
        LambdaQueryWrapper<CaseDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CaseDetail::getGroupId,groupId);
        queryWrapper.notIn(CaseDetail::getId,ids);
        return list(queryWrapper);
    }

    private CaseDetail getCaseDetail(CaseDetailRequest caseDetailRequest) {
        CaseDetail caseDetail = this.getById(caseDetailRequest.getId());
        if (ObjectUtil.isEmpty(caseDetail)) {
            throw new BusinessException(CaseExceptionEnum.CASE_DETAIL_NOT_EXISTED);
        }
        return caseDetail;
    }

    private LambdaQueryWrapper<CaseDetail> createWrapper(CaseDetailRequest caseDetailRequest) {
        LambdaQueryWrapper<CaseDetail> queryWrapper = new LambdaQueryWrapper<>();
        Long groupId = caseDetailRequest.getGroupId();
        Integer isRun = caseDetailRequest.getIsRun();
        ProtocolType protocolType = caseDetailRequest.getProtocolType();

        queryWrapper.eq(ObjectUtil.isNotEmpty(isRun), CaseDetail::getIsRun, isRun);
        queryWrapper.eq(ObjectUtil.isNotEmpty(protocolType), CaseDetail::getProtocolType, protocolType);
        queryWrapper.eq(CaseDetail::getGroupId, groupId);

        queryWrapper.orderByAsc(CaseDetail::getId);

        return queryWrapper;
    }
}
