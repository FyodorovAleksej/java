package com.company.CommonUserPackage;

/**
 * Created by Alexey on 22.02.2017.
 */
public class Encryptor {
    public static String encrypt(String password,String login){
        if (password == null)
            return null;
        else {
            if (login == null){
                return password;
            }
            byte[] txt = password.getBytes();
            byte[] key = login.getBytes();
            byte[] res = new byte[password.length()];
            for (int i = 0; i < txt.length; i++) {
                res[i] = (byte) (txt[i] ^ key[i % key.length]);
            }
            return new String(res);
        }
    }

    /**
     * decrypt input String by key (xor)
     * @param password - encrypted string
     * @param login  - as a key
     * @return - encrypted string
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
            byte[] key = login.getBytes();

            for (int i = 0; i < password.length(); i++) {
                res[i] = (byte) (text[i] ^ key[i % key.length]);
            }
            return new String(res);
        }
    }
}
