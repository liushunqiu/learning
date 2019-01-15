package com.liushunqiu.v1.controller;

import com.liushunqiu.annotation.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiVersion(1)
@RequestMapping("/{test}")
@RestController("TestVersionController-v1")
public class TestVersionController {

	@RequestMapping("/hello")
	public String hello(){
		return "hello v1";
	}

}
