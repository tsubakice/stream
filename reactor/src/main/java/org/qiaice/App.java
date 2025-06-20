package org.qiaice;

import reactor.core.publisher.Flux;

public class App {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(0, 10);

        // 请求重塑 buffer, 将流中的数据按个数封装为 List
        flux.buffer(4).log().subscribe();

        // 请求重塑 limitRate, 限制请求数据的速率
        flux.log().limitRate(5).subscribe();
    }
}
