package com.xudc.exception.advice;

import com.xudc.exception.exceptions.CustomException;
import com.xudc.exception.res.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author xudc
 * @date 2019/4/30 22:06
 */
@RestControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseResult customException(CustomException e) {
        return new ResponseResult(e.getExceptionEnum());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult exception(RuntimeException e) {
        e.printStackTrace();
        return ResponseResult.fail();
    }
}
