package cn.stylefeng.guns.modular.joey.mycase.api.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用例API
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("case_api")
public class CaseApi extends BaseEntity {

    private static final long serialVersionUID = -83999538624814783L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("host_id")
    private Long hostId;

    @TableField("api")
    private String api;

    @TableField("request_method")
    private String requestMethod;

    @TableField("request_data_type")
    private String requestDataType;

    @TableField("comment")
    private String comment;
}
