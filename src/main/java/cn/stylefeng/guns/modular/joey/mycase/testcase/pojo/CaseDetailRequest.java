package cn.stylefeng.guns.modular.joey.mycase.testcase.pojo;

import cn.stylefeng.guns.modular.joey.mycase.testcase.enums.ProtocolType;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotNull;

/**
 * 用例详情
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CaseDetailRequest extends BaseRequest {

    private static final long serialVersionUID = 5201137792213661608L;

    @NotNull(message = "用例详情ID不能为空", groups = {edit.class, delete.class, detail.class})
    private Long id;

    @NotNull(message = "用例组ID不能为空", groups = {add.class, edit.class})
    private Long groupId;

    private String comment;

    private Integer isRun;

    private ProtocolType protocolType;

    private Long hostId;

    private String requestData;

    private String preResult;

    private Integer isCache;

    public void setRequestData(String requestData) {
        this.requestData = HtmlUtils.htmlUnescape(requestData);
    }

    public void setPreResult(String preResult) {
        this.preResult = HtmlUtils.htmlUnescape(preResult);
    }
}
