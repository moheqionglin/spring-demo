package com.moheqionglin.pgv3server;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.moheqionglin.pgv3server.pgHandler.V3ProtocolByteToMessageDecoder;
import com.moheqionglin.pgv3server.pgHandler.inbound.*;
import com.moheqionglin.pgv3server.pgHandler.outbound.V3ProtocolMessageToByteEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wanli.zhou
 * @description
 * @time 2020-02-05 14:47
 */
public class PostgresqlV3Server {

    private static final Logger log = LoggerFactory.getLogger(PostgresqlV3Server.class);

    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    private ChannelFuture channelFuture;
    private final static int port = 6543;
    private final int DEFAULT_WORKER_THREAD_CNT = Runtime.getRuntime().availableProcessors() * 2;
    private final int DEFAULT_BACKLOG = 128;

    public static void main(String[] args) throws InterruptedException {
        try {
            initLogback();
            log.info("init log success !");
        } catch (JoranException e) {
            e.printStackTrace();
        }
        PostgresqlV3Server server = new PostgresqlV3Server();
        server.startServer();


    }


    public void startServer() throws InterruptedException {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup(DEFAULT_WORKER_THREAD_CNT);

        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                .addLast(new V3ProtocolByteToMessageDecoder())
                                .addLast(new V3ProtocolMessageToByteEncoder())
                                //Start up
                                .addLast(new V3StartupMessageInboundHandler())
                                .addLast(new V3SSLRequestInboundHandler())
                                .addLast(new V3PasswordMessageInboundHandler())
                                //Simple Query
                                .addLast(new V3QueryInboundHandler())
                                //Extend Query
                                .addLast(new V3ParserInboundHandler())
                                .addLast(new V3BindInboundHandler())
                                //Common query
                                .addLast(new V3TerminateInboundHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, DEFAULT_BACKLOG)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channelFuture = bootstrap.bind(port).sync();
            log.info("Start success ! listen at " + port);
        } catch (InterruptedException e) {
            log.error("Postgresql V3 Server error", e);
        }finally {
            channelFuture.channel().closeFuture().sync();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.info("Stop Postgresql V3 server success !");
        }
    }

    private static void initLogback() throws JoranException {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(PostgresqlV3Server.class.getClassLoader().getResourceAsStream("pg-logback.xml"));
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
    }
}