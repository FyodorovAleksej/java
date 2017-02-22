package com.company.GUIPackage;

import com.company.CommonUserPackage.CommonUser;
import com.company.CommonUserPackage.Order;
import com.company.CommonUserPackage.UsersList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 21.02.2017.
 */

public class LoginWindow {
    private JButton signUp = new JButton("Sign Up");

    private JButton signIn = new JButton("Sign In");
    private JTextField loginText = new JTextField(20);
    private JTextField passwordText = new JTextField(20);
    private JLabel loginTextLabel = new JLabel("Login:");
    private JLabel passwordTextLabel = new JLabel("Password:");
    private JLabel infoTextLabel = new JLabel("Info:");
    private JComboBox comboBox;
    private UsersList list;
    private JFrame window;

    public LoginWindow(String title) {
        list = new UsersList();
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.add(new CommonUser(loginText.getText(),passwordText.getText(),Order.ADMIN));
                System.out.println(list.toString());
            }
        });

        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.checkPassword(loginText.getText(),passwordText.getText())){
                    System.out.println("YES");
                }
            }
        });
        window = new JFrame(title);
        window.setLayout(new GridLayout(9,2));
        window.setSize(450,400);
        Order[] items = {Order.ADMIN,Order.USER,Order.GUEST};
        comboBox = new JComboBox(items);
        window.add(loginTextLabel);
        window.add(loginText);
        window.add(passwordTextLabel);
        window.add(passwordText);
        window.add(signIn);
        window.add(signUp);
        window.add(comboBox);
        window.add(infoTextLabel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void show(){
        window.setVisible(true);
    }

    public String toString(){
        return list.toString();
    }
}

