package cn.stylefeng.guns.modular.joey.mycase.api.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.exception.BusinessException;
import cn.stylefeng.guns.modular.joey.mycase.api.entity.CaseHost;
import cn.stylefeng.guns.modular.joey.mycase.api.mapper.CaseHostMapper;
import cn.stylefeng.guns.modular.joey.mycase.api.pojo.CaseHostRequest;
import cn.stylefeng.guns.modular.joey.mycase.api.service.CaseHostService;
import cn.stylefeng.guns.modular.joey.mycase.testcase.exception.CaseExceptionEnum;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用例HOST服务实现类
 *
 * @author ycc
 */
@Service
public class CaseHostServiceImpl extends ServiceImpl<CaseHostMapper, CaseHost> implements CaseHostService {
    @Override
    public PageResult<CaseHost> findPage(CaseHostRequest caseHostRequest) {
        LambdaQueryWrapper<CaseHost> wrapper = createWrapper(caseHostRequest);
        Page<CaseHost> page = page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public CaseHost detail(CaseHostRequest caseHostRequest) {
        return getCaseHost(caseHostRequest);
    }

    @Override
    public void add(CaseHostRequest caseHostRequest) {
        CaseHost caseHost = new CaseHost();
        BeanUtils.copyProperties(caseHostRequest, caseHost);
        save(caseHost);
    }

    @Override
    public void del(CaseHostRequest caseHostRequest) {
        CaseHost caseHost = getCaseHost(caseHostRequest);
        removeById(caseHost.getId());
    }

    @Override
    public void edit(CaseHostRequest caseHostRequest) {
        CaseHost caseHost = getCaseHost(caseHostRequest);
        BeanUtils.copyProperties(caseHostRequest, caseHost);
        updateById(caseHost);
    }

    private CaseHost getCaseHost(CaseHostRequest caseHostRequest) {
        CaseHost caseHost = getById(caseHostRequest.getId());
        if (ObjectUtil.isEmpty(caseHost)) {
            throw new BusinessException(CaseExceptionEnum.CASE_GROUP_NOT_EXISTED);
        }
        return caseHost;
    }

    private LambdaQueryWrapper<CaseHost> createWrapper(CaseHostRequest caseHostRequest) {
        LambdaQueryWrapper<CaseHost> queryWrapper = new LambdaQueryWrapper<>();

        String host = caseHostRequest.getHost();

        queryWrapper.like(ObjectUtil.isNotEmpty(host), CaseHost::getHost, host);

        queryWrapper.orderByAsc(CaseHost::getId);

        return queryWrapper;
    }

}
