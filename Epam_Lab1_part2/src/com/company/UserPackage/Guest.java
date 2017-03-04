package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 */
public class Guest implements Usable {
    @Override
    public FileObject add(String path) {
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
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
