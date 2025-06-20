package org.qiaice;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class App {
    public static void main(String[] args) {

        // 自定义线程调度
        // subscribeOn, 对上游生效, 从源到 publishOn 为止
        // publishOn, 对下游生效, 从当前 publishOn 到下一个 publishOn 为止

        // 选用建议
        // 纯 CPU 计算 -> Schedulers.parallel()
        // 阻塞 I/O (文件、网络、数据库) -> Schedulers.boundedElastic()
        // 顺序或单线程 -> Schedulers.single()
        // 测试或同步场景 -> Schedulers.immediate()
        Flux.range(0, 10)
                .map(element -> "<" + element)
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.single())
                .map(element -> element + ">")
                .publishOn(Schedulers.parallel())
                .subscribe(element -> System.out.println("Element " + element + " on thread " + Thread.currentThread().getName()));
    }
}
