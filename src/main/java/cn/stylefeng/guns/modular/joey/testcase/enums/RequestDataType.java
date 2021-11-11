package cn.stylefeng.guns.modular.joey.testcase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求数据类型枚举
 *
 * @author ycc
 */
@Getter
@AllArgsConstructor
public enum RequestDataType {

    /**
     * 浏览器的原生 form 表单
     */
    NONE("application/x-www-form-urlencoded;charset=UTF-8"),
    /**
     * 表单
     */
    FORM("multipart/form-data;charset=UTF-8"),
    /**
     * json
     */
    JSON("application/json;charset=UTF-8"),
    /**
     * xml-rpc
     */
    XML("text/xml;charset=UTF-8");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
