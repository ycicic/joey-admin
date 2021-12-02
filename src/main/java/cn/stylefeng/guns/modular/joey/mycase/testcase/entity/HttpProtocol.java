package cn.stylefeng.guns.modular.joey.mycase.testcase.testcase.entity;

import cn.stylefeng.guns.modular.joey.mycase.testcase.enums.RequestDataType;
import cn.stylefeng.guns.modular.joey.mycase.testcase.enums.RequestMethod;
import lombok.Data;

/**
 * http协议渠道
 *
 * @author ycc
 */
@Data
public class HttpProtocol {

    private String host;
    private String url;
    private RequestMethod method;
    private RequestDataType dataType;

}
