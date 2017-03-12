package com.company.GUIPackage;

import com.company.CommonUserPackage.CommonUser;
import com.company.CommonUserPackage.UsersList;
import com.company.FileObjectPackage.FileList;
import com.company.FileObjectPackage.FileObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by Alexey on 24.02.2017.
 * GUI class, that show list of file and allows some operations
 */
public class ListWindow {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(ListWindow.class);
    private JButton addButton = new JButton("add new file");
    private JList  fileList;
    private JTextField findField = new JTextField(100);
    private JButton findButton =  new JButton("Find");
    private CommonUser user;
    private UsersList list;
    private FileList model;
    private JFrame window;
    private GridBagConstraints grid = new GridBagConstraints();

    //-----------------------Constructors--------------------------------------

    /**
     * Constructor of List window
     * @param new_user - current user
     * @param new_list - list of registered users
     */
    public ListWindow(CommonUser new_user, UsersList new_list){
        this.list = new_list;
        this.user = new_user;

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
            }
        });

        JButton openButton = new JButton("open");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAction();
            }
        });

        JButton deleteButton = new JButton("delete file");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findAction();
            }
        });

        JButton refreshAllButton = new JButton("refresh all");
        refreshAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAllAction();
            }
        });

        JButton offerButton = new JButton("offer file");
        offerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offerAction();
            }
        });

        window = new JFrame("Catalog");
        model = new FileList();
        model.readDB();

        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                findField.setText("");
                findButton.getActionListeners()[0].actionPerformed(new ActionEvent(findButton,34,"3"));
                model.save();
                list.save();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        fileList = new JList(model);

        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        window.setSize(500,500);
        window.setLayout(new GridBagLayout());


        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.PAGE_START;
        setGrid(0,0);
        window.add(addButton,grid);


        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1,0);
        window.add(openButton,grid);


        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(2,0);
        window.add(deleteButton,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(3,0);
        window.add(offerButton,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 3;
        setGrid(0,1);
        window.add(findField,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 1;
        setGrid(3,1);
        window.add(findButton,grid);


        grid.fill = GridBagConstraints.BOTH;
        grid.gridwidth = 4;
        grid.weighty = 2;
        setGrid(0,2);
        window.add(scrollPane,grid);


        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 4;
        grid.weighty = 0;
        setGrid(0,3);
        window.add(refreshAllButton,grid);

        fileList.setAutoscrolls(true);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //-----------------------Methods-------------------------------------------

    /**
     * start show this window
     */
    public void show(){
        window.setVisible(true);
    }

    /**
     * set grid for gridBag layout
     * @param gridx - GridBagLayout.gridx
     * @param gridy - GridBagLayout.gridy
     */
    private void setGrid(int gridx, int gridy)
    {
        grid.weightx = 0.5;
        grid.gridx = gridx;
        grid.gridy = gridy;
    }

    //-----------------------Actions-------------------------------------------

    /**
     * Perform when button "Find" was pressed
     */
    private void findAction() {
        if (!findField.getText().equals("")) {
            model.find(findField.getText());
        } else {
            model.readDB();
        }
        fileList.repaint();
        log.info("perform find - " + findField.getText());
    }

    /**
     * Perform when button "Open" was pressed
     */
    private void openAction() {
        if (fileList.getSelectedIndex() >= 0) {
            model.openCurrent(fileList.getSelectedIndex());
            log.info("perform open - " + fileList.getSelectedValue().toString());
        }
    }

    /**
     * Perform when button "Add" was pressed
     */
    private void addAction(){
        JFileChooser fileChooser = new JFileChooser("C:\\");
        if (fileChooser.showDialog(null,"Open File") == JFileChooser.APPROVE_OPTION) {
            File desktopFile = fileChooser.getSelectedFile();
            FileObject file = user.add(desktopFile.getPath());
            if (file != null) {
                addButton.setEnabled(true);
                model.add(model.size(), file);
                list.refresh(user);
                list.save();
                model.save();
                list.sendingAll(file.getPath());
            }
            else {
                addButton.setEnabled(false);
            }

        }
    }

    /**
     * Perform when button "Delete" was pressed
     */
    private void deleteAction(){
        int index = fileList.getSelectedIndex();
        if (index >= 0) {
            model.removeElementAt(index);
            if (index > 1)
                fileList.setSelectedIndex(index - 1);
        }
    }

    /**
     * Perform when button "Refresh all" was pressed
     */
    private void refreshAllAction(){
        model.refreshAll();
    }

    /**
     * Perform when button "offer" was pressed
     */
    private void offerAction(){
        OfferWindow offerWindow = new OfferWindow("Offer file");
        offerWindow.show();
    }
}
