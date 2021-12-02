package cn.stylefeng.guns.modular.joey.mycase.api.exception;

import cn.stylefeng.guns.core.consts.ProjectConstants;
import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口模块异常枚举
 *
 * @author ycc
 */
@AllArgsConstructor
@Getter
public enum ApiExceptionEnum implements AbstractExceptionEnum {

    /**
     * HOST信息不存在
     */
    CASE_HOST_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ProjectConstants.BUSINESS_EXCEPTION_STEP_CODE + "01", "HOST信息不存在");

    private final String errorCode;

    private final String userTip;

}
