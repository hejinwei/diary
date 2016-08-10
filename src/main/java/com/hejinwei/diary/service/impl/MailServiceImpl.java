package com.hejinwei.diary.service.impl;

import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

import com.hejinwei.diary.service.MailService;
import com.hejinwei.diary.util.Constants;

@Service
public class MailServiceImpl implements MailService {

	private static final String CODING = "UTF-8";

	@Override
	public void apacheSimpleMail(String toEmail, String subject, String msg) throws EmailException {

		String hostname = Constants.MAIL_HOSTNAME;
		String popUsername = Constants.MAIL_POP_USERNAME;
		String popPassword = Constants.MAIL_POP_PASSWORD;
		String username = Constants.MAIL_USERNAME;

		SimpleEmail email = new SimpleEmail();
		email.setHostName(hostname);
		email.setAuthentication(popUsername, popPassword);
		email.setCharset(CODING);

		email.addTo(toEmail);
		email.setFrom(popUsername, username);
		email.setSubject(subject);
		email.setMsg(msg);
		email.send();

	}

	@Override
	public void apacheMultiPartEmail(String toEmail, String subject, String msg, String filePath, String fileName)
			throws EmailException {

		String hostname = Constants.MAIL_HOSTNAME;
		String popUsername = Constants.MAIL_POP_USERNAME;
		String popPassword = Constants.MAIL_POP_PASSWORD;
		String username = Constants.MAIL_USERNAME;

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(hostname);
		email.setAuthentication(popUsername, popPassword);
		email.setCharset(CODING);

		email.addTo(toEmail);
		email.setFrom(popUsername, username);
		email.setSubject(subject);
		email.setMsg(msg);
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(filePath);// 本地文件
		// attachment.setURL(new URL(filePath));//远程文件filePath
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(fileName);
		attachment.setName(fileName);// fileName

		email.attach(attachment);
		email.send();

	}

	@Override
	public void apacheMultiPartEmail(String toEmail, String subject, String msg, List<String> filePaths)
			throws EmailException {

		String hostname = Constants.MAIL_HOSTNAME;
		String popUsername = Constants.MAIL_POP_USERNAME;
		String popPassword = Constants.MAIL_POP_PASSWORD;
		String username = Constants.MAIL_USERNAME;

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(hostname);
		email.setAuthentication(popUsername, popPassword);
		email.setCharset(CODING);

		email.addTo(toEmail);
		email.setFrom(popUsername, username);
		email.setSubject(subject);
		email.setMsg(msg);

		for (String filePath : filePaths) {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(filePath);// 本地文件
			// attachment.setURL(new URL(filePath));//远程文件filePath
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription(filePath.substring(0, filePath.lastIndexOf(".") - 1));
			attachment.setName(filePath.substring(0, filePath.lastIndexOf(".") - 1));// fileName

			email.attach(attachment);
		}

		email.send();

	}

	@Override
	public void apacheHtmlMail(String toEmail, String subject, String msg) throws EmailException {
		String hostname = Constants.MAIL_HOSTNAME;
		String popUsername = Constants.MAIL_POP_USERNAME;
		String popPassword = Constants.MAIL_POP_PASSWORD;
		String username = Constants.MAIL_USERNAME;

		HtmlEmail email = new HtmlEmail();
		email.setHostName(hostname);
		email.setAuthentication(popUsername, popPassword);
		email.setCharset(CODING);

		email.addTo(toEmail);
		email.setFrom(popUsername, username);
		email.setSubject(subject);
		// email.setHtmlMsg("<b>关于论文最后答辩时间</b><br/><div>2013-05-18</div>");
		email.setHtmlMsg(msg);
		email.send();

	}

}
