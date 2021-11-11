package cn.stylefeng.guns.modular.joey.testcase.service;

import cn.stylefeng.guns.modular.joey.testcase.entity.CaseGroup;
import cn.stylefeng.guns.modular.joey.testcase.pojo.CaseGroupRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用例组服务接口
 *
 * @author ycc
 */
public interface CaseGroupService extends IService<CaseGroup> {

    /**
     * 添加用例组
     *
     * @param caseGroupRequest 用例组请求
     */
    void add(CaseGroupRequest caseGroupRequest);

    /**
     * 删除用例组
     *
     * @param caseGroupRequest 用例组请求
     */
    void del(CaseGroupRequest caseGroupRequest);

    /**
     * 修改用例组
     *
     * @param caseGroupRequest 用例组请求
     */
    void edit(CaseGroupRequest caseGroupRequest);

    /**
     * 分页查询用例组
     *
     * @param caseGroupRequest 用例组请求
     * @return 分页-用例组
     */
    PageResult<CaseGroup> findPage(CaseGroupRequest caseGroupRequest);

    /**
     * 查询用例组详情
     *
     * @param caseGroupRequest 用例组请求
     * @return 用例组详情
     */
    CaseGroup detail(CaseGroupRequest caseGroupRequest);

    /**
     * 查询用例组集合
     *
     * @param caseGroupRequest 用例组请求
     * @return 用例组集合
     */
    List<CaseGroup> findList(CaseGroupRequest caseGroupRequest);
}
