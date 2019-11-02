package com.amar.natural.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceUtil {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(EnvPropertyReader.getValue(""));
		mailMessage.setSubject("Test email");
		mailMessage.setText("Nothing to read");
		javaMailSender.send(mailMessage);
	}
}
