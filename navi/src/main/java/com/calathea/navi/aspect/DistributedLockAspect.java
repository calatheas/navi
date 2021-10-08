package com.calathea.navi.aspect;

import com.calathea.navi.common.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class DistributedLockAspect {
    private final static String KEY = "id";
    private final static long WAIT_SECONDS = 10;
    private final static long LEASE_SECONDS = 5;

    @Autowired
    RedisService redisService;

    // 어노테이션 기반 타겟 찾기
    @Pointcut("@annotation(DistributedLock)")
    public void distributedLockMethods() {
    }

    // 패키지 기반 타겟 찾기
    @Pointcut("execution(* com.calathea.navi.*.service..*(..))")
    public void serviceMethods() {
    }

    // 타겟을 포함하여 실행
    @Around("distributedLockMethods() && serviceMethods()")
    public Object lockTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        String transactionId = null;

        try {
            for (int i = 0; i < parameterNames.length; i++) {
                if (KEY.equals(parameterNames[i])) {
                    transactionId = (String) joinPoint.getArgs()[i];
                    break;
                }
            }
        } catch (Exception e) {
            log.warn("Key for lock is not found");
        }

        if (StringUtils.isNotBlank(transactionId)) {
            log.info("Wait for locks");
//            RLock lock = redisService.getLock(transactionId);
//            lock.tryLock(WAIT_SECONDS, LEASE_SECONDS, TimeUnit.SECONDS);
            log.info("Locked by this thread");
            Object object = joinPoint.proceed();
//            lock.unlock();
            log.info("Unlocked by this thread");
            return object;
        } else {
            return joinPoint.proceed();
        }
    }
}
