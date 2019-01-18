package com.liushunqiu.cosumer;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "topic-1",selectorExpression = "tag2",consumerGroup = "consumer-2")
public class Tag2Consumer implements RocketMQListener<String> {

	private static final Logger logger = LoggerFactory.getLogger(Tag2Consumer.class);

	@Override
	public void onMessage(String s) {
		logger.info("consumer-group-2接收到消息={}",s);
	}
}
