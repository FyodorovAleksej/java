package com.company;

import com.company.CommonUserPackage.CommonUser;
import com.company.CommonUserPackage.Encryptor;
import com.company.CommonUserPackage.Order;
import com.company.CommonUserPackage.UsersList;
import junit.framework.TestCase;

/**
 * Created by Alexey on 21.02.2017.
 */

public class Test extends TestCase {
    private UsersList list;
    @Override
    public void setUp(){
        list = new UsersList();
    }
    @Override
    public void tearDown(){

    }

    public void testadd1(){
        list.add(new CommonUser("Akela","1998", Order.ADMIN));
        list.add(new CommonUser("alex","1234",Order.ADMIN));
        assertEquals (list.checkPassword("Akela","1998"),true);

    }
    public void testadd2() {
        list.add(new CommonUser("Akela", "1998",Order.ADMIN));
        list.add(new CommonUser("alex", "1234",Order.ADMIN));
        assertEquals (list.find("Ak"),null);
        assertEquals (!list.checkPassword("Akela","123"),true);
    }
    public void testadd3(){
        list.add(new CommonUser("Akela","1998",Order.ADMIN));
        list.add(new CommonUser("alex","1234",Order.ADMIN));
        assertEquals (list.find("1998"),null);
        assertEquals (list.checkPassword("A","8"),false);
    }
    public void testadd4(){
        list.add(new CommonUser("Akela","1998",Order.ADMIN));
        list.add(new CommonUser("alex","1234",Order.ADMIN));
        list.add(new CommonUser("Akela","556",Order.ADMIN));
        assertEquals (!list.checkPassword(null,null),true);
    }
    public void testadd5(){
        assertEquals (list.find("Akela"),null);
    }

    public void testencript1(){
        String key = "k";
        String text = "12345678";
        assertEquals (Encryptor.decrypt(Encryptor.encrypt(text,key),key),text);
    }
    public void testencript2(){
        String key = "key 12";
        String text = "12345678  7854";
        assertEquals (Encryptor.decrypt(Encryptor.encrypt(text,key),key),text);
    }
    public void testencript3(){
        String key = "k";
        String text = null;
        assertEquals (Encryptor.decrypt(Encryptor.encrypt(text,key),key), null);
    }
    public void testencript4(){
        String key = null;
        String text = "12345678";
        assertEquals (Encryptor.decrypt(Encryptor.encrypt(text,key),key),text);
    }

    public void testSave1() {
        list.add(new CommonUser("testLogin","qwerty",Order.USER));
        list.save();
        UsersList readList = UsersList.read();
        System.out.println(readList.toString());
    }
    public void testSave2() {
        list.add(new CommonUser("test2login","qrty",Order.USER));
        list.add(new CommonUser("test2login2","qrty",Order.USER));
        list.add(new CommonUser("nnn","ddddddddddddddddddddddd",Order.USER));
        list.save();
        UsersList readList = UsersList.read();
        System.out.println(readList.toString());
    }
    public void testSave3() {
        list.add(new CommonUser("test3login","hhghtgjhftjejtydjgfijff",Order.USER));
        list.save();
        UsersList readList = UsersList.read();
        System.out.println(readList.toString());
    }
}
