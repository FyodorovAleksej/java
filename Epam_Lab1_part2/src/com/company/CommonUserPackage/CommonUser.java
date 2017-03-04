package com.company.CommonUserPackage;

import com.company.FileObjectPackage.FileObject;
import com.company.UserPackage.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Created by Alexey on 21.02.2017.
 */
public class CommonUser {
    private String login;
    private String password;
    private Usable order;
    private DateControll calendar = null;

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
        return out;
    }

    public FileObject add(String path){
        FileObject fileObject = order.add(path);
        if (fileObject != null) {
            calendar = new DateControll();
        }
        return fileObject;
    }

    public boolean read(String path){
        return order.read(path);
    }

    public String write() {
        String s = this.login;
        s += "\t" + this.password;
        if (calendar != null) {
            s += "\t" + this.calendar.toString();
        }
        else{
            s += "\t" + "";
        }
        s += "\t" + order.getQouta().toString();
        return s;
    }

    public static CommonUser readFromFile(String buffer) {
        String[] strings = buffer.split("\t");
        if (strings.length < 3){
            return null;
        }
        Order order;
        CommonUser user;
        if (strings[3].equals("")) {
            order = Order.GUEST;
        }
        else {
            if (Long.valueOf(strings[3]) >= 0) {
                order = Order.USER;
            }
            else{
                order = Order.ADMIN;
            }
        }
        user = new CommonUser(strings[0],Encryptor.encrypt(strings[1],strings[0]),order);
        if (order.equals(Order.USER)){
            if (!strings[2].equals("") && new DateControll().moreThan(DateControll.valueOf(strings[2]))){
                user.order.refresh();
            }
            else{
                user.order.setQouta(Long.valueOf(strings[3]));
            }
        }
        return user;
    }
    public String getLogin(){
        return login;
    }
}
