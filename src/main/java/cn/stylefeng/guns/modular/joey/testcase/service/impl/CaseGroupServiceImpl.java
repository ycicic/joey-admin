package cn.stylefeng.guns.modular.joey.testcase.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.exception.BusinessException;
import cn.stylefeng.guns.modular.joey.testcase.exception.CaseExceptionEnum;
import cn.stylefeng.guns.modular.joey.testcase.entity.CaseGroup;
import cn.stylefeng.guns.modular.joey.testcase.mapper.CaseGroupMapper;
import cn.stylefeng.guns.modular.joey.testcase.pojo.CaseGroupRequest;
import cn.stylefeng.guns.modular.joey.testcase.service.CaseGroupService;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.dsctn.api.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用例组服务实现类
 *
 * @author ycc
 */
@Service
public class CaseGroupServiceImpl extends ServiceImpl<CaseGroupMapper, CaseGroup> implements CaseGroupService {

    @Override
    public void add(CaseGroupRequest caseGroupRequest) {
        CaseGroup caseGroup = new CaseGroup();
        BeanUtils.copyProperties(caseGroupRequest, caseGroup);
        save(caseGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(CaseGroupRequest caseGroupRequest) {
        CaseGroup caseGroup = getCaseGroup(caseGroupRequest);
        removeById(caseGroup.getId());
    }

    @Override
    public void edit(CaseGroupRequest caseGroupRequest) {
        CaseGroup caseGroup = getCaseGroup(caseGroupRequest);
        BeanUtils.copyProperties(caseGroupRequest, caseGroup);
        updateById(caseGroup);
    }

    @Override
    public PageResult<CaseGroup> findPage(CaseGroupRequest caseGroupRequest) {
        LambdaQueryWrapper<CaseGroup> wrapper = createWrapper(caseGroupRequest);
        Page<CaseGroup> caseGroupPage = page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(caseGroupPage);
    }

    @Override
    public CaseGroup detail(CaseGroupRequest caseGroupRequest) {
        return queryCaseGroup(caseGroupRequest);
    }

    @Override
    public List<CaseGroup> findList(CaseGroupRequest caseGroupRequest) {
        LambdaQueryWrapper<CaseGroup> wrapper = createWrapper(caseGroupRequest);
        return list(wrapper);
    }

    private CaseGroup getCaseGroup(CaseGroupRequest caseGroupRequest) {
        CaseGroup caseGroup = this.getById(caseGroupRequest.getId());
        if (ObjectUtil.isEmpty(caseGroup)) {
            throw new BusinessException(CaseExceptionEnum.CASE_GROUP_NOT_EXISTED);
        }
        return caseGroup;
    }

    private CaseGroup queryCaseGroup(CaseGroupRequest caseGroupRequest) {
        CaseGroup caseGroup = this.getById(caseGroupRequest.getId());
        if (ObjectUtil.isEmpty(caseGroup)) {
            throw new BusinessException(CaseExceptionEnum.CASE_GROUP_NOT_EXISTED);
        }
        return caseGroup;
    }

    private LambdaQueryWrapper<CaseGroup> createWrapper(CaseGroupRequest caseGroupRequest) {
        LambdaQueryWrapper<CaseGroup> queryWrapper = new LambdaQueryWrapper<>();
        String module = caseGroupRequest.getModule();

        queryWrapper.like(ObjectUtil.isNotEmpty(module), CaseGroup::getModule, module);

        queryWrapper.orderByDesc(CaseGroup::getIsRun);
        queryWrapper.orderByAsc(CaseGroup::getId);

        return queryWrapper;
    }
}
