package com.moheqionglin.remotecontroller;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.BlockingQueue;


public class CommandDispatcherTelnetHandler extends ChannelInboundHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	List<CommandHandler> handlers = new ArrayList<>();

	public CommandDispatcherTelnetHandler(List<CommandHandler> handlers) {
		super();
		this.handlers = handlers;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		ByteBuf bb = (ByteBuf)msg;
		StringBuilder tmp = new StringBuilder();
		while (bb.isReadable()) {
			tmp.append((char)bb.readByte());
		}
		String cmd = tmp.toString();
		String[] cmdS = cmd.split(" +");
		String command = cmdS[0];
		String args[] = Arrays.copyOfRange(cmdS, 1, cmdS.length);
		logger.info("Remote controller received command: " + command + ", args : " + StringUtils.trimArrayElements(args));

		for(CommandHandler handler: this.handlers){
			if(handler.accept(command)){
				CommandResponse rsp = null;
				try{
					rsp = handler.process(command, new CommandRequest(args));
				}catch (Exception e){
					logger.error("process error for <" + command+">  params = " + StringUtils.trimArrayElements(args), e);
					rsp = new CommandResponse("Process exception!");
				}
				if(StringUtils.isEmpty(rsp.getCommandComponentName())){
					rsp.setCommandComponentName(handler.getClass().getSimpleName());
				}
				ctx.writeAndFlush(rsp);
			}
		}
		if("quit".equals(cmd)){
			ctx.close();

		}
	}
}
