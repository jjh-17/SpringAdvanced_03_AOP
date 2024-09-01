package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 대안2:지연 조회
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

    // 스프링 컨테이너에서 객체 조회 => 실제 객체 사용 시점으로 조회 지연 가능
    private final ObjectProvider<CallServiceV2> callServiceV2Provider;

    public void external() {
        log.info("call external");

        // getObject() 호출 시점에 스프링 빈 조회
        // 자기 자신 DI X => 순환 사이클 발생 X
        callServiceV2Provider.getObject().internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
