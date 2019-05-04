package com.xudc.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author xudc
 * @date 2019/4/30 21:52
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    SUCCESS(true,10000,"操作成功！"),
    ZERO_ERROR(false,10001,"除数不能为零！"),
    INVALID_ARGS(false,10002,"缺少参数！"),
    FAIL(false,99999,"系统异常"),

    ;
    private boolean success;
    private int code;
    private String message;
}
