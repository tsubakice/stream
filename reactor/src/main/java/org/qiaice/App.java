package org.qiaice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.atomic.AtomicInteger;

public class App {
    public static void main(String[] args) {

        // 编程式创建 Flux 流 -- 同步环境下
        Flux.generate(AtomicInteger::new, (state, sink) -> {
            if (state.getAndIncrement() >= 10) {
                sink.complete();
            } else {
                sink.next(state);
            }
            return state;
        }).log().limitRate(5).subscribe();

        class UserLoginListener {
            final FluxSink<Object> sink;

            UserLoginListener(FluxSink<Object> sink) {
                this.sink = sink;
            }

            void login(String username) {
                sink.next(username);
            }
        }

        // 编程式创建 Flux 流 -- 异步环境下
        // 官方将其与监听器相结合, 达到事件触发后向通道中添加元素的效果
        Flux.create(sink -> {
            UserLoginListener listener = new UserLoginListener(sink);
            for (int i = 0; i < 10; i++) {
                listener.login("用户-" + i);
            }
        }).log().limitRate(5).subscribe();
    }
}
