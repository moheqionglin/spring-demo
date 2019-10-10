package com.moheqionglin.bizBlockNettyIOThread;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;


/**
 * @author wanli.zhou
 * @description
 * @time 2019-09-30 11:39
 */
public class CustomHandler extends ChannelInitializer<SocketChannel> {
    EventExecutorGroup executor = new DefaultEventExecutorGroup(1, null, 100, new RejectedExecutionHandler() {
        @Override
        public void rejected(Runnable task, SingleThreadEventExecutor executor) {
            System.out.println("--- reject " + task);
        }
    });
    EventExecutorGroup executor2 = new DefaultEventExecutorGroup(1, null, 100, new RejectedExecutionHandler() {
        @Override
        public void rejected(Runnable task, SingleThreadEventExecutor executor) {
            System.out.println("---| reject " + task);
        }
    });


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline()
                .addLast(new IdleStateHandler(1, 1, 1))
                .addLast(new IdelEventHandler())
                .addLast(executor, new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("register");
                        ctx.fireChannelRegistered();
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("Active");
                        ctx.fireChannelActive();
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("InActive");
                        ctx.fireChannelInactive();
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msgo) throws Exception {
                        System.out.println(System.currentTimeMillis() + " 1>> start read !!!" + Thread.currentThread().getName());
                  Thread.sleep(1 * 1000);
                        ByteBuf msg = (ByteBuf) msgo;
                        byte[] bytes = new byte[msg.readableBytes()];
                        msg.readBytes(bytes);
                        System.out.println("     " + System.currentTimeMillis() + " 1>> " + new String(bytes));

                        ByteBuf byteBuf = Unpooled.buffer(100);
                        byteBuf.writeBytes(("receive " + new String(bytes)).getBytes());
                        ctx.writeAndFlush(byteBuf);

                        //调用下一个channelInboundHandler
                        ctx.fireChannelRead(byteBuf);
                    }
                })//SimpleChannelInboundHandler 一旦channelRead0以后消息会释放。
//                .addLast(executor2, new SimpleChannelInboundHandler<ByteBuf>() {
//                    @Override
//                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//                        System.out.println(System.currentTimeMillis() + " 2>> start read !!!" + Thread.currentThread().getName());
//
//                        byte[] bytes = new byte[msg.readableBytes()];
//                        msg.readBytes(bytes);
//                        msg.resetReaderIndex();
//                        System.out.println("     " + System.currentTimeMillis() + " 2>> " + new String(bytes));
//                        ctx.channel().writeAndFlush(msg);
//                    }
//                })
//                .addLast(new ChannelDuplexHandler() {
//                    private String receive = null;
//
//                    @Override
//                    public void channelRead(ChannelHandlerContext ctx, Object msgo) throws Exception {
//                        ByteBuf msg = (ByteBuf) msgo;
//                        byte[] bytes = new byte[msg.readableBytes()];
//                        msg.readBytes(bytes);
//                        receive = new String(bytes);
//                        //调用下一个channelInboundHandler
//                        ctx.fireChannelRead(msgo);
//                    }
//
//                    @Override
//                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//                        ByteBuf byteBuf = Unpooled.buffer(100);
//                        byteBuf.writeBytes(("receive " + receive).getBytes());
//                        ctx.writeAndFlush(byteBuf);
//
//                        ctx.write(byteBuf, promise);
//                        super.write(ctx, msg, promise);
//                    }
//
//                })
        ;
    }
}

class IdelEventHandler extends ChannelInboundHandlerAdapter {
    private int lossConnectCount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.print(".");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println("已经1秒未收到客户端的消息了！ " + event.state() + " [" + lossConnectCount + "]");
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 5) {
                    System.out.println("关闭这个不活跃通道！");
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        lossConnectCount = 0;
        System.out.println("client says: " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}