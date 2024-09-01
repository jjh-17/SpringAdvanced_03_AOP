package hello.aop.proxys;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ProxyCastingTest {

    @Test
    @DisplayName("JDK 동적 프록시 타입 캐스팅")
    void jdkProxy() {
        // given
        MemberServiceImpl target = new MemberServiceImpl();

        // when
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시

        // then
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", memberServiceProxy.getClass());
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    @DisplayName("CGLIB 타입 캐스팅")
    void cglibProxy() {
        // given
        MemberServiceImpl target = new MemberServiceImpl();

        // when
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        // then
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", memberServiceProxy.getClass());
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }

}
