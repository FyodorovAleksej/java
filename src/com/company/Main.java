package com.company;

import com.company.GUIPackage.LoginWindow;

/**
 * Create application "Home Cataloguer"
 * 1) catalog of documents, music, video, books
 * 2) use Java library swing
 * 3) there are some roles, such as ADMINISTRATOR, USER, GUEST
 * 4) ADMINISTRATOR can add files in catalog
 * 5) USER can add file with a limit of 10 Mb per day
 * 6) GUEST can read all files
 * 7) support reading and finding files in catalog
 * 8) password can't save in raw format
 * 9) structure of catalog save in file
 * 10)* structure of catalog save in DataBase
 * 11)* when you add a file to the catalog notification occurs on mail
 * 12)* when you find files in the catalog, results of finding show page by page
 * 13)* GUEST can offer adding of files by mail
 */
public class Main {

    public static void main(String[] args) {
        LoginWindow window = new LoginWindow("Login Window");
        window.show();
    }
}
