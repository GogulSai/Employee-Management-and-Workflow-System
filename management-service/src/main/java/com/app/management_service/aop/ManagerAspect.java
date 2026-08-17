package com.app.management_service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ManagerAspect {

    @Pointcut("execution(* com.app.management_service.service.ManagerService.*(..))")
    public void managerServiceMethods() {}

    // ✅ BEFORE → log input
    @Before("managerServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("➡️ Entering method: " + joinPoint.getSignature().getName());
        System.out.println("📥 Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    // ✅ AFTER → always executes
    @After("managerServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("⬅️ Exiting method: " + joinPoint.getSignature().getName());
    }

    // ✅ AFTER RETURNING → success
    @AfterReturning(pointcut = "managerServiceMethods()", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        System.out.println("✅ Method Success: " + joinPoint.getSignature().getName());
        System.out.println("📤 Response: " + result);
    }

    // ✅ AFTER THROWING → exception
    @AfterThrowing(pointcut = "managerServiceMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        System.out.println("❌ Exception in: " + joinPoint.getSignature().getName());
        System.out.println("⚠️ Error: " + ex.getMessage());
    }

    // ✅ AROUND → execution time (VERY IMPORTANT in real apps)
    @Around("managerServiceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        System.out.println("⏱️ Start: " + pjp.getSignature().getName());

        Object result = pjp.proceed(); // actual method call

        long end = System.currentTimeMillis();

        System.out.println("⏱️ End: " + pjp.getSignature().getName() +
                " | Time Taken: " + (end - start) + " ms");

        return result;
    }

}
