package cn.stylefeng.guns.modular.joey.testcase.service;

import cn.stylefeng.guns.modular.joey.testcase.entity.CaseDetail;
import cn.stylefeng.guns.modular.joey.testcase.pojo.CaseDetailRequest;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用例详情服务
 *
 * @author ycc
 */
public interface CaseDetailService extends IService<CaseDetail> {

    /**
     * 添加用例详情
     *
     * @param caseDetailRequest 用例详情请求
     */
    void add(CaseDetailRequest caseDetailRequest);

    /**
     * 删除用例详情
     *
     * @param caseDetailRequest 用例详情请求
     */
    void del(CaseDetailRequest caseDetailRequest);

    /**
     * 修改用例详情
     *
     * @param caseDetailRequest 用例详情请求
     */
    void edit(CaseDetailRequest caseDetailRequest);

    /**
     * 分页查询用例详情
     *
     * @param caseDetailRequest 用例详情请求
     * @return 分页-用例详情
     */
    PageResult<CaseDetail> findPage(CaseDetailRequest caseDetailRequest);

    /**
     * 查询用例详情集合
     *
     * @param caseDetailRequest 用例详情请求
     * @return 用例详情集合
     */
    List<CaseDetail> findList(CaseDetailRequest caseDetailRequest);

    /**
     * 查询用例详情
     *
     * @param caseDetailRequest 用例详情请求
     * @return 用例详情
     */
    CaseDetail detail(CaseDetailRequest caseDetailRequest);

    /**
     * 根据ID集合查询用例详情集合
     *
     * @param ids id集合
     * @return 用例详情集合
     */
    List<CaseDetail> listByIds(List<Long> ids);

    /**
     * 根据组ID和已有ID集合查询剩余可用用例集合
     *
     * @param groupId 组ID
     * @param ids     已有ID集合
     * @return 用例集合
     */
    List<CaseDetail> listExecutable(Long groupId, List<Long> ids);
}
