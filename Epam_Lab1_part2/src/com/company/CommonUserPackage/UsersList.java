package com.company.CommonUserPackage;

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

    public CommonUser find(String login){
        for (CommonUser i : users){
            if (i.equals(login)){
                return i;
            }
        }
        return null;
    }

    public boolean checkPassword(String login,String password){
        CommonUser user = find(login);
        if (user !=null){
            return user.checkPassword(password);
        }
        else return false;
    }
    public String toString(){
        String s = "";
        for (CommonUser i : users){
            s += i.toString() + "\n";
        }
        return s;
    }
}
