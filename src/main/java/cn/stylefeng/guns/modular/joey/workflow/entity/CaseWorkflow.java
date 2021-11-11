package cn.stylefeng.guns.modular.joey.workflow.entity;

import cn.stylefeng.guns.modular.joey.testcase.entity.CaseDetail;
import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用例工作流
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("case_workflow")
public class CaseWorkflow extends BaseEntity {

    private static final long serialVersionUID = 3381822588343770864L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("group_id")
    private Long groupId;

    @TableField("execution_order")
    private String executionOrder;

    @TableField("comment")
    private String comment;

}
