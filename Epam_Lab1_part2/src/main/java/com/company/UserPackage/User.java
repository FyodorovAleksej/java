package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alexey on 22.02.2017.
 * Class, that describes privileges of USER
 */

public class User implements Usable {
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(User.class);
    private final long QOUTA = 10485760;//10 Mb
    private long qouta = QOUTA;

    //-----------------------Get/Set-------------------------------------------

    /**
     * method for get value of quota if this day of this USER
     * @return - the value of quota for this USER
     */
    public Long getQouta() {
        return qouta;
    }

    /**
     * method for set value of quota for this USER
     * @param new_qouta - new value of quota
     */
    public void setQouta(long new_qouta){
        this.qouta = new_qouta;
        log.info("set quota - " + Long.toString(new_qouta));
    }

    //-----------------------Methods-------------------------------------------

    /**
     * method for adding file with pathname in catalog
     * @param path - the full path of adding file
     * @return - FileObject - the Object of adding file
     *                 null - if USER can't add this file in catalog
     */
    @Override
    public FileObject add(String path) {
        FileObject file =  new FileObject(path);
        if (qouta >= file.getFileSize()){
            qouta -= file.getFileSize();
            log.info("add performed");
            return new FileObject(path);
        }
        else {
            return null;
        }
    }

    /**
     * method for reading file with pathname by USER
     * @param path - full pathname of file, that will be reading
     * @return - true - if USER can read this file
     *          false - if USER can't read this file
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
     * @return - true - if USER have privileges for deleting file
     *          false - if USER haven't privileges for deleting file
     */
    @Override
    public boolean delete(String path) {
        return false;
    }

    /**
     * method for refresh the value of quota at end of the day
     */
    public void refresh(){
        qouta = QOUTA;
        log.info("quota was refresh");
    }

}
