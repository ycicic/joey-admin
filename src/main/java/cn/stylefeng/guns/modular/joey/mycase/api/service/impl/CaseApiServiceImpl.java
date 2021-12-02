package cn.stylefeng.guns.modular.joey.mycase.api.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.exception.BusinessException;
import cn.stylefeng.guns.modular.joey.mycase.api.entity.CaseApi;
import cn.stylefeng.guns.modular.joey.mycase.api.mapper.CaseApiMapper;
import cn.stylefeng.guns.modular.joey.mycase.api.pojo.CaseApiRequest;
import cn.stylefeng.guns.modular.joey.mycase.api.service.CaseApiService;
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
 * 用例API 服务实现类
 *
 * @author ycc
 */
@Service
public class CaseApiServiceImpl extends ServiceImpl<CaseApiMapper, CaseApi> implements CaseApiService {

    @Override
    public PageResult<CaseApi> findPage(CaseApiRequest caseApiRequest) {
        LambdaQueryWrapper<CaseApi> wrapper = createWrapper(caseApiRequest);
        Page<CaseApi> page = page(PageFactory.defaultPage(), wrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public CaseApi detail(CaseApiRequest caseApiRequest) {
        return getCaseApi(caseApiRequest);
    }

    @Override
    public void add(CaseApiRequest caseApiRequest) {
        CaseApi caseHost = new CaseApi();
        BeanUtils.copyProperties(caseApiRequest, caseHost);
        save(caseHost);
    }

    @Override
    public void del(CaseApiRequest caseApiRequest) {
        CaseApi caseHost = getCaseApi(caseApiRequest);
        removeById(caseHost.getId());
    }

    @Override
    public void edit(CaseApiRequest caseApiRequest) {
        CaseApi caseHost = getCaseApi(caseApiRequest);
        BeanUtils.copyProperties(caseApiRequest, caseHost);
        updateById(caseHost);
    }

    private CaseApi getCaseApi(CaseApiRequest caseApiRequest) {
        CaseApi caseHost = getById(caseApiRequest.getId());
        if (ObjectUtil.isEmpty(caseHost)) {
            throw new BusinessException(CaseExceptionEnum.CASE_GROUP_NOT_EXISTED);
        }
        return caseHost;
    }

    private LambdaQueryWrapper<CaseApi> createWrapper(CaseApiRequest caseApiRequest) {
        LambdaQueryWrapper<CaseApi> queryWrapper = new LambdaQueryWrapper<>();

        Long hostId = caseApiRequest.getHostId();
        String requestMethod = caseApiRequest.getRequestMethod();
        String requestDataType = caseApiRequest.getRequestDataType();
        String api = caseApiRequest.getApi();
        queryWrapper.eq(CaseApi::getHostId, hostId);
        queryWrapper.like(ObjectUtil.isNotEmpty(api), CaseApi::getApi, api);
        queryWrapper.eq(ObjectUtil.isNotEmpty(requestMethod), CaseApi::getRequestMethod, requestMethod);
        queryWrapper.eq(ObjectUtil.isNotEmpty(requestDataType),CaseApi::getRequestDataType, requestDataType);

        queryWrapper.orderByAsc(CaseApi::getId);

        return queryWrapper;
    }
}
