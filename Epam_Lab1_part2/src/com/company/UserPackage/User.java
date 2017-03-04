package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 */

public class User implements Usable {
    private long qouta = 10485760;
    @Override
    public FileObject add(String path) {
        FileObject file =  new FileObject(path);
        if (qouta >= file.getFileSize()){
            qouta -= file.getFileSize();
            return new FileObject(path);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean isReady() {
        return (qouta > 0);
    }

    @Override
    public boolean read(String path) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File(path));
        } catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String path) {
        return false;
    }
}
