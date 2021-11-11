package cn.stylefeng.guns.modular.joey.testcase.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用例组
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("case_group")
public class CaseGroup extends BaseEntity {
    private static final long serialVersionUID = -6052259573611056348L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("module")
    private String module;

    @TableField("comment")
    private String comment;

    @TableField("is_run")
    private Integer isRun;

}
