package org.qiaice;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

class FlowTest {

    @Test
    void flowTest() throws InterruptedException {

        // 创建发布者
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // 创建订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("订阅开始");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("item: " + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("订阅异常, 取消订阅");
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("订阅完成, 结束订阅");
                subscription.cancel();
            }
        };
        Flow.Subscriber<String> subscriber2 = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("订阅开始");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("item: " + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("订阅异常, 取消订阅");
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("订阅完成, 结束订阅");
                subscription.cancel();
            }
        };

        // 订阅发布者
        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber2);

        // 推送数据
        for (int i = 0; i < 10; i++) {
            publisher.submit(String.valueOf(i));
        }

        Thread.sleep(1000);
        publisher.close();
    }
}
