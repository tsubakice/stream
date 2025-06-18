package org.qiaice;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class AppTest {

    @Test // 无入参, 有出参, 可以充当生产者
    void supplierTest() {
        Supplier<String> supplier = () -> "supplier";
        System.out.println(supplier.get());
    }

    @Test // 有入参, 无出参, 可以充当消费者
    void consumerTest() {
        Consumer<String> consumer = System.out::println;
        consumer.accept("consumer");
    }

    @Test // 有入参, 有出参, 可以充当中间操作
    void functionTest() {
        Function<String, String> function = String::toUpperCase;
        System.out.println(function.apply("function"));
    }

    @Test // 有入参, 有出参, 可以充当中间操作
    void predicateTest() {
        Predicate<String> predicate = s -> s.length() > 5;
        System.out.println(predicate.test("predicate"));
        System.out.println(predicate.test("PREDICATE"));
    }
}
