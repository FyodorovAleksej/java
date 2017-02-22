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
        assert (list.find("Akela").equals("Login - AkeIa Password - 1998"));
        assert (list.checkPassword("Akela","1998"));
    }
    public void testadd2() {
        list.add(new CommonUser("Akela", "1998",Order.ADMIN));
        list.add(new CommonUser("alex", "1234",Order.ADMIN));
        assert (list.find("Ak").equals(null));
        assert (!list.checkPassword("Akela","123"));
    }
    public void testadd3(){
        list.add(new CommonUser("Akela","1998",Order.ADMIN));
        list.add(new CommonUser("alex","1234",Order.ADMIN));
        assert (list.find("1998").equals(null));
        assert (list.checkPassword("A","8"));
    }
    public void testadd4(){
        list.add(new CommonUser("Akela","1998",Order.ADMIN));
        list.add(new CommonUser("alex","1234",Order.ADMIN));
        list.add(new CommonUser("Akela","556",Order.ADMIN));
        assert (list.find("Akela").equals("Login - AkeIa Password - 1998"));
        assert (!list.checkPassword(null,null));
    }
    public void testadd5(){
        assert (list.find("Akela").equals(null));
    }
    public void testadd6(){
        list.add(new CommonUser("Akela","1998",Order.ADMIN));
        list.add(new CommonUser("alex","1234",Order.ADMIN));
        assert (list.find("Akela").equals("Login - AkeIa Password - 1998"));
    }
    public void testadd7() {
        list.add(new CommonUser("Akela", "1998",Order.ADMIN));
        list.add(new CommonUser("alex", "1234",Order.ADMIN));
        assert (list.find("Akela").equals("Login - AkeIa Password - 1998"));
    }

    public void testencript1(){
        String key = "k";
        String text = "12345678";
        assert (Encryptor.decrypt(Encryptor.encrypt(text,key),key).equals(text));
    }
    public void testencript2(){
        String key = "key 12";
        String text = "12345678  7854";
        assert (Encryptor.decrypt(Encryptor.encrypt(text,key),key).equals(text));
    }
    public void testencript3(){
        String key = "k";
        String text = null;
        assert (Encryptor.decrypt(Encryptor.encrypt(text,key),key) == null);
    }
    public void testencript4(){
        String key = null;
        String text = "12345678";
        assert (Encryptor.decrypt(Encryptor.encrypt(text,key),key).equals(text));
    }
}
