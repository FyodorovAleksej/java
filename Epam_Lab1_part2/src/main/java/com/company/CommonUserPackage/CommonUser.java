package com.company.CommonUserPackage;

import com.company.FileObjectPackage.FileObject;
import com.company.UserPackage.Admin;
import com.company.UserPackage.Guest;
import com.company.UserPackage.Usable;
import com.company.UserPackage.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Alexey on 21.02.2017.
 * Class of common methods of all users
 */
public class CommonUser {
    private static final Logger log = LogManager.getLogger(CommonUser.class);
    private String login;
    private String password;
    private Usable order;
    private DateControll calendar = null;
    //-----------------------------------------------------------------------------

    /**
     * Create new user with input login, password, privileges: ADMIN, USER, GUEST
     * @param new_login - login of new user
     * @param new_password - password of new user
     * @param new_order - role of new user
     */
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
        log.entry();
        log.info(new_login + " enter in the system");
        log.exit();
    }

    /**
     * Method for check on the respective login of user and input login
     * @param findLogin - login of checked user
     * @return - true - if login of current user equals input login
     *          false - if login of current user not equals input login
     */
    public boolean equals(String findLogin){
        return this.login.equals(findLogin);
    }

    /**
     * method for check on the respective password of user and input password
     * @param checkingPassword - password of checked user
     * @return - true - if this user's password equals input password
     *          false - if this user's password not equals input password
     */
    public boolean checkPassword(String checkingPassword){
        return Encryptor.decrypt(password,login).equals(checkingPassword);
    }

    /**
     * method for transform this object to string
     * @return - the result of transform
     */
    @Override
    public String toString(){
        String out = "Login - ";
        out += login;
        out += " Password - ";
        out += Encryptor.decrypt(password,login);
        return out;
    }

    /**
     * adding file with input pathname by using user privileges
     * @param path - the pathname
     * @return - the file object, that after this adding in a file list
     *         - null, if current user can't adding this file
     */
    public FileObject add(String path){
        FileObject fileObject = order.add(path);
        if (fileObject != null) {
            calendar = new DateControll();
        }
        log.info("adding " + path + " in list");
        return fileObject;
    }

    /**
     * method of open the file with input pathname by current user
     * @param path - pathname of opening file
     * @return - true - if current user can open this file
     *          false - if current user can't open this file
     */
    public boolean read(String path){
        log.info("open file " + path);
        return order.read(path);
    }

    /**
     * method for save single element of Common User in file
     * @return - String in format: "LOGIN\tPASSWORD\tCALENDAR\tQUOTA" for write in file
     */
    public String write() {
        String stringOut = this.login;
        stringOut += "\t" + this.password;
        if (calendar != null) {
            stringOut += "\t" + this.calendar.toString();
        }
        else{
            stringOut += "\t" + "";
        }
        stringOut += "\t" + order.getQouta().toString();
        return stringOut;
    }

    /**
     * method for reading single element from string from file
     * @param buffer - String from file in format: "LOGIN\tPASSWORD\tCALENDAR\tQUOTA"
     * @return - the reading element
     */
    public static CommonUser readFromFile(String buffer) {
        String[] strings = buffer.split("\t");
        if (strings.length < 3){
            log.error("illegal format of file \"login.txt\"");
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
                log.info("refresh quota for user " + strings[0]);
            }
            else{
                if (!strings[2].equals("")) {
                    user.order.setQouta(Long.valueOf(strings[3]));
                }
                else {
                    user.order.refresh();
                }
            }
            user.calendar = DateControll.valueOf(strings[2]);
        }
        return user;
    }

    /**
     * get value of login of current user
     * @return - the value of login
     */
    public String getLogin(){
        return login;
    }
}
