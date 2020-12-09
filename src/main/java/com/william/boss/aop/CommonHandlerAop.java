package com.william.boss.aop;

import com.william.boss.context.ViewContentHolder;
import com.william.boss.vo.KeyValue;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author john
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class CommonHandlerAop {

    @Pointcut("execution(public * com.thymeleafdemo.controller..*Controller.*(..))")
    public void controllerInterceptor() {
    }

    @Around("controllerInterceptor()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o = null;
        try {
            o = pjp.proceed();
            if (o instanceof ModelAndView) {
                KeyValue<String, String> keyValue = new KeyValue<>();
                keyValue.setKey(((ModelAndView)o).getViewName());
                ViewContentHolder.set(keyValue);
            }
        } finally {
            String response = String.valueOf(o);
            log.info(response);
        }
        return o;
    }
}
