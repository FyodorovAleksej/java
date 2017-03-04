package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 */
public class Admin implements Usable {
    @Override
    public FileObject add(String path) {
        return new FileObject(path);
    }

    @Override
    public boolean isReady() {
        return true;
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
        File file = new File(path);
        return file.delete();
    }
    public Long getQouta(){
        return (long)(-1);
    }

    @Override
    public void refresh() {
    }

    @Override
    public void setQouta(long new_qouta) {
    }
}
