package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Aspect => 자동 스캔 X. 직접 등록 필요
 */
@Slf4j
@Aspect
public class AspectV1 {

    /**
     * 어드바이스 정의
     * @param joinPoint : 어드바이스 적용 지점
     * @return : 타겟 메서드 실행 반환값
     * @throws Throwable
     */
    @Around("execution(* hello.aop.order..*(..))")  // 포인트 컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
