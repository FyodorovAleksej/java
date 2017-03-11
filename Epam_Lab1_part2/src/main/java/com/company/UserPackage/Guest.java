package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alexey on 22.02.2017.
 * Class, that describers privileges of GUEST (sign In without registration)
 */
public class Guest implements Usable {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(Guest.class);

    //-----------------------Get/Set-------------------------------------------

    /**
     * method for get value of quota if this day of this GUEST
     * @return - the value of quota for this GUEST
     */
    public Long getQouta() {
        return null;
    }

    /**
     * method for set value of quota for this GUEST
     * @param new_qouta - new value of quota
     */
    @Override
    public void setQouta(long new_qouta) {
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for adding file with pathname into catalog
     * @param path - the full path of adding file
     * @return - FileObject - Object of adding file
     *         - null - if GUEST can't adding file
     */
    @Override
    public FileObject add(String path) {
        return null;
    }

    /**
     * method for reading file with pathname by USER
     * @param path - full pathname of file, that will be reading
     * @return - true - if GUEST can read this file
     *          false - if GUEST can't read this file
     */
    @Override
    public boolean read(String path) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File(path));
            log.info("open performed");
        } catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * method, that delete file with this filepath
     * @param path - full pathname of deleting file
     * @return - true - if GUEST have privileges for deleting file
     *          false - if GUEST haven't privileges for deleting file
     */
    @Override
    public boolean delete(String path) {
        return false;
    }

    /**
     * method for refresh the value of quota at end of the day
     */
    @Override
    public void refresh() {
    }

}
