package cn.stylefeng.guns.modular.joey.mycase.api.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用例HOST
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("case_host")
public class CaseHost extends BaseEntity {
    private static final long serialVersionUID = -6741383935375514737L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("protocol")
    private String protocol;

    @TableField("host")
    private String host;

    @TableField("port")
    private String port;

    @TableField("comment")
    private String comment;

}
