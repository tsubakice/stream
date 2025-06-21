package org.qiaice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class AppTest {

    @Test
    void filterTest() {
        Flux.range(0, 10)
                .filter(element -> element % 2 == 0)
                .log().subscribe();
    }

    @Test
    void flatMapTest() {
        Flux.just("1-2", "3-4", "5-6")
                .flatMap(element -> Flux.fromArray(element.split("-")))
                .log().subscribe();
    }

    @Test
    void concatTest() {
        Flux.concat(Flux.range(0, 5), Flux.range(5, 5))
                .log().subscribe();
    }

    // transform 与 transformDeferred 的区别就是是否重复计算
    // transform 仅计算一次转换逻辑, 并将该逻辑应用于所有订阅者
    // transformDeferred 每次订阅时都会重新计算转化逻辑, 适用于为每个订阅者提供独立状态的场景
    @Test
    void transformTest() {
        Flux<Integer> flux1 = Flux.range(0, 10)
                .transform(flux -> flux.map(element -> element * 2)
                        .filter(element -> element > 5));
        flux1.log().subscribe();
        flux1.log().subscribe();
    }

    @Test
    void emptyTest() {
        Mono.empty().defaultIfEmpty("default").log().subscribe();
        Mono.empty().switchIfEmpty(Mono.just("switch")).log().subscribe();
    }

    @Test
    void zipTest() {
        Flux.zip(Flux.range(0, 5), Flux.range(5, 5))
                .log().subscribe();
    }
}
