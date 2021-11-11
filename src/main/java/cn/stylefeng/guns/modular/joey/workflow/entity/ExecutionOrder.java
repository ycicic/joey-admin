package cn.stylefeng.guns.modular.joey.workflow.entity;

import cn.stylefeng.guns.modular.joey.testcase.entity.CaseDetail;
import lombok.Data;

/**
 * 执行顺序
 *
 * @author ycc
 */
@Data
public class ExecutionOrder {
    private Long order;
    private Long detailId;
}