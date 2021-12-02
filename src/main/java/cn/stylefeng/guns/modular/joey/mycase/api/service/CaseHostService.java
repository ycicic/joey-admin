package cn.stylefeng.guns.modular.joey.mycase.api.service;

import cn.stylefeng.guns.modular.joey.mycase.api.entity.CaseHost;
import cn.stylefeng.guns.modular.joey.mycase.api.pojo.CaseHostRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用例HOST服务
 *
 * @author ycc
 */
public interface CaseHostService extends IService<CaseHost> {

    /**
     * 分页查询HOST
     *
     * @param caseHostRequest HOST请求
     * @return 分页-HOST
     */
    PageResult<CaseHost> findPage(CaseHostRequest caseHostRequest);

    /**
     * 查询HOST详情
     *
     * @param caseHostRequest HOST请求
     * @return HOST详情
     */
    CaseHost detail(CaseHostRequest caseHostRequest);

    /**
     * 添加HOST
     *
     * @param caseHostRequest HOST请求
     */
    void add(CaseHostRequest caseHostRequest);

    /**
     * 删除HOST
     *
     * @param caseHostRequest HOST请求
     */
    void del(CaseHostRequest caseHostRequest);

    /**
     * 修改HOST
     *
     * @param caseHostRequest HOST请求
     */
    void edit(CaseHostRequest caseHostRequest);

}
