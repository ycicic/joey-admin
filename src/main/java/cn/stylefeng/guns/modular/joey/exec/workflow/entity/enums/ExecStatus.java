package cn.stylefeng.guns.modular.joey.exec.workflow.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行状态
 *
 * @author ycc
 */
@AllArgsConstructor
@Getter
public enum ExecStatus {

    /**
     * 执行失败
     */
    UNSTABLE(-1, "执行失败"),
    /**
     * 未执行
     */
    AWAIT(0, "未执行"),
    /**
     * 执行中
     */
    EXECUTION(1, "执行中"),
    /**
     * 执行成功
     */
    SUCCESS(2, "执行成功");

    private final Integer value;
    private final String desc;

}
