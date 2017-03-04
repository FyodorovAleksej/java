package com.company.GUIPackage;

import com.company.CommonUserPackage.CommonUser;
import com.company.FileObjectPackage.FileList;
import com.company.FileObjectPackage.FileObject;

import javax.swing.*;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Alexey on 24.02.2017.
 */
public class ListWindow {
    private JButton addButton = new JButton("add new file");
    private JButton openButton = new JButton("open");
    private JButton deleteButton = new JButton("delete file");
    private JButton refreshAllButton = new JButton("refresh all");
    private JList  fileList;
    private CommonUser user;
    private FileList model;
    private GridBagConstraints grid = new GridBagConstraints();
    public ListWindow(CommonUser new_user){
        this.user = new_user;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\");
                if (fileChooser.showDialog(null,"Open File") == JFileChooser.APPROVE_OPTION) {
                    File desktopFile = fileChooser.getSelectedFile();
                    FileObject file = user.add(desktopFile.getPath());
                    if (file != null) {
                        addButton.setEnabled(true);
                        model.add(model.size(), file);
                    }
                    else {
                        addButton.setEnabled(false);
                    }
                }

            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileList.getSelectedIndex() >= 0) {
                    System.out.println(fileList.getSelectedValue().toString());
                    model.openCurrent(fileList.getSelectedIndex());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = fileList.getSelectedIndex();
                if (index >= 0) {
                    model.removeElementAt(index);
                    if (index > 1)
                        fileList.setSelectedIndex(index - 1);
                }
            }
        });
        refreshAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.refreshAll();
            }
        });
        JFrame window = new JFrame();
        model = FileList.read();
        if (model == null) {
            model = new FileList();
        }
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                model.save();
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
        JScrollBar scrollBar = new JScrollBar(1);
        fileList.add(scrollBar);
        fileList.setAutoscrolls(true);
        window.setSize(500,500);
        window.setLayout(new GridBagLayout());


        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.PAGE_START;
        setGrid(0,0,0.5);
        window.add(addButton,grid);


        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(1,0,0.5);
        window.add(openButton,grid);


        grid.fill = GridBagConstraints.HORIZONTAL;
        setGrid(2,0,0.5);
        window.add(deleteButton,grid);


        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 3;
        grid.weighty = 20;
        setGrid(0,1,0.5);
        window.add(fileList,grid);

        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 3;
        grid.weighty = 0;
        setGrid(0,2,0.5);
        window.add(refreshAllButton,grid);


        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setVisible(true);
    }

    public void setGrid(int gridx, int gridy, double weightx)
    {
        grid.weightx = weightx;
        grid.gridx = gridx;
        grid.gridy = gridy;
    }
}
