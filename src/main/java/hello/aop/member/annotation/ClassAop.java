package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 클래스, 인터페이스, 열거형 등에 적용 가능 애노테이션 => 메서드, 필드, 패키지 불가
 * 런타임 동안 애노테이션 유지
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAop {
}
