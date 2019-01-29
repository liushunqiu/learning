package com.liushunqiu.service;

public interface MailService {
	/**
	 * 发送文字的email
	 * @param to
	 * @param subject
	 * @param body
	 */
	void sendSimpleMail(String to,String subject,String body);

	/**
	 * 发送html的email
	 * @param to
	 * @param subject
	 * @param body
	 */
	void sendHtmlMail(String to,String subject,String body);

	/**
	 * 发送附件email
	 * @param to
	 * @param subject
	 * @param body
	 * @param fileName
	 */
	void sendAttachmentMail(String to,String subject,String body,String fileName);

	/**
	 * 发送模版email
	 * @param to
	 * @param subject
	 */
	void sendTemplateMail(String to,String subject);
}
