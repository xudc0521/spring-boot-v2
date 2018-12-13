package com.xudc.springboot.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
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
 * @date 2018/12/13 11:33
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * 匹配com.xudc.springboot.controller及其子包下所有类的所有方法
     */
    @Pointcut("execution(public * com.xudc.springboot.controller.*.*(..))")
    public void webLog(){

    }

    /**
     * http://localhost:8080/index?id=2
     * 这里可以把日志信息保存到日志系统或者MongoDB等NoSQL数据库中
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        // 获取此连接点上的参数
        Object[] args = joinPoint.getArgs();
        // args=[2]
        log.info("args={}",JSON.toJSONString(args));
        // 获取连接点上的签名信息
        Signature signature = joinPoint.getSignature();
        // 代理的方法名 func=index
        log.info("func={}",signature.getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // URL地址 URL:http://localhost:8080/index
        log.info("URL:{}", request.getRequestURL());
        // Http_Method:GET,提交参数方法method
        log.info("Http_Method:{}",request.getMethod());
        log.info("IP:{}",request.getRemoteAddr());
        //获取所有参数对象,put到map中
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String,Object> params = new HashMap<>(16);
        for (String key : parameterMap.keySet()) {
            if (parameterMap.get(key).length == 1) {
                params.put(key,parameterMap.get(key)[0]);
            } else {
                params.put(key,parameterMap.get(key));
            }
        }
        log.info("Params={}", JSON.toJSONString(params));
    }


    /**
     * 处理完请求返回的内容
     * @param ret
     */
    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        log.info("Return={}",JSON.toJSONString(ret));
    }
}
