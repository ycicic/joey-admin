package cn.stylefeng.guns.modular.joey.mycase.api.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * HOST管理请求pojo
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CaseHostRequest extends BaseRequest {
    private static final long serialVersionUID = 2820796564046676320L;

    @NotNull(message = "ID不能为空", groups = {edit.class, delete.class, detail.class})
    private Long id;

    @NotNull(message = "协议不能为空", groups = {add.class, edit.class})
    private String protocol;

    @NotNull(message = "HOST不能为空", groups = {add.class, edit.class})
    private String host;

    private String port;

    private String comment;

}
