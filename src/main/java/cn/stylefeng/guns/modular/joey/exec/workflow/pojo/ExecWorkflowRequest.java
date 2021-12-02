package cn.stylefeng.guns.modular.joey.exec.workflow.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotNull;

/**
 * 执行工作流
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExecWorkflowRequest extends BaseRequest {
    private static final long serialVersionUID = -1158030468661286148L;

    @NotNull(message = "工作流ID不能为空")
    private Long id;

    @NotNull(message = "执行分支不能为空")
    private String branches;

    private String params;

    public void setParams(String params) {
        this.params = HtmlUtils.htmlUnescape(params);
    }
}
