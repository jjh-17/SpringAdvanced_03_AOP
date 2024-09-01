package hello.aop.pointcut;

import hello.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired Child child;

    //
    @Test
    @DisplayName("자식의 부모 메서드 출력")
    void printParentMethod() {
        log.info("child Proxy={}", child.getClass());
        child.parentMethod(); //부모 클래스만 있는 메서드
    }

    @Test
    @DisplayName("자식의 메서드 출력")
    void printChildMethod() {
        log.info("child Proxy={}", child.getClass());
        child.childMethod(); //부모, 자식 모두 있는 메서드
    }

    // 스프링 빈 설정
    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    // 부모 클래스
    static class Parent {
        // 부모에만 있는 메서드
        public void parentMethod() { }
    }

    // 자식 클래스
    @ClassAop
    static class Child extends Parent {
        // 자식에만 있는 메서드
        public void childMethod() { }
    }

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect {
        /**
         * @target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
         * @ClassAop 가 적용된 클래스와 그 부모 클래스의 메서드 모두에 적용되는 어드바이스
         */
        @Around("execution(* hello.aop..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        /**
         * @within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
         * @ClassAop 가 적용된 클래스의 메서드에만 적용되는 어드바이스
         */
        @Around("execution(* hello.aop..*(..)) && @within(hello.aop.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}