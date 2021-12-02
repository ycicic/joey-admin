package cn.stylefeng.guns.modular.joey.mycase.workflow.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotNull;

/**
 * 用例工作流
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CaseWorkflowRequest extends BaseRequest {
    private static final long serialVersionUID = 1314248061712139611L;

    @NotNull(message = "用例工作流ID不能为空", groups = {edit.class, delete.class, detail.class})
    private Long id;

    @NotNull(message = "用例组ID不能为空", groups = {add.class, edit.class})
    private Long groupId;
    private String executionOrder;

    private String params;

    private String comment;

    public void setExecutionOrder(String executionOrder) {
        this.executionOrder = HtmlUtils.htmlUnescape(executionOrder);
    }

    public void setParams(String params) {
        this.params = HtmlUtils.htmlUnescape(params);
    }
}
