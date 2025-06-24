package org.qiaice;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class AppTest {

    // 自定义异常处理
    // onErrorReturn: 返回自定义值
    // onErrorResume: 在返回自定义值之前进行额外的操作
    // onErrorComplete: 将异常信号转换为完成信号
    // onErrorMap: 将异常包装为自定义异常, 并向下传递
    // onErrorContinue: 处理异常后继续推进
    // onErrorStop: 发生异常后停止流(从根本上的停止, 也就是会停止所有监听者的监听)

    @Test
    void errorTest() {
        Flux.just(1, 2, 0, 3)
                .map(element -> 100 / element)
                .onErrorContinue((t, e) -> System.out.println("元素: " + e + " 存在异常: " + t.getLocalizedMessage()))
                .subscribe(e -> System.out.println("e -> " + e), System.err::println);
    }
}
