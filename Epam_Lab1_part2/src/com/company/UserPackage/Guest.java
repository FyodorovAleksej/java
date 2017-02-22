package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 */
public class Guest implements Usable {
    @Override
    public boolean add(String path, int size, LinkedList<FileObject> list) {
        return false;
    }

    @Override
    public boolean read(String path, LinkedList<FileObject> list) {
        for (FileObject i : list){
            if (i.equals(path)){
                return true;
            }
        }
        return false;
    }
}
