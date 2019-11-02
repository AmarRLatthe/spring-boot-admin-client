package com.amar.natural.util;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Authenticator;

public class EmailQueueDataInserter implements Runnable {

private static final Logger LOGGER = Logger.getLogger("EmailQueueDataInserter");
	
	private BlockingQueue<EmailData> combinedDataQ = new LinkedBlockingQueue<>();
	
	Thread dataPusher = null;
	
	private EmailQueueDataInserter() {

    }
	
	/**
	 * 
	 * Singleton Helper, only loads when required
	 *
	 */
	
	private static class SingletonHelper {

        private static final EmailQueueDataInserter INSTANCE = new EmailQueueDataInserter();
    }
	
	/**
     * Thread safe get Instance implementation
     *
     * @return
     */
	
	public static synchronized EmailQueueDataInserter getElasticDataInserter() {
        return SingletonHelper.INSTANCE;
    }
	
	public void putObjectInQ(EmailData obj) {
        try {
            LOGGER.info("Putting email data in queue : " + combinedDataQ.size());
            combinedDataQ.put(obj);
        } catch (InterruptedException ex) {
            Logger.getLogger(EmailQueueDataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	
	@Override
    public void run() {
        try {
            EmailData obj;
            
            while (true) {
                obj = combinedDataQ.take();

                Thread.sleep(500); //maintain unique ids in high speed

                if (obj.isAttachment) {
                    LOGGER.log(Level.INFO, "Sending email to user : {0} --> With attachment", obj.getTo());
                    this.sendMail(obj.getFrom(), obj.getPassword(), obj.getTo(), obj.getSubject(), obj.getContent(), obj.getFilePath());
                    LOGGER.log(Level.INFO, "Email sent to user : {0}", obj.getTo());
                } else if (!obj.isMultipleRec()) {
                    LOGGER.log(Level.INFO, "Sending email to user : {0}", obj.getTo());
                    this.sendMail(obj.getFrom(), obj.getPassword(), obj.getTo(), obj.getSubject(), obj.getContent(), null);
                    LOGGER.log(Level.INFO, "Email sent to user : {0}", obj.getTo());
                } else {
                    LOGGER.log(Level.INFO, "Sending email to user : {0}", obj.getTo());
                    this.sendMailToMultipleRecipent(obj.getFrom(), obj.getPassword(), obj.getToMulti(), obj.getSubject(), obj.getContent());
                    LOGGER.log(Level.INFO, "Email sent to user : {0}", obj.getTo());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(EmailQueueDataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
	
	private boolean sendMail(String from, String password, String to, String subject, String content, String filePath) {
        boolean status = false;

        //Get the session object  
        Properties props = System.getProperties();
        // props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.host", "smtp.office365.com");
        // props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        // props.setProperty("mail.smtp.socketFactory.fallback", "false");
        // props.setProperty("mail.smtp.port", "465");
        // props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.port", "587");
        //props.setProperty("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        //props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        //compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(to, false));
            message.setSubject(subject);

            if (filePath != null && filePath.trim().length() > 0) {
                MimeBodyPart mbp1 = new MimeBodyPart();
                //mbp1.setText(content,"UTF-8","text/html");
                mbp1.setContent(content, "text/html");

                String[] pathArr = filePath.split(",");

                Multipart mp = new MimeMultipart();

                mp.addBodyPart(mbp1);
                
                for (String path : pathArr) {
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(path);
                    mbp2.setDataHandler(new DataHandler(fds));
                    String fileName = fds.getName();
                    LOGGER.info("filename is: " + fileName);
                    mbp2.setFileName(fileName);
                    mp.addBodyPart(mbp2);
                }
                message.setContent(mp);
            } else {
                message.setContent(content, "text/html");
            }
            // Send message  
            Transport.send(message);
            LOGGER.info("message sent successfully....");
            status = true;

        } catch (MessagingException mex) {
            LOGGER.info(mex.toString());
        }
        return status;
    }
	
	private boolean sendMailToMultipleRecipent(String from, String password, String[] EmailTo, String subject, String content) {
        boolean status = false;

        //Get the session object  
        Properties props = System.getProperties();
        //props.setProperty("mail.smtp.host", "smtp.office365.com");
        props.setProperty("mail.smtp.host", "smtp.office365.com");
        // props.setProperty("mail.smtp.socketFactory.fallback", "false");
        // props.setProperty("mail.smtp.port", "465");
        // props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.port", "587");
        //props.setProperty("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        //props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        //compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            int counter = 0;
            InternetAddress[] recipientAddress = new InternetAddress[EmailTo.length];
            for (String recipient : EmailTo) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.CC, recipientAddress);
            message.setSubject(subject);
            message.setContent(content, "text/html");

            // Send message  
            Transport.send(message);
            LOGGER.info("message sent successfully....");
            status = true;
        } catch (MessagingException mex) {
            LOGGER.info(mex.toString());
        }
        return status;
    }
}
