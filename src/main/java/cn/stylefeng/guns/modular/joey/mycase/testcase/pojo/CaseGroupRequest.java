package cn.stylefeng.guns.modular.joey.mycase.testcase.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 用例组管理请求
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CaseGroupRequest extends BaseRequest {
    private static final long serialVersionUID = 5959691079734432161L;

    @NotNull(message = "用例组ID不能为空", groups = {edit.class, delete.class, detail.class})
    private Long id;

    @NotNull(message = "所属模块不能为空", groups = {add.class, edit.class})
    private String module;

    private String comment;

    private Integer isRun;
}
