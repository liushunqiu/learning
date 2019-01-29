package com.liushunqiu.controller;

import com.liushunqiu.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEmailController {
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/simpleMail",method = RequestMethod.GET)
	public String simpleMail(){
		mailService.sendSimpleMail("402349563@qq.com","ces","内容");
		return "Test";
	}

	@RequestMapping(value = "/htmlMail",method = RequestMethod.GET)
	public String htmlMail(){
		String content="<html>\n" +
				"<body>\n" +
				"    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
				"</body>\n" +
				"</html>";
		mailService.sendHtmlMail("402349563@qq.com","ces",content);
		return "Test";
	}

	@RequestMapping(value = "/attachmentMail",method = RequestMethod.GET)
	public String attachmentMail(){
		String content="<html>\n" +
				"<body>\n" +
				"    <h3>钱大老板你好啊!</h3>\n" +
				"</body>\n" +
				"</html>";
		mailService.sendAttachmentMail("qianzhen99@126.com","钱老板",content,"D:\\5da9fad0jw8f2rxvrms3oj20e80e73yw.jpg");
		return "Test";
	}

	@RequestMapping(value = "/templateMail",method = RequestMethod.GET)
	public String templateMail(){
		mailService.sendTemplateMail("402349563@qq.com","ces");
		return "Test";
	}
}
