package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
public class CallServiceV0Test {

    @Autowired CallServiceV0 callServiceV0;

    @Test
    @DisplayName("외부 호출")
    void external() {
        // then
        callServiceV0.external();
    }

    @Test
    @DisplayName("내부 호출")
    void internal() {
        // then
        callServiceV0.internal();
    }
}
