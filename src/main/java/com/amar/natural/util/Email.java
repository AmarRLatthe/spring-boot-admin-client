package com.amar.natural.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
	private static final Logger log = LoggerFactory.getLogger(Email.class.getName());

	public static boolean sendMail(String from, String password, String to, String subject, String content) {

		EmailData obj = new EmailData();

		obj.setContent(content);
		obj.setFrom(from);
		obj.setPassword(password);
		obj.setSubject(subject);
		obj.setTo(to);
		obj.setIsMultipleRec(false);

		EmailQueueDataInserter.getElasticDataInserter().putObjectInQ(obj);

		return true;
	}

	public static boolean sendMailOptAttachment(String from, String password, String to, String subject, String content,
			boolean attachFile, String filePath) {

		EmailData obj = new EmailData();
		obj.setContent(content);
		obj.setFrom(from);
		obj.setPassword(password);
		obj.setSubject(subject);
		obj.setTo(to);
		obj.setIsMultipleRec(false);
		obj.setFilePath(filePath);
		obj.setAttachment(attachFile);

		EmailQueueDataInserter.getElasticDataInserter().putObjectInQ(obj);

		return true;
	}

	public static boolean sendMailToMultipleRecipent(String from, String password, String[] EmailTo, String subject,
			String content) {

		EmailData obj = new EmailData();

		obj.setContent(content);
		obj.setFrom(from);
		obj.setPassword(password);
		obj.setSubject(subject);
		obj.setToMulti(EmailTo);
		obj.setIsMultipleRec(true);

		EmailQueueDataInserter.getElasticDataInserter().putObjectInQ(obj);

		return true;
	}
}
