package org.qiaice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ReactorTest {

    @Test
    void fluxTest() {
        Flux.range(0, 7)
                .map(e -> "prefix-" + e).log()
                .doOnNext(e -> {
                    System.out.println("订阅到达: " + e);
                    if ("prefix-4".equals(e)) {
                        throw new RuntimeException("订阅错误");
                    }
                })
                .doOnComplete(() -> System.out.println("订阅结束"))
                .doFinally(t -> System.out.println("订阅结束信号类型: " + t))
                .subscribe(e -> System.out.println("开始订阅: " + e),
                        t -> System.out.println("错误信息: " + t.getLocalizedMessage()));
    }

    @Test
    void monoTest() {
        Mono.just(1).subscribe(e -> System.out.println("e -> " + e));
    }
}
