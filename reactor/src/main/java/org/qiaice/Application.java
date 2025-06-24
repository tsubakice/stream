package org.qiaice;

import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Application {

    public static DataBufferFactory dataBufferFactory;

    public static void main(String[] args) {
        HttpHandler handler = ((request, response) -> {
            if (Objects.isNull(dataBufferFactory)) {
                dataBufferFactory = response.bufferFactory();
            }
            byte[] bytes = (request.getURI() + " Hello ya ~").getBytes(StandardCharsets.UTF_8);
            return response.writeWith(Mono.just(dataBufferFactory.wrap(bytes)));
        });
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create().host("localhost").port(8080).handle(adapter).bindNow();
        while (true) {}
    }
}
