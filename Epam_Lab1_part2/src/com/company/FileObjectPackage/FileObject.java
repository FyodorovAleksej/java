package com.company.FileObjectPackage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alexey on 22.02.2017.
 * Class, that describes file information
 */
public class FileObject extends Component {

    private String path;
    private long size;
    //--------------------------------------------------------------

    /**
     * creating new file object from file with pathname
     * @param new_path - the path of file, that will describes
     */
    public FileObject (String new_path){
        path = new_path;
        File desktopFile = new File(new_path);
        size = desktopFile.length();
    }

    /**
     * method for getting value of path
     * @return - pathname of file
     */
    public String getPath() {
        return path;
    }

    /**
     * method for getting file size of this file
     * @return - the value of file size
     */
    public long getFileSize() {
        return size;
    }

    /**
     * method for transform this file into string
     * @return - string in format: "Path - this.PATH Size - this.SIZE"
     */
    @Override
    public String toString(){
        return "Path - " + path + " Size - " + Long.toString(size);
    }

    /**
     * method, that use for save one object in file
     * @return - string in format: "PATH\tSIZE"
     */
    public String toSave(){
        String saveString = "";
        saveString += this.path;
        saveString += '\t';
        saveString += String.valueOf(this.size);
        return saveString;
    }

    /**
     * open this file by operating system
     * @return - true - if system open this file
     *          false - if system can't open this file
     */
    public boolean open(){
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File(this.path));
            return  true;
        } catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }

}
