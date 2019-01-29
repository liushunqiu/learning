package com.liushunqiu.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TextHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(TextHandler.class);

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payLoad  = message.getPayload();
		logger.info("接收到的内容={}",payLoad);
		session.sendMessage(new TextMessage("返回给客户端的数据,"+payLoad));
	}
}
