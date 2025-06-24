package org.qiaice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

class AppTest {

    // 重试不止单单请求导致重试的元素, 而是重新请求这条流

    @Test
    void timeoutAndRetryTest() {
        Flux.just(0, 1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(2))
                .timeout(Duration.ofSeconds(1)) // 设置超时时间
                .retry(3) // 设置重试次数
                .log().subscribe();
    }
}
