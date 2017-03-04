package com.company.FileObjectPackage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alexey on 22.02.2017.
 */
public class FileObject extends Component {
    private String path;
    private long size;
    public FileObject (String new_path){
        path = new_path;
        File desktopFile = new File(new_path);
        size = desktopFile.length();
    }

    public String getPath() {
        return path;
    }

    public long getFileSize() {
        return size;
    }
    public boolean equals(String s){
        return path.equals(s);
    }
    public String toString(){
        return "Path - " + path + " Size - " + Long.toString(size);
    }
    public String toSave(){
        String saveString = "";
        saveString += this.path;
        saveString += '\t';
        saveString += String.valueOf(this.size);
        return saveString;
    }
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
