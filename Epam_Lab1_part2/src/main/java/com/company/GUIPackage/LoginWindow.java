package com.company.GUIPackage;

import com.company.CommonUserPackage.CommonUser;
import com.company.CommonUserPackage.Order;
import com.company.CommonUserPackage.UsersList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the class of Login Window
 * Created by Alexey on 21.02.2017.
 */

public class LoginWindow {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(LoginWindow.class);
    private JButton checkLogin;
    private JTextField loginText = new JTextField(20);
    private JLabel infoTextLabel = new JLabel("info: ");
    private JPasswordField passwordText = new JPasswordField(20);
    private UsersList list;
    private GridBagConstraints grid = new GridBagConstraints();
    private JFrame window;

    //-----------------------Constructors--------------------------------------

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
                signUpAction();
            }
        });

        JButton signIn = new JButton("Sign In");
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInAction();
            }
        });

        JButton signAsGuest = new JButton("Sign as guest");
        signAsGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asGuestAction();
            }
        });

        checkLogin = new JButton("Check login");
        checkLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLoginAction();
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
        window.add(infoTextLabel,grid);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //-----------------------Methods-------------------------------------------

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
    private void setGrid(int gridx, int gridy, double weightx)
    {
        grid.weightx = weightx;
        grid.gridx = gridx;
        grid.gridy = gridy;
    }

    //-----------------------Actions-------------------------------------------

    /**
     * Perform when button "SignUp" was pressed
     */
    private void signUpAction() {
        if (loginText.getText().matches(".*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(ru|aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$")) {
            list.add(new CommonUser(loginText.getText(), passwordText.getText(), Order.USER));
        } else {
            infoTextLabel.setText("Username not correct - not email. ");
        }
        if (passwordText.getText().equals("")) {
            infoTextLabel.setText(infoTextLabel.getText() + "Input password");
        }
    }
    /**
     * Perform when button "SignIn" was pressed
     */
    private void signInAction() {
        if (loginText.getText().equals("Fyodorov.aleksej@gmail.com") && passwordText.getText().equals("Akela1998")) {
            ListWindow listWindow = new ListWindow(new CommonUser(loginText.getText(), passwordText.getText(), Order.ADMIN), list);
            listWindow.show();
            window.dispose();
        }
        if (list.checkPassword(loginText.getText(), passwordText.getText())) {
            ListWindow listWindow = new ListWindow(new CommonUser(loginText.getText(), passwordText.getText(), Order.USER), list);
            listWindow.show();
            list.save();
            window.dispose();
        }
    }
    /**
     * Perform when button "Sign as a guest" was pressed
     */
    private void asGuestAction() {
        ListWindow listWindow = new ListWindow(new CommonUser(null, null, Order.GUEST), list);
        listWindow.show();
        System.out.println("Join as guest\n");
        window.dispose();
    }
    /**
     * Perform when button "Check Login" was pressed
     */
    private void checkLoginAction() {
        if (list.find(loginText.getText()) == null) {
            checkLogin.setBackground(new Color(40, 255, 255));
        } else {
            checkLogin.setBackground(new Color(255, 40, 40));
        }
    }
}