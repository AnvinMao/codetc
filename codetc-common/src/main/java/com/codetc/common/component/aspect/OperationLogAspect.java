package com.codetc.common.component.aspect;

import com.codetc.common.annotation.OperationLog;
import com.codetc.common.model.Log;
import com.codetc.common.util.ServletUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 操作日志处理切面
 * Created by anvin on 1/16/2021.
 */
@Aspect
public abstract class OperationLogAspect {

    @Pointcut("@annotation(com.codetc.common.annotation.OperationLog)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            long time = System.currentTimeMillis() - beginTime;
            this.handlerLog(joinPoint, (int) time);
        }

    }

    private void handlerLog(ProceedingJoinPoint joinPoint, int time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog == null) {
            return;
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        String args = Arrays.toString(joinPoint.getArgs());
        String ip = ServletUtils.getIpAddress();
        Log log = new Log();
        log.setMethod(className + "." + methodName + "()");
        log.setIp(ip);
        log.setParams(args);
        log.setOperation(operationLog.value());
        log.setSpanTime(time);
        this.saveLog(log);
    }

    protected abstract void saveLog(Log log);
}
