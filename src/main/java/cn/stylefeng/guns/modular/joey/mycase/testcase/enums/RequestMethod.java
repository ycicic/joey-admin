package cn.stylefeng.guns.modular.joey.mycase.testcase.enums;

/**
 * 请求方式枚举
 *
 * @author ycc
 */
public enum RequestMethod {

    /**
     * 允许客户端查看服务器的性能
     */
    OPTIONS,
    /**
     * 请求指定的页面信息，并返回实体主体
     */
    GET,
    /**
     * 类似于get请求，只不过返回的响应中没有具体的内容，用于获取报头
     */
    HEAD,
    /**
     * 向指定资源提交数据进行处理请求
     */
    POST,
    /**
     * 从客户端向服务器传送的数据取代指定的文档的内容
     */
    PUT,
    /**
     * 请求服务器删除指定的页面
     */
    DELETE,
    /**
     * 回显服务器收到的请求，主要用于测试或诊断
     */
    TRACE,
    /**
     * 将连接改为管道方式的代理服务器
     */
    CONNECT;

}
