package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    // 내부 호출 발생
    public void external() {
        log.info("call external");
        internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
