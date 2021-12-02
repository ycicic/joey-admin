package cn.stylefeng.guns.modular.joey.mycase.api.controller;

import cn.stylefeng.guns.modular.joey.mycase.api.pojo.CaseApiRequest;
import cn.stylefeng.guns.modular.joey.mycase.api.pojo.CaseHostRequest;
import cn.stylefeng.guns.modular.joey.mycase.api.service.CaseApiService;
import cn.stylefeng.guns.modular.joey.mycase.api.service.CaseHostService;
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

/**
 * 接口管理
 *
 * @author ycc
 */
@RestController
@ApiResource(name = "接口管理")
public class CaseApiController {

    private CaseHostService caseHostService;

    private CaseApiService caseApiService;

    @Autowired
    public void setCaseHostService(CaseHostService caseHostService) {
        this.caseHostService = caseHostService;
    }

    @Autowired
    public void setCaseApiService(CaseApiService caseApiService) {
        this.caseApiService = caseApiService;
    }

    @GetResource(name = "分页查询HOST列表", path = "/api/host/findPage")
    public ResponseData pageHost(CaseHostRequest caseHostRequest) {
        return new SuccessResponseData(caseHostService.findPage(caseHostRequest));
    }

    @GetResource(name = "查询HOST详情", path = "/api/host/detail")
    public ResponseData detailHost(@Validated(BaseRequest.detail.class)CaseHostRequest caseHostRequest) {
        return new SuccessResponseData(caseHostService.detail(caseHostRequest));
    }

    @PostResource(name = "添加HOST", path = "/api/host/add")
    public ResponseData addHost(@RequestBody @Validated(BaseRequest.add.class) CaseHostRequest caseHostRequest) {
        caseHostService.add(caseHostRequest);
        return new SuccessResponseData();
    }

    @PostResource(name = "删除HOST", path = "/api/host/delete")
    public ResponseData deleteHost(@RequestBody @Validated(BaseRequest.delete.class) CaseHostRequest caseHostRequest) {
        caseHostService.del(caseHostRequest);
        return new SuccessResponseData();
    }

    @PostResource(name = "修改HOST", path = "/api/host/edit")
    public ResponseData editHost(@RequestBody @Validated(BaseRequest.edit.class) CaseHostRequest caseHostRequest) {
        caseHostService.edit(caseHostRequest);
        return new SuccessResponseData();
    }

    @GetResource(name = "分页查询API列表", path = "/api/findPage")
    public ResponseData pageApi(CaseApiRequest caseApiRequest) {
        return new SuccessResponseData(caseApiService.findPage(caseApiRequest));
    }

    @GetResource(name = "查询HOST详情", path = "/api/detail")
    public ResponseData detailApi(@Validated(BaseRequest.detail.class)CaseApiRequest caseApiRequest) {
        return new SuccessResponseData(caseApiService.detail(caseApiRequest));
    }

    @PostResource(name = "添加HOST", path = "/api/add")
    public ResponseData addApi(@RequestBody @Validated(BaseRequest.add.class) CaseApiRequest caseApiRequest) {
        caseApiService.add(caseApiRequest);
        return new SuccessResponseData();
    }

    @PostResource(name = "删除HOST", path = "/api/delete")
    public ResponseData deleteApi(@RequestBody @Validated(BaseRequest.delete.class) CaseApiRequest caseApiRequest) {
        caseApiService.del(caseApiRequest);
        return new SuccessResponseData();
    }

    @PostResource(name = "修改HOST", path = "/api/edit")
    public ResponseData editApi(@RequestBody @Validated(BaseRequest.edit.class) CaseApiRequest caseApiRequest) {
        caseApiService.edit(caseApiRequest);
        return new SuccessResponseData();
    }

}
