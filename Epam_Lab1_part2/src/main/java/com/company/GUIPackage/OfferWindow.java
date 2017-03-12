package com.company.GUIPackage;

import com.company.MailPackage.SenderCommon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class of offer window, that call, after press button "offer file" in ListWindow
 * Created by Alexey on 07.03.2017.
 */
public class OfferWindow {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(com.company.GUIPackage.LoginWindow.class);
    private JTextField mailField = new JTextField(20);

    private JPasswordField passwordField= new JPasswordField(20);

    private JTextField subjectField = new JTextField(500);
    private JTextArea messageField = new JTextArea();

    private GridBagConstraints grid = new GridBagConstraints();

    private JFrame window;

    //-----------------------Constructors--------------------------------------

    /**
     * constructor of offer window with input title
     * @param title - title of window
     */
    public OfferWindow(String title) {

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAction();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAction();
            }
        });

        window = new JFrame(title);
        window.setSize(new Dimension(450, 250));
        window.setMinimumSize(new Dimension(450, 250));
        window.setMaximumSize(new Dimension(451, 251));
        window.setLayout(new GridBagLayout());

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.PAGE_START;
        setGrid(0, 0, 0);
        JLabel mailTextLabel = new JLabel("input email: ");
        window.add(mailTextLabel, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1, 0, 0.5);
        window.add(mailField, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(0, 1, 0.5);
        JLabel passwordTextLabel = new JLabel("input password: ");
        window.add(passwordTextLabel, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1, 1, 0.5);
        window.add(passwordField, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 2;
        setGrid(0, 2, 0.5);
        JLabel subjectTextLabel = new JLabel("input subject: ");
        window.add(subjectTextLabel, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(0, 3, 0.5);
        window.add(subjectField, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        JLabel messageTextLabel = new JLabel("input message: ");
        setGrid(0, 4, 0.5);
        window.add(messageTextLabel, grid);

        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 2;
        setGrid(0, 5, 0.5);
        window.add(messageField, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 1;
        grid.weighty = 0;
        setGrid(0, 6, 0.5);
        window.add(sendButton, grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1, 6, 0.5);
        window.add(cancelButton, grid);

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
     * @param gridx   - GridBagLayout.gridx
     * @param gridy   - GridBagLayout.gridy
     * @param weightx - GridBagLayout.weightx
     */
    private void setGrid(int gridx, int gridy, double weightx) {
        grid.weightx = weightx;
        grid.gridx = gridx;
        grid.gridy = gridy;
    }

    //-----------------------Actions-------------------------------------------

    /**
     * Perform when button "send" was pressed
     */
    public void sendAction(){
        try {
            SenderCommon.sendByGuest(mailField.getText(), passwordField.getText(), subjectField.getText(), messageField.getText());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        window.dispose();
    }

    /**
     * Perform when button "cancel" was pressed
     */
    public void cancelAction(){
        window.dispose();
    }
}
