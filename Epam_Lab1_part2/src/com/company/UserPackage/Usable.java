package com.company.UserPackage;

import com.company.FileObjectPackage.FileObject;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Alexey on 22.02.2017.
 * interface of all roles in catalog:
 * ADMIN, GUEST, USER
 */
public interface Usable {
     /**
      * method for adding file by filepath in the catalog
      * @param path - the full path of adding file
      * @return - Object of file with full pathname
      */
     FileObject add(String path);

     /**
      * method for read file by filepath in the catalog
      * @param path - full pathname of file, that will be reading
      * @return - is file with this path exist?
      */
     boolean read(String path);

     /**
      * getting quota of this user
      * @return -1 - for ADMIN
      *       null - for GUEST
      * USER.quota - for USER
      */
     Long getQouta();

     /**
      * method for refresh value of quota
      */
     void refresh();

     /**
      * method for setting new value of quota
      * @param new_qouta - new value of quota
      */
     void setQouta(long new_qouta);

     /**
      * method for deleting file with pathname
      * @param path - full pathname of deleting file
      * @return - true - if user can delete file with pathname
      *          false - if user can't delete file with pathname
      */
     boolean delete(String path);
}
