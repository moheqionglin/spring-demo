package com;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.jersey.JerseySupport;
import io.helidon.webserver.zipkin.ZipkinTracerBuilder;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogManager;

/**
 * WebServer Jersey
 */
public final class WebServerJerseyMain {

    private WebServerJerseyMain() {
    }

    /**
     * 运行Jersey WebServer示例。
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        // 配置日志记录，以避免标准的JVM默认设置
        LogManager.getLogManager().readConfiguration(WebServerJerseyMain.class.getResourceAsStream("/logging.properties"));

        // 最简单方法启动在8080端口
        startServer(ServerConfiguration.builder()
                .tracer(
                        ZipkinTracerBuilder.forService("my-application")
                                .zipkinHost("localhost").build()
                )
                .port(8080)
                .build());


    }

    static CompletionStage<WebServer> startServer(ServerConfiguration serverConfiguration) {

        WebServer webServer = WebServer.create(
                serverConfiguration,
                Routing.builder()
                        //在/jersey上下文根目录注册Jersey应用程序
                        .register("/jersey",
                                JerseySupport.create(new ResourceConfig(HelloWorld.class)))
                        .build());


        return webServer.start()
                .whenComplete((server, t) -> {
                    System.out.println("Jersey WebServer started.");
                    System.out.println("Try the hello world resource at: http://localhost:" + server.port() + "/jersey/hello");
                });
        // http://localhost:8080/jersey/hello
    }
}