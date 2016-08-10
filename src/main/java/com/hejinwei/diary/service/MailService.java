package com.hejinwei.diary.service;

import java.util.List;

import org.apache.commons.mail.EmailException;

public interface MailService {

	/**
	 * 使用apache common mail 发送纯文本邮件
	 * 
	 * @param toEmail
	 *            收件人地址
	 * @param subject
	 *            主题
	 * @param msg
	 *            邮件内容
	 */
	public void apacheSimpleMail(String toEmail, String subject, String msg) throws EmailException;

	/**
	 * 使用apache common mail 发送带附件的邮件
	 * 
	 * @param toEmail
	 *            收件人地址
	 * @param subject
	 *            主题
	 * @param msg
	 *            邮件内容
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名
	 */
	public void apacheMultiPartEmail(String toEmail, String subject, String msg, String filePath, String fileName)
			throws EmailException;

	/**
	 * 使用apache common mail 发送带附件的邮件(多个附件)
	 * 
	 * @param toEmail
	 * @param subject
	 * @param msg
	 * @param filePaths
	 */
	public void apacheMultiPartEmail(String toEmail, String subject, String msg, List<String> filePaths)
			throws EmailException;

	/**
	 * 使用apache common mail 发送html邮件
	 * 
	 * @param toEmail
	 *            收件人地址
	 * @param subject
	 *            主题
	 * @param msg
	 *            邮件内容
	 */
	public void apacheHtmlMail(String toEmail, String subject, String msg) throws EmailException;

}
