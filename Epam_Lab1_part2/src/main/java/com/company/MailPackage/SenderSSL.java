package com.company.MailPackage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * class for sending mails by using SSL protocol
 */
public class SenderSSL {
    //-----------------------Objects-------------------------------------------
    private String username;
    private String password;
    private Properties props;

    //-----------------------Constructors--------------------------------------

    /**
     * Set properties for getting connection and setting
     * @param username - mail of admin
     * @param password - password of mail of admin
     */
    public SenderSSL(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for sending mail with input subject
     * @param subject - the Theme of mail
     * @param text - the text of message
     * @param fromEmail - the mail of admin
     * @param toEmail - the mail address
     */
    public void send(String subject, String text, String fromEmail, String toEmail) throws RuntimeException{
        Session session = Session.getDefaultInstance(props, new Authenticator() {
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
            throw new RuntimeException(e);
        }
    }
}