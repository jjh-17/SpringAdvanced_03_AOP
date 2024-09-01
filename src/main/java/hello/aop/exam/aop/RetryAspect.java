package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 재시도 애스펙트
 */
@Slf4j
@Aspect
public class RetryAspect {

    // Retry 타입 파라미터를 가진 메서드에 적용
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        final int MAX_RETRY = retry.value();
        Exception exceptionHolder = null;

        for(int retryCnt = 1; retryCnt<= MAX_RETRY; retryCnt++) {
            try {
                log.info("[retry] try count={}/{}", retryCnt, MAX_RETRY);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
