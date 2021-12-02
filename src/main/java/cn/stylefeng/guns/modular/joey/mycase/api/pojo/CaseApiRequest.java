package cn.stylefeng.guns.modular.joey.mycase.api.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * API请求参数
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CaseApiRequest extends BaseRequest {

    private static final long serialVersionUID = -1086155125384566862L;

    @NotNull(message = "ID不能为空", groups = {edit.class, delete.class, detail.class})
    private Long id;

    @NotNull(message = "HOST ID不能为空", groups = {add.class, edit.class})
    private Long hostId;

    @NotNull(message = "API不能为空", groups = {add.class, edit.class})
    private String api;

    @NotNull(message = "请求方式不能为空", groups = {add.class, edit.class})
    private String requestMethod;

    @NotNull(message = "请求数据类型不能为空", groups = {add.class, edit.class})
    private String requestDataType;

    private String comment;
}
