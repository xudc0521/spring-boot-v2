package com.xudc.exception.res;

import com.xudc.exception.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author xudc
 * @date 2019/4/30 22:14
 */
@Data
public class ResponseResult {
    private boolean success;
    private int code;
    private String message;

    public ResponseResult(ExceptionEnum exceptionEnum) {
        this.success = exceptionEnum.isSuccess();
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public static ResponseResult fail(){
        return new ResponseResult(ExceptionEnum.FAIL);
    }
}
