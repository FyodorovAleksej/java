package com.company.CommonUserPackage;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Alexey on 21.02.2017.
 */
public class UsersList{
    private LinkedList<CommonUser> users;
    public UsersList(){
        users = new LinkedList<>();
    }

    public void add(CommonUser new_User){
        users.add(new_User);
    }

    public CommonUser find (String login){
            for (CommonUser i : users) {
                if (i.equals(login)) {
                    return i;
                }
            }
        return null;
    }

    public boolean checkPassword(String login,String password){
        CommonUser user = find(login);
        if (user != null) {
            return user.checkPassword(password);
        }
        else {
            return false;
        }
    }

    public void save() {
        FileWriter writer;
        try {
            writer = new FileWriter("login.txt", false);
                for (int i = 0; i < users.size() - 1; i++) {
                    String s = users.get(i).write();
                    writer.write(s);
                    writer.append('\n');
                }
                String s = users.get(users.size() - 1).write();
                writer.write(s);
                writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public String toString(){
        String s = "";
        for (CommonUser i : users){
            if (i != null) {
                s += i.toString() + "\n";
            }
        }
        return s;
    }
    public void refresh(CommonUser user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(user.getLogin())){
                users.set(i,user);
            }
        }
    }

}
