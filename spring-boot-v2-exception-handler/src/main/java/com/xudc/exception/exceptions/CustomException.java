package com.xudc.exception.exceptions;

import com.xudc.exception.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author xudc
 * @date 2019/4/30 21:52
 */
@Data
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -4096086160269012865L;
    private ExceptionEnum exceptionEnum;

    public CustomException(ExceptionEnum exceptionEnum) {
        super();
        this.exceptionEnum = exceptionEnum;
    }
}
