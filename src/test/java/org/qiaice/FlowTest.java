package org.qiaice;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

class FlowTest {

    // 自定义处理器
    static class CustomProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {
        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Integer item) {
            submit(String.valueOf(item).repeat(3));
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            subscription.cancel();
        }

        @Override
        public void onComplete() {
            subscription.cancel();
        }
    }

    @Test
    void flowTest() throws InterruptedException {

        // 创建发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // 创建处理器
        CustomProcessor processor = new CustomProcessor();

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

        // 处理器订阅发布者
        publisher.subscribe(processor);

        // 订阅者订阅处理器
        processor.subscribe(subscriber);

        // 推送数据
        for (int i = 0; i < 10; i++) {
            publisher.submit(i);
        }

        Thread.sleep(1000);
        publisher.close();
        processor.close();
    }
}
