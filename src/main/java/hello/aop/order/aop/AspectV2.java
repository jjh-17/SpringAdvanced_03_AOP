package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트컷 분리
 */
@Slf4j
@Aspect
public class AspectV2 {

    /**
     * 외부 포인트컷을 경로로 지정
     * @param joinPoint : 어드바이스 적용 지점
     * @return : 타겟 메서드 실행 반환값
     * @throws Throwable
     */
    @Around("allOrder()")  // 포인트 컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    /**
     * 포인트컷 분리
     */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() { } // 포인트컷 시그니처

}
