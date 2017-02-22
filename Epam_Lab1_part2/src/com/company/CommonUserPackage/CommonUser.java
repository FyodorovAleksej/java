package com.company.CommonUserPackage;

import com.company.FileObjectPackage.FileObject;
import com.company.UserPackage.*;

import java.util.LinkedList;

/**
 * Created by Alexey on 21.02.2017.
 */
public class CommonUser {
    private String login;
    private String password;
    private Usable order;

    public CommonUser(String new_login, String new_password, Order new_order){
        this.login = new_login;
        this.password = Encryptor.encrypt(new_password,new_login);
        switch (new_order){
            case ADMIN:{
                order = new Admin();
                break;
            }
            case USER:{
                order = new User();
                break;
            }
            default:{
                order = new Guest();
                break;
            }
        }
    }

    public boolean equals(String findLogin){
        return this.login.equals(findLogin);
    }

    public boolean checkPassword(String checkingPassword){
        return Encryptor.decrypt(password,login).equals(checkingPassword);
    }

    public void setPassword(String new_password){
        password = Encryptor.encrypt(new_password,login);
    }

    public String toString(){
        String out = "Login - ";
        out += login;
        out += " Password - ";
        out += Encryptor.decrypt(password,login);
        out += order.toString();
        return out;
    }

    public boolean add(String path, int size, LinkedList<FileObject> list){
        return order.add(path,size,list);
    }

    public boolean read(String path, LinkedList<FileObject> list){
        return order.read(path, list);
    }
}
