package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {

    // 포인트컷 표현식 처리 클래스
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("기본 메서드")
    void printMethod() {
        // then
        log.info("helloMethod={}", helloMethod);
    }
    
    @Test
    @DisplayName("가장 정확한 매칭")
    void exactMatch() {
        // given
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("가장 많은 생략 매칭")
    void allMatch() {
        // given
        pointcut.setExpression("execution(* *(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("이름 매칭")
    void nameMatch() {
        // given
        pointcut.setExpression("execution(* hello(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("이름 매칭 * 1")
    void nameMatchStart1() {
        // given
        pointcut.setExpression("execution(* hel*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("이름 매칭 * 2")
    void nameMatchStar2() {
        // given
        pointcut.setExpression("execution(* *el*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("이름 매칭 실패")
    void nameMatchFail() {
        // given
        pointcut.setExpression("execution(* nono(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("패키지 매칭 - 정확한 매칭1")
    void packageExactMatch1() {
        // given
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 - 정확한 매칭2")
    void packageExactMatch2() {
        // given
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 - 실패")
    void packageExactMatchFalse() {
        // given
        pointcut.setExpression("execution(* hello.aop.*.*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("패키지 매칭 - 서브 패키지1")
    void packageMatchSubPackage1() {
        // given
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 - 서브 패키지2")
    void packageMatchSubPackage2() {
        // given
        pointcut.setExpression("execution(* hello.aop..*.*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입 매칭")
    void typeExactMatch() {
        // given
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모 타입 매칭")
    void typMatchSuperType() {
        // given
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("내부 메서드만 매칭")
    void typeMatchInternal() throws NoSuchMethodException {
        // given
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        // then
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모 타입으로 내부 메서드 매칭 실패")
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        // given
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        // then
        assertThat(pointcut.matches(internalMethod,
                MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("파라미터 매칭")
    void argsMatch() {
        // given
        pointcut.setExpression("execution(* *(String))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터 매칭 실패")
    void argsMatchNoArgs() {
        // given
        pointcut.setExpression("execution(* *())");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("모든 타입의 파라미터 하나 매칭")
    void argsMatchStar() {
        // given
        pointcut.setExpression("execution(* *(*))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    @DisplayName("모든 타입, 모든 개수의 파라미터 매칭")
    void argsMatchAll() {
        // given
        pointcut.setExpression("execution(* *(..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("String 타입 시작, 그 외 모든 타입 및 개수의 파라미터 매칭")
    void argsMathsComplex() {
        // given
        pointcut.setExpression("execution(* *(String, ..))");

        // then
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
