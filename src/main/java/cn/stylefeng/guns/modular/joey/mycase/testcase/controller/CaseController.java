package cn.stylefeng.guns.modular.joey.mycase.testcase.controller;

import cn.stylefeng.guns.modular.joey.mycase.testcase.pojo.CaseDetailRequest;
import cn.stylefeng.guns.modular.joey.mycase.testcase.pojo.CaseGroupRequest;
import cn.stylefeng.guns.modular.joey.mycase.testcase.service.CaseDetailService;
import cn.stylefeng.guns.modular.joey.mycase.testcase.service.CaseGroupService;
import cn.stylefeng.guns.utils.jenkins.JenkinsUtils;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

/**
 * 用例组管理控制器
 *
 * @author ycc
 */
@RestController
@ApiResource(name = "用例管理")
public class CaseController {

    private CaseGroupService caseGroupService;

    private CaseDetailService caseDetailService;

    @Autowired
    public void setCaseGroupService(CaseGroupService caseGroupService) {
        this.caseGroupService = caseGroupService;
    }

    @Autowired
    public void setCaseDetailService(CaseDetailService caseDetailService) {
        this.caseDetailService = caseDetailService;
    }

    /**
     * 添加用例组
     *
     * @param caseGroupRequest 用例组请求
     * @return 返回结果
     */
    @PostResource(name = "添加用例组", path = "/caseGroup/add")
    public ResponseData addCaseGroup(@RequestBody @Validated(BaseRequest.add.class) CaseGroupRequest caseGroupRequest) {
        caseGroupService.add(caseGroupRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除用例组
     *
     * @param caseGroupRequest 用例组请求
     * @return 返回结果
     */
    @PostResource(name = "删除用例组", path = "/caseGroup/delete")
    public ResponseData deleteCaseGroup(@RequestBody @Validated(CaseGroupRequest.delete.class) CaseGroupRequest caseGroupRequest) {
        caseGroupService.del(caseGroupRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑用例组
     *
     * @param caseGroupRequest 用例组请求
     * @return 返回结果
     */
    @PostResource(name = "编辑用例组", path = "/caseGroup/edit")
    public ResponseData editCaseGroup(@RequestBody @Validated(CaseGroupRequest.edit.class) CaseGroupRequest caseGroupRequest) {
        caseGroupService.edit(caseGroupRequest);
        return new SuccessResponseData();
    }

    /**
     * 查看用例组详情
     *
     * @author ycc
     * @date 2020/3/25 14:00
     */
    @GetResource(name = "查看用例组详情", path = "/caseGroup/detail")
    public ResponseData detailCaseGroup(@Validated(CaseGroupRequest.detail.class) CaseGroupRequest caseGroupRequest) {
        return new SuccessResponseData(caseGroupService.detail(caseGroupRequest));
    }

    /**
     * 分页查询用例组列表
     *
     * @param caseGroupRequest 用例组请求
     * @return 返回结果
     */
    @GetResource(name = "分页查询用例组列表", path = "/caseGroup/findPage")
    public ResponseData pageCaseGroup(CaseGroupRequest caseGroupRequest) {
        return new SuccessResponseData(caseGroupService.findPage(caseGroupRequest));
    }

    @GetResource(name = "查看用例详情", path = "/caseDetail/detail")
    public ResponseData detailCaseDetail(@Validated(BaseRequest.detail.class) CaseDetailRequest caseDetailRequest) {
        return new SuccessResponseData(caseDetailService.detail(caseDetailRequest));
    }

    /**
     * 添加用例详情
     *
     * @param caseDetailRequest 用例详情请求
     * @return 返回结果
     */
    @PostResource(name = "添加用例详情", path = "/caseDetail/add")
    public ResponseData addCaseDetail(@RequestBody @Validated(BaseRequest.add.class) CaseDetailRequest caseDetailRequest) {
        caseDetailService.add(caseDetailRequest);
        return new SuccessResponseData();
    }

    /**
     * 删除用例详情
     *
     * @param caseDetailRequest 用例详情请求
     * @return 返回结果
     */
    @PostResource(name = "删除用例详情", path = "/caseDetail/delete")
    public ResponseData deleteCaseDetail(@RequestBody @Validated(CaseGroupRequest.delete.class) CaseDetailRequest caseDetailRequest) {
        caseDetailService.del(caseDetailRequest);
        return new SuccessResponseData();
    }

    /**
     * 编辑用例详情
     *
     * @param caseDetailRequest 用例详情请求
     * @return 返回结果
     */
    @PostResource(name = "编辑用例详情", path = "/caseDetail/edit")
    public ResponseData editCaseDetail(@RequestBody @Validated(CaseGroupRequest.edit.class) CaseDetailRequest caseDetailRequest) {
        caseDetailService.edit(caseDetailRequest);
        return new SuccessResponseData();
    }

    /**
     * 分页查询用例详情列表
     *
     * @param caseDetailRequest 用例组请求
     * @return 返回结果
     */
    @GetResource(name = "分页查询用例详情列表", path = "/caseDetail/findPage")
    public ResponseData pageCaseDetail(CaseDetailRequest caseDetailRequest) {
        return new SuccessResponseData(caseDetailService.findPage(caseDetailRequest));
    }

    /**
     * 查询用例组
     *
     * @return 返回结果
     */
    @GetResource(name = "查询用例组", path = "/caseGroup/list")
    public ResponseData listCaseGroup() {
        return new SuccessResponseData(caseGroupService.findList(new CaseGroupRequest()));
    }

}
