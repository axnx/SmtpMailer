package de.test.Mailer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Snippet {
	
	public static void main(String[] args) {
	
		final Properties mailprop = XProperties.read("config.properties");
		
		Properties props = new Properties();
	    props.put("mail.smtp.host", mailprop.getProperty("smtp_host"));
	    props.put("mail.smtp.socketFactory.port", mailprop.getProperty("smtp_port"));
	    props.put("mail.smtp.socketFactory.class",
	            "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", mailprop.getProperty("auth"));
	    props.put("mail.smtps.port", mailprop.getProperty("smtp_port")); 
	    //props.put("mail.smtps.ssl.trust", "mail.gmx.net"); 
	    //props.put("mail.smtps.starttls.enable", mailprop.getProperty("tls"));
	    props.put("mail.smtps.ssl.enable", mailprop.getProperty("ssl"));
	    Session session = Session.getDefaultInstance(props,
	        new javax.mail.Authenticator() {
	                            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(mailprop.getProperty("username"),mailprop.getProperty("password"));
	            }
	        });
	    
	    session.setDebug(true);
	   
	    try {
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(mailprop.getProperty("mail_from")));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(mailprop.getProperty("mail_to")));
	        message.setSubject("Testing Subject");
	        message.setText("Test Mail");
	
	        Transport.send(message);
	
	        System.out.println("Done");
	
	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	}
}

