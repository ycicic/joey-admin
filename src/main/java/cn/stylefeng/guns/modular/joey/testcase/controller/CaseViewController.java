package cn.stylefeng.guns.modular.joey.testcase.controller;

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * 用例组管理界面
 *
 * @author ycc
 */
@Controller
@ApiResource(name = "用例管理界面")
public class CaseViewController {

    @GetResource(name = "用例管理-首页", path = "/view/case")
    public String caseGroupIndex() {
        return "/modular/joey/case/case.html";
    }

    @GetResource(name = "用例组管理-新增", path = "/view/caseGroup/add")
    public String caseGroupAdd() {
        return "/modular/joey/case/case_group_add.html";
    }

    @GetResource(name = "用例组管理-编辑", path = "/view/caseGroup/edit")
    public String caseGroupEdit() {
        return "/modular/joey/case/case_group_edit.html";
    }

    @GetResource(name = "用例详情管理-新增", path = "/view/caseDetail/add")
    public String caseDetailAdd() {
        return "/modular/joey/case/case_detail_add.html";
    }

    @GetResource(name = "用详情管理-编辑", path = "/view/caseDetail/edit")
    public String caseDetailEdit() {
        return "/modular/joey/case/case_detail_edit.html";
    }

    @GetResource(name = "用例详情管理-查看执行数据",path = "/view/caseDetail/requestData")
    public String caseDetailRequestData(){
        return "/modular/joey/case/case_detail_request_data.html";
    }
}
