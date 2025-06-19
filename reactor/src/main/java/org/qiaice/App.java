package org.qiaice;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class App {
    public static void main(String[] args) {
        Flux.range(0, 10)
                .subscribe(new BaseSubscriber<>() {

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("订阅成功");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("订阅到达: " + value);
                        if (value == 5) {
                            throw new RuntimeException("exception");
                        }
                        request(1);
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("订阅完成");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        System.out.println("订阅异常");
                    }

                    @Override
                    protected void hookOnCancel() {
                        System.out.println("订阅被取消");
                    }

                    @Override
                    protected void hookFinally(SignalType type) {
                        System.out.println("订阅结束");
                    }
                });
    }
}
