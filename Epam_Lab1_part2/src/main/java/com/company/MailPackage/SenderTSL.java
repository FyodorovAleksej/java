package com.company.MailPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * class for sending mails by using TSL protocol
 */
public class SenderTSL {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(SenderTSL.class);

    private String username;
    private String password;
    private Properties props;

    //-----------------------Constructors--------------------------------------

    /**
     * Set properties for getting connection and setting
     * @param username - mail of admin
     * @param password - password of mail of admin
     */
    public SenderTSL(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for sending mail with input subject
     * @param subject - the Theme of mail
     * @param text - the text of message
     * @param fromEmail - the mail of admin
     * @param toEmail - the mail address
     */
    public void send(String subject, String text, String fromEmail, String toEmail){
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
