package com.company.MailPackage;

/**
 * Class for sending mails
 * Created by Alexey on 06.03.2017.
 */
public class SenderCommon {
    //-----------------------Objects-------------------------------------------
    private static String adminLogin = "Fyodorov.aleksej@gmail.com";
    private static String adminPassword = "";
    private static SenderTSL tlsSender = new SenderTSL(adminLogin, adminPassword);
    private static SenderSSL sslSender = new SenderSSL(adminLogin, adminPassword);

    //-----------------------Methods-------------------------------------------

    /**
     * static method for send email by using one of the protocols
     * @param subject - the theme of the mail
     * @param message - the text of message
     * @param address - the address
     */
    public static void send(String subject, String message, String address){
        tlsSender.send(subject, message, adminLogin, address);
        //sslSender.send(subject, message, adminLogin, address);
    }
}
