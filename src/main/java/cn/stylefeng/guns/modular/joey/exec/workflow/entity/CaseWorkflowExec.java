package cn.stylefeng.guns.modular.joey.exec.workflow.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用例工作流执行记录
 *
 * @author ycc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("case_workflow_exec")
public class CaseWorkflowExec extends BaseEntity {
    private static final long serialVersionUID = -1418829638633804314L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("workflow_id")
    private Long workflowId;

    @TableField("status")
    private Integer status;

    @TableField("build_number")
    private Integer buildNumber;
}
