package com.xudc.springboot.aop;

import com.alibaba.fastjson.JSON;
import com.xudc.springboot.annotation.ShowLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xudc
 */
@Aspect
@Component
@Slf4j
public class AnnotationLogAspect {

    @Pointcut("execution(public * com.xudc.springboot.web.*.*(..))")
    public void aopLog(){
    }

    @Before("aopLog() && @annotation(showLog)")
    public void doBefore(JoinPoint joinPoint, ShowLog showLog) {
        Object[] args = joinPoint.getArgs();
        log.info("参数={}", JSON.toJSONString(args));
        Signature signature = joinPoint.getSignature();
        log.info("方法={}", signature.getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer url = request.getRequestURL();
        log.info("请求地址={}", url);

        Map<String, Object> params = new HashMap<>(16);
        request.getParameterMap().forEach((key, values) -> {
            if (values.length == 1) {
                params.put(key, values[0]);
            } else {
                params.put(key, values);
            }
        });
        log.info("请求参数={}", JSON.toJSONString(params));
        log.info("注解value={}", showLog.value());
    }
}
