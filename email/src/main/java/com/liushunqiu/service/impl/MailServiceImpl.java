package com.liushunqiu.service.impl;

import com.liushunqiu.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${spring.mail.from}")
	private String from;

	@Override
	public void sendSimpleMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		try {
			mailSender.send(message);
			logger.info("简单邮件已经发送。");
		} catch (Exception e) {
			logger.error("发送简单邮件时发生异常！", e);
		}
	}

	@Override
	public void sendHtmlMail(String to, String subject, String body) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			//发送html的第二个参数必须设置为true
			helper.setText(body,true);
			mailSender.send(message);
			logger.info("html邮件已经发送。");
		} catch (MessagingException e) {
			logger.error("发送html邮件发生异常！", e);
		}
	}

	@Override
	public void sendAttachmentMail(String to, String subject, String body, String fileName) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			//发送html的第二个参数必须设置为true
			helper.setText(body,true);
			FileSystemResource fileSystemResource = new FileSystemResource(new File(fileName));
			helper.addAttachment(fileName,fileSystemResource);
			mailSender.send(message);
			logger.info("附件邮件已经发送。");
		} catch (MessagingException e) {
			logger.error("发送附件邮件发生异常！", e);
		}
	}

	@Override
	public void sendTemplateMail(String to, String subject) {
		org.thymeleaf.context.Context context = new Context();
		context.setVariable("id","111");
		String emailBody = templateEngine.process("emailTemplate",context);
		this.sendHtmlMail(to,subject,emailBody);
	}
}
