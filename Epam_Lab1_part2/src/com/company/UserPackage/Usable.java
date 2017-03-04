package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 */
public interface Usable {
     FileObject add(String path);
     boolean isReady();
     boolean read(String path);
     boolean delete(String path);
     Long getQouta();
     void refresh();
     void setQouta(long new_qouta);
}
