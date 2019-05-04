package com.xudc.exception.web;

import com.xudc.exception.enums.ExceptionEnum;
import com.xudc.exception.exceptions.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2019/4/30 21:42
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(Integer num) {
        if (num == null) {
            throw new CustomException(ExceptionEnum.INVALID_ARGS);
        }
        if (num == 0) {
            throw new CustomException(ExceptionEnum.ZERO_ERROR);
        }
        int result = 10 / num;
        return "success-" + result;
    }
}
