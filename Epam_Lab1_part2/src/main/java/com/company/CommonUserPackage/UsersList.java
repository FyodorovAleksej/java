package com.company.CommonUserPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Alexey on 21.02.2017.
 * Class that contains users in system
 */
public class UsersList extends LinkedList<CommonUser>{
    private static final Logger log = LogManager.getLogger(UsersList.class);
    /**
     * Basic constructor of class - create empty list
     */
    public UsersList(){
       super();
    }

    /**
     * adding new user in list
     * @param new_User - new User, that should be adding in the list
     * @return - do operation ending successfully?
     */
    public boolean add(CommonUser new_User){
        return super.add(new_User);
    }

    /**
     * method for find user in list by his login
     * @param login - login of finding user
     * @return - CommonUser - if the find get result
     *                 null - if user with this login not contains in the list
     */
    public CommonUser find (String login){
            for (CommonUser i : this) {
                if (i.equals(login)) {
                    return i;
                }
            }
        return null;
    }


    /**
     * method for checking password for this login
     * @param login - login for checking this password
     * @param password - password of user
     * @return - true - if password of this user input correctly
     *          false - if password of this user input not correctly
     */
    public boolean checkPassword(String login,String password){
        CommonUser user = find(login);
        if (user != null) {
            return user.checkPassword(password);
        }
        else {
            return false;
        }
    }

    /**
     * method for saving the users list in file "login.txt"
     */
    public void save() {
        FileWriter writer;
        try {
            writer = new FileWriter("login.txt", false);
                for (int i = 0; i < this.size() - 1; i++) {
                    String s = this.get(i).write();
                    writer.write(s);
                    writer.append('\n');
                }
                String s = this.get(this.size() - 1).write();
                writer.write(s);
                writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method for reading the user list from file "login.txt"
     * @return - users list
     */
    public static UsersList read() {
        FileReader reader;
        UsersList list = new UsersList();
        try {
                reader = new FileReader("login.txt");
                int character = (int)'\t';
                do {
                    String buffer = "";
                    do {
                        character = reader.read();
                        if (character == -1 || (char)character == '\n') break;
                        buffer += (char)character;
                    } while ((char) character != '\n');
                    if (!buffer.equals(""))
                    list.add(CommonUser.readFromFile(buffer));
                }while (character != -1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Object method for transform users list into String
     * @return - the result of transform
     */
    @Override
    public String toString(){
        String s = "";
        for (CommonUser i : this){
            if (i != null) {
                s += i.toString() + "\n";
            }
        }
        return s;
    }

    /**
     * method for refresh information of user in list of users
     * @param user - object of user with new information
     */
    public void refresh(CommonUser user) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getLogin().equals(user.getLogin())){
                this.set(i,user);
            }
        }
    }

}
