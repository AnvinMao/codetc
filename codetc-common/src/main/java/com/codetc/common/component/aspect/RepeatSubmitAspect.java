package com.codetc.common.component.aspect;

import com.codetc.common.annotation.NoRepeatSubmit;
import com.codetc.common.constant.ResultCodeEnum;
import com.codetc.common.exception.ApiException;
import com.codetc.common.model.RedisLock;
import com.codetc.common.service.RedisMutexLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * 重复提交检测切面
 * Created by anvin on 1/16/2021.
 */
@Aspect
public abstract class RepeatSubmitAspect {

    @Autowired
    private RedisMutexLock mutexLock;

    @Pointcut("@annotation(com.codetc.common.annotation.NoRepeatSubmit)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        NoRepeatSubmit noRepeatSubmit = method.getAnnotation(NoRepeatSubmit.class);
        RedisLock lock = this.mutexLock.lock(this.getKey(), noRepeatSubmit.lockTime());
        if (lock != null) {
            try {
                return joinPoint.proceed();
            } finally {
                this.mutexLock.releaseLock(lock);
            }
        } else {
            throw new ApiException(
                    ResultCodeEnum.REPEAT_SUBMIT.getCode(),
                    ResultCodeEnum.REPEAT_SUBMIT.getMessage());
        }
    }

    protected abstract String getKey();
}
