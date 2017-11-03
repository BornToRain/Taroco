package xyz.weechang.taroco.common.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

public class ProfilingAspect {

    @Around("methodsToBeProfiled()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch(getClass().getSimpleName());
        try {
            sw.start(pjp.getSignature().getName());
            return pjp.proceed();
        } finally {
            sw.stop();
            System.out.println(sw.getLastTaskName() + sw.shortSummary());
        }
    }

    @Pointcut("execution(public * org.axonframework.eventstore.mongo.MongoEventStore.*(..))")
    public void methodsToBeProfiled() {
    }
}