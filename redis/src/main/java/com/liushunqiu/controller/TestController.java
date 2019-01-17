package com.liushunqiu.controller;

import com.liushunqiu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestController
public class TestController {

	@Autowired
	private RedisUtils redisUtils;

	@RequestMapping(value = "/test",method = RequestMethod.GET)
	public Map<String,String> test(){
		redisUtils.set("PICKUP_TAKE_AREA:XZ4-015345","test");
		Map<String,String> result = redisUtils.getBatchKey(Arrays.asList("PICKUP_TAKE_AREA:XZ4-037889",
				"PICKUP_TAKE_AREA:XZ4-015345",
				"PICKUP_TAKE_AREA:XZ5-0259071",
				"PICKUP_TAKE_AREA:XZ4-001020",
				"PICKUP_TAKE_AREA:XZ4-029523",
				"PICKUP_TAKE_AREA:XZ4-020462",
				"PICKUP_TAKE_AREA:XZ4-015339",
				"PICKUP_TAKE_AREA:XZ4-018918",
				"PICKUP_TAKE_AREA:XZ4-020934",
				"PICKUP_TAKE_AREA:XZ4-012590",
				"PICKUP_TAKE_AREA:XZ5-0153830",
				"PICKUP_TAKE_AREA:XZ4-029631",
				"PICKUP_TAKE_AREA:XZ4-017951",
				"PICKUP_TAKE_AREA:XZ4-006545",
				"PICKUP_TAKE_AREA:XZ5-0369114"));
		return result;
	}


	@RequestMapping(value = "/testLock",method = RequestMethod.GET)
	public String lock(){
		try {
			if (!redisUtils.lock("key",1000L)){
				return "枷锁失败";
			}
			// do something;
		} finally {
			redisUtils.unLock("key");
		}
		return "test";
	}
}
