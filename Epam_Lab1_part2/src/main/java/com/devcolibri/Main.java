package com.devcolibri;

public class Main {
    private static com.devcolibri.tls.Sender tlsSender = new com.devcolibri.tls.Sender("Fyodorov.aleksej@gmail.com", "Alex004005199998");
    private static com.devcolibri.ssl.Sender sslSender = new com.devcolibri.ssl.Sender("Fyodorov.aleksej@gmail.com", "Alex004005199998");

    public static void main(String[] args){
        tlsSender.send("This is Subject", "TLS: This is text!", "Fyodorov.aleksej@gmail.com", "Fyodorov.aleksej@mail.ru");
        sslSender.send("This is Subject", "SSL: This is text!", "Fyodorov.aleksej@gmail.com", "Fyodorov.aleksej@mail.ru");
    }
}