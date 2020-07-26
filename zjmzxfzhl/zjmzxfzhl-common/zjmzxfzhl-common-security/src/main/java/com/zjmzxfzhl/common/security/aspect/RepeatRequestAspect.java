package com.zjmzxfzhl.common.security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.core.aspect.components.RepeatRequestComponent;
import com.zjmzxfzhl.common.security.util.SecurityUtils;

/**
 * 登录用户请求防重发处理,Order=0优先于RedissonLockAspect
 * 
 * @author 庄金明
 * @date 2020年3月24日
 */
@Aspect
@Component
@Order(0)
public class RepeatRequestAspect {
    @Autowired
    private RepeatRequestComponent repeatRequestComponent;

    @Pointcut("execution(* com.*..*.controller.*.*(..))")
    private void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object controllerAspectAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return repeatRequestComponent.exec(joinPoint, SecurityUtils.getUserId());
    }
}