package com.company.MailPackage;

/**
 * Class for sending mails
 * Created by Alexey on 06.03.2017.
 */
public class SenderCommon {
    //-----------------------Objects-------------------------------------------
    private static String adminLogin = "Fyodorov.aleksej@gmail.com";
    private static String adminPassword = "";

    //-----------------------Methods-------------------------------------------

    /**
     * static method for sendByAdmin email by using one of the protocols
     * @param subject - the theme of the mail
     * @param message - the text of message
     * @param address - the address
     */
    public static void sendByAdmin(String subject, String message, String address){
        SenderTSL tlsSender = new SenderTSL(adminLogin, adminPassword);
        //SenderSSL sslSender = new SenderSSL(adminLogin, adminPassword);
        tlsSender.send(subject, message, adminLogin, address);
        //sslSender.sendByAdmin(subject, message, adminLogin, address);
    }
    public static void sendByGuest(String login, String password, String subject, String message){
        SenderTSL tlsSender = new SenderTSL(login, password);
        tlsSender.send(subject, message, login, adminLogin);
        //SenderSSL sslSender = new SenderSSL(login, password);
        //sslSender.send(subject, message, login, adminLogin);
    }
}
