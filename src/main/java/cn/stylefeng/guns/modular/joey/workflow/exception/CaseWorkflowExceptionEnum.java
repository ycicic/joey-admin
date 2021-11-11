package cn.stylefeng.guns.modular.joey.workflow.exception;

import cn.stylefeng.guns.core.consts.ProjectConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 用例工作流异常枚举
 *
 * @author ycc
 */
@Getter
public enum CaseWorkflowExceptionEnum implements AbstractExceptionEnum {
    /**
     * 用例工作流不存在
     */
    CASE_WORKFLOW_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ProjectConstants.BUSINESS_EXCEPTION_STEP_CODE + "01", "用例工作流不存在"),

    CASE_GROUP_HASE_NOT_DETAIL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ProjectConstants.BUSINESS_EXCEPTION_STEP_CODE + "02", "关联用例组内无用例数据");

    private final String errorCode;

    private final String userTip;

    CaseWorkflowExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
