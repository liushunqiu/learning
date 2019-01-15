package com.liushunqiu.v2.controller;

import com.liushunqiu.annotation.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiVersion(2)
@RequestMapping("/{test}")
@RestController("TestVersionController-v2")
public class TestVersionController {

	@RequestMapping("/hello")
	public String hello(){
		return "hello v2";
	}

}
