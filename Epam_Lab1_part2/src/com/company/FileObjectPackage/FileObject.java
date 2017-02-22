package com.company.FileObjectPackage;

/**
 * Created by Alexey on 22.02.2017.
 */
public class FileObject {
    private String path;
    private int size;
    public FileObject (String new_path, int new_size){
        path = new_path;
        size = new_size;
    }


    public String getPath() {
        return path;
    }

    public int getSize() {
        return size;
    }
    public boolean equals(String s){
        return path.equals(s);
    }
}
