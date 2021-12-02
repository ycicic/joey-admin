package cn.stylefeng.guns.modular.joey.exec.workflow.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 执行参数
 *
 * @author ycc
 */
@Data
public class ExecParamRequest implements Serializable {
    private static final long serialVersionUID = -5132229288966122265L;
    private String key;
    private String value;
}
