package cn.stylefeng.guns.modular.joey.mycase.api.controller;

import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import org.springframework.stereotype.Controller;

/**
 * 接口管理界面
 *
 * @author ycc
 */
@Controller
@ApiResource(name = "接口管理界面")
public class CaseApiViewController {

    @GetResource(name = "接口管理-首页", path = "/view/api/index")
    public String caseApiIndex() {
        return "/modular/joey/case/api/api.html";
    }

    @GetResource(name = "接口管理-添加HOST", path = "/view/api/host/add")
    public String addHost() {
        return "/modular/joey/case/api/host_add.html";
    }

    @GetResource(name = "接口管理-修改HOST", path = "/view/api/host/edit")
    public String editHost() {
        return "/modular/joey/case/api/host_edit.html";
    }

    @GetResource(name = "接口管理-添加API", path = "/view/api/add")
    public String addApi() {
        return "/modular/joey/case/api/api_add.html";
    }

    @GetResource(name = "接口管理-修改API", path = "/view/api/edit")
    public String editApi() {
        return "/modular/joey/case/api/api_edit.html";
    }

    @GetResource(name = "接口管理-选择接口", path = "/view/api/select")
    public String selectApi() {
        return "/modular/joey/case/api/api_select.html";
    }
}
