package com.moheqionglin.remotecontroll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class TelnetResponseEncoder extends MessageToByteEncoder<CommandResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, CommandResponse rsp, ByteBuf out){

		if (rsp == null) {
			out.writeBytes("\r\n".getBytes());
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb  .append("****************")
			.append(rsp.getCommandComponentName())
			.append("****************")
			.append("\r\n")
			.append(StringUtils.trimToEmpty(rsp.getResult()))
			.append("\r\n");
		if(rsp.getAttacheData() != null && !rsp.getAttacheData().isEmpty()){
			sb.append(prettyFormateMap("> ", rsp.getAttacheData()))
				.append("\r\n");
		}

		out.writeBytes(sb.toString().getBytes());
	}
	
	private String prettyFormateMap(String prefix, Map<String, String> map) {
		final int maxKeyLength = map.entrySet().stream().map(en -> en.getKey().length()).max(Integer::compareTo).get() + 5;

		StringBuilder sb = new StringBuilder();
		map.entrySet().stream().forEach(en -> {
			String keyString = String.format("%-"+ maxKeyLength + "s", en.getKey() + ":");

			sb.append(prefix)
			  .append(keyString)
			  .append(String.format("%-20s", en.getValue()))
			  .append("\r\n");
		});

		
		return sb.toString();
	}
	
}
