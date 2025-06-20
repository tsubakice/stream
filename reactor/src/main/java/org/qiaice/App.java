package org.qiaice;

import reactor.core.publisher.Flux;

public class App {
    public static void main(String[] args) {

        // 自定义元素处理, 相当于更强大的 map
        Flux.range(1, 10)
                .handle((e, sink) -> sink.next(e * e))
                .log().subscribe();
    }
}
