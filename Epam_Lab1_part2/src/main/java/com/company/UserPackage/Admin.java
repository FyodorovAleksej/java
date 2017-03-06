package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alexey on 22.02.2017.
 * Class, that describes privileges of ADMIN
 */
public class Admin implements Usable {

    /**
     * method for get value of quota if this day of this ADMIN
     * @return - the value of quota for this ADMIN
     */
    public Long getQouta(){
        return (long)(-1);
    }

    /**
     * method for set value of quota for this ADMIN
     * @param new_qouta - new value of quota
     */
    @Override
    public void setQouta(long new_qouta) {
    }

    //-------------------------------------------------------------------------

    @Override
    public FileObject add(String path) {
        return new FileObject(path);
    }

    /**
     * method for reading file with pathname by USER
     * @param path - full pathname of file, that will be reading
     * @return - true - if ADMIN can read this file
     *          false - if ADMIN can't read this file
     */
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
    /**
     * method, that delete file with this filepath
     * @param path - full pathname of deleting file
     * @return - true - if ADMIN have privileges for deleting file
     *          false - if ADMIN haven't privileges for deleting file
     */
    @Override
    public boolean delete(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * method for refresh the value of quota at end of the day
     */
    @Override
    public void refresh() {
    }

}
