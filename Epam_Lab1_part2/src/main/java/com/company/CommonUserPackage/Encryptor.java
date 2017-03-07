package com.company.CommonUserPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Alexey on 22.02.2017.
 * Class for encrypting and decrypting strings by using Vernam's cipher
 */
public class Encryptor {
    private static final Logger log = LogManager.getLogger(Encryptor.class);
    /**
     * method for encrypt password, by using a login
     * @param password - encrypted string
     * @param login - key, that using for encrypt password
     * @return - the result of encrypt
     */
    public static String encrypt(String password,String login){
        if (password == null)
            return null;
        else {
            if (login == null){
                return password;
            }
            byte[] txt = password.getBytes();
            byte[] key = login.toUpperCase().getBytes();
            byte[] res = new byte[password.length()];
            for (int i = 0; i < txt.length; i++) {
                res[i] = (byte) ((txt[i] ^ key[i % key.length]));
            }
            return new String(res);
        }
    }

    /**
     * decrypt input String by key
     * @param password - encrypted string
     * @param login  - as a key
     * @return - decrypted string
     */
    public static String decrypt(String password,String login){
        if (password == null){
            return null;
        }
        else {
            if (login == null){
                return password;
            }
            byte[] text = password.getBytes();
            byte[] res = new byte[password.length()];
            byte[] key = login.toUpperCase().getBytes();

            for (int i = 0; i < password.length(); i++) {
                res[i] = (byte) ((text[i] ^ key[i % key.length]));
            }
            return new String(res);
        }
    }
}
