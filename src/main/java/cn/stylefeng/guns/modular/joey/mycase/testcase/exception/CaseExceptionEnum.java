package cn.stylefeng.guns.modular.joey.mycase.testcase.exception;

import cn.stylefeng.guns.core.consts.ProjectConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 用例模块异常枚举
 *
 * @author ycc
 */
@Getter
public enum CaseExceptionEnum implements AbstractExceptionEnum {

    /**
     * 用例组不存在
     */
    CASE_GROUP_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ProjectConstants.BUSINESS_EXCEPTION_STEP_CODE + "01", "用例组不存在"),

    /**
     * 用例详情不存在
     */
    CASE_DETAIL_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ProjectConstants.BUSINESS_EXCEPTION_STEP_CODE + "02", "用例详情不存在");

    private final String errorCode;

    private final String userTip;

    CaseExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }
}
