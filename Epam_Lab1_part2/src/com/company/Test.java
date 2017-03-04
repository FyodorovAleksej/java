package com.company;

import com.company.CommonUserPackage.*;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
        list.add(new CommonUser("nnn","45",Order.USER));
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
    public void testDateControll(){
        DateControll dateControll = new DateControll(2017,04,03);
    }
    public void testSplit(){
        String s = "1\t2\t3\t4";
        String[] strings = s.split("\t");
        for (String i: strings){
            System.out.println(i);
        }
    }
    public void testDateControll1(){
        DateControll current = new DateControll();
        DateControll old = new DateControll(2017,02,03);
        assertTrue(current.moreThan(old));
    }
    public void testDateControll2(){
        DateControll current = new DateControll();
        DateControll old = new DateControll(2017,02,04);
        assertTrue(!current.moreThan(old));
    }
}
