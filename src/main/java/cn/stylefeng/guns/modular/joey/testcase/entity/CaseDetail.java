package cn.stylefeng.guns.modular.joey.testcase.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import cn.stylefeng.guns.modular.joey.testcase.enums.ProtocolType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用例详情
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("case_detail")
public class CaseDetail extends BaseEntity {

    private static final long serialVersionUID = 2377303305894936920L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("group_id")
    private Long groupId;

    @TableField("comment")
    private String comment;

    @TableField("is_run")
    private Integer isRun;

    @TableField("protocol_type")
    private ProtocolType protocolType;

    @TableField("host_id")
    private Long hostId;

    @TableField("request_data")
    private String requestData;

    @TableField("pre_result")
    private String preResult;

    @TableField("is_cache")
    private Integer isCache;

    @TableField(exist = false)
    private Long order;
}
