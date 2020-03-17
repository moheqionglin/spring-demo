package com.moheqionglin.remotecontroll;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : RemoteController
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-17 15:01
 */

public class RemoteCommandServer implements ApplicationContextAware {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ServerBootstrap bootstrap = new ServerBootstrap();

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ChannelFuture sync = null;
    private List<CommandHandler> handlers = new ArrayList<>();
    @Value("${rmc.port:8111}")
    private int port;

    /**
     * start netty server
     */
    @PostConstruct
    public void init(){
        log.info("call Post Construct...");
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024 * 2));
                            ch.pipeline().addLast(new TelnetResponseEncoder());
                            ch.pipeline().addLast(new CommandDispatcherTelnetHandler(handlers));
                        }
                    })
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            sync = bootstrap.bind(port).sync();

        } catch (InterruptedException e) {
            log.error("Telnet Netty Server InterruptedException error", e);
            closeNettyServer();
        }

        log.info("Telnet Netty server is listening on " + port);
    }

    @PreDestroy
    public void destroy(){
        log.info("Start stop telnet server.");
        closeNettyServer();
        log.info("Finish stop telnet server.");
    }

    private void closeNettyServer() {
        try {
            if(sync != null){
                sync.channel().closeFuture().sync();
            }
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (InterruptedException ex) {
            log.error("colse netty server error", ex);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("set application context");
        Map<String, CommandHandler> controllableBeans = applicationContext.getBeansOfType(CommandHandler.class);
        for (Map.Entry<String, CommandHandler> entry : controllableBeans.entrySet()) {
            log.info("Added Handler {} ", entry.getKey());
            handlers.add(entry.getValue());
        }
        log.info("set application context " + handlers.size());
    }
}