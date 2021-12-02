package cn.stylefeng.guns.modular.joey.mycase.api.service;

import cn.stylefeng.guns.modular.joey.mycase.api.entity.CaseApi;
import cn.stylefeng.guns.modular.joey.mycase.api.pojo.CaseApiRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用例API 服务接口
 *
 * @author ycc
 */
public interface CaseApiService extends IService<CaseApi> {

    /**
     * 分页查询Api
     *
     * @param caseApiRequest Api请求
     * @return 分页-Api
     */
    PageResult<CaseApi> findPage(CaseApiRequest caseApiRequest);

    /**
     * 查询Api详情
     *
     * @param caseApiRequest Api请求
     * @return Api详情
     */
    CaseApi detail(CaseApiRequest caseApiRequest);

    /**
     * 添加Api
     *
     * @param caseApiRequest Api请求
     */
    void add(CaseApiRequest caseApiRequest);

    /**
     * 删除Api
     *
     * @param caseApiRequest Api请求
     */
    void del(CaseApiRequest caseApiRequest);

    /**
     * 修改Api
     *
     * @param caseApiRequest Api请求
     */
    void edit(CaseApiRequest caseApiRequest);


}
