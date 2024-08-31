package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class AopTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;

    @Test
    @DisplayName("정보 테스트")
    void aopInfo() {
        // then
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    @DisplayName("성공")
    void success() {
        // then
        orderService.orderItem("itemA");
    }

    @Test
    @DisplayName("실패")
    void exception() {
        // then
        assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);

    }
}
