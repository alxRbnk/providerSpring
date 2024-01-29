package com.rubnikovich.provider.mail;


import com.rubnikovich.provider.util.CustomPassword;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LogManager.getLogger();

    public void sendMail(String sendTo, String currentMessage) {
        String from = "mynogood@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mynogood@gmail.com", CustomPassword.mailPassword);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setSubject("This is the Subject Line!");
            message.setText(currentMessage);
            Transport.send(message);
            logger.info("Sent message successfully");
        } catch (MessagingException mex) {
            logger.info("email sending error");
        }
    }

}



