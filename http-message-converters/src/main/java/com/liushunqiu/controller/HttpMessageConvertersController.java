package com.liushunqiu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class HttpMessageConvertersController {
	@RequestMapping("/converters")
	public User hello() {
		User user = new User();
		user.setId(1);
		return user;
	}

	class User implements Serializable{
		private String name;

		private Integer id;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
	}
}
