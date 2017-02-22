package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 */
public interface Usable {
     boolean add(String path, int size, LinkedList<FileObject> list);
     boolean read(String path, LinkedList<FileObject> list);
}
