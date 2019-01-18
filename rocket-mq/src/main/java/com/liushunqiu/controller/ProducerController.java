package com.liushunqiu.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProducerController {
	@Resource
	private RocketMQTemplate rocketMQTemplate;

	@RequestMapping(value = "/send",method = RequestMethod.GET)
	public String send(){
		rocketMQTemplate.send("topic-1:tag1", MessageBuilder.withPayload("aaa").build());
		return "test";
	}

	@RequestMapping(value = "/sendTransaction",method = RequestMethod.GET)
	public String sendTransaction(){
		rocketMQTemplate.sendMessageInTransaction("transaction","topic-1:tag2", MessageBuilder.withPayload("测试").build(),null);
		return "test";
	}
}
