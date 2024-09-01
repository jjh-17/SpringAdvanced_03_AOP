package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest(properties = "spring.main.allow-circular-references=true")
public class CallServiceV2Test {

    @Autowired CallServiceV2 callServiceV2;

    @Test
    @DisplayName("외부 호출")
    void external() {
        // then
        callServiceV2.external();
    }

    @Test
    @DisplayName("내부 호출")
    void internal() {
        // then
        callServiceV2.internal();
    }
}