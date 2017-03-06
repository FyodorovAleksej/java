package com.company.GUIPackage;

import com.company.CommonUserPackage.CommonUser;
import com.company.CommonUserPackage.Order;
import com.company.CommonUserPackage.UsersList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by Alexey on 21.02.2017.
 */

public class LoginWindow {
    private JButton checkLogin = new JButton("Check login");
    private JTextField loginText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private UsersList list;
    private GridBagConstraints grid = new GridBagConstraints();
    private JFrame window;
    //-------------------------------------------------------------------------------

    /**
     * constructor of window with login
     * @param title - title of window
     */
    public LoginWindow(String title) {
        list = UsersList.read();
        JButton signUp = new JButton("Sign Up");
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.add(new CommonUser(loginText.getText(),passwordText.getText(),Order.USER));
                System.out.println(list.toString());
            }
        });

        JButton signIn = new JButton("Sign In");
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginText.getText().equals("Admin") && passwordText.getText().equals("Admin"))
                {
                    ListWindow listWindow = new ListWindow(new CommonUser(loginText.getText(),passwordText.getText(),Order.ADMIN),list);
                    listWindow.show();
                    window.dispose();
                }
                if (list.checkPassword(loginText.getText(),passwordText.getText())){
                    ListWindow listWindow = new ListWindow(new CommonUser(loginText.getText(),passwordText.getText(),Order.USER),list);
                    listWindow.show();
                    list.save();
                    window.dispose();
                }
            }
        });
        JButton signAsGuest = new JButton("Sign as guest");
        signAsGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListWindow listWindow = new ListWindow(new CommonUser(null,null,Order.GUEST),list);
                listWindow.show();
                System.out.println("Join as guest\n");
                window.dispose();
            }
        });
        checkLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.find(loginText.getText()) == null) {
                    checkLogin.setBackground(new Color(40,255,255));
                }
                else {
                    checkLogin.setBackground(new Color(255,40,40));
                }
            }
        });
        window = new JFrame(title);
        window.setSize(new Dimension(450,150));
        window.setMinimumSize(new Dimension(450,150));
        window.setMaximumSize(new Dimension(451,151));
        window.setLayout(new GridBagLayout());

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(0,0,0);
        JLabel loginTextLabel = new JLabel("Login:");
        window.add(loginTextLabel,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1,0,0.5);
        window.add(loginText,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(2,0,0.5);
        window.add(checkLogin,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(0,1,0.5);
        JLabel passwordTextLabel = new JLabel("Password:");
        window.add(passwordTextLabel,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 2;
        setGrid(1,1,0.5);
        window.add(passwordText,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 1;
        setGrid(0,2,0.5);
        window.add(signIn,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1,2,0.5);
        window.add(signUp,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(2,2,0.5);
        window.add(signAsGuest,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(0,3,0.5);
        JLabel infoTextLabel = new JLabel("Info:");
        window.add(infoTextLabel,grid);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * method for show this window
     */
    public void show() {
        window.setVisible(true);
    }

    /**
     * set grid for gridBag layout
     * @param gridx - GridBagLayout.gridx
     * @param gridy - GridBagLayout.gridy
     * @param weightx - GridBagLayout.weightx
     */
    public void setGrid(int gridx, int gridy, double weightx)
    {
        grid.weightx = weightx;
        grid.gridx = gridx;
        grid.gridy = gridy;
    }
}

