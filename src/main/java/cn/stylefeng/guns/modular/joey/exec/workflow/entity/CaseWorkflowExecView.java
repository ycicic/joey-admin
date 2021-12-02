package cn.stylefeng.guns.modular.joey.exec.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用例工作流执行视图表
 *
 * @author ycc
 */
@Data
@TableName("case_workflow_exec_view")
public class CaseWorkflowExecView {

    private static final long serialVersionUID = -8324606239644298228L;

    @TableField("workflow_id")
    private Long workflowId;

    @TableField("group_id")
    private Long groupId;

    @TableField("comment")
    private String comment;

    @TableField("last_status")
    private Integer lastStatus;

    @TableField("last_build_number")
    private Integer lastBuildNumber;

    @TableField("last_success_build_number")
    private Integer lastSuccessBuildNumber;

    @TableField("success_count")
    private Integer successCount;

    @TableField("sum_count")
    private Integer sumCount;

    @TableField(exist = false)
    private String lastBuildUrl;

    @TableField(exist = false)
    private String lastSuccessBuildUrl;

}
