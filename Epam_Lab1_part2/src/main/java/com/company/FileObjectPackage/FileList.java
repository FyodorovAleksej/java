package com.company.FileObjectPackage;

import com.company.DataBaseController.DataBaseProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alexey on 03.03.2017.
 * Class, that describes logic in a list of file objects
 */
public class FileList extends DefaultListModel<FileObject>{
    //-----------------------Objects-------------------------------------------
    private static final Logger log = LogManager.getLogger(FileList.class);

    //-----------------------Methods-------------------------------------------

    /**
     * method for saving all file objects from this list in a sql table "filePathSchem.fileTable" in format:
     */
    public void save() {
        DataBaseProcessor dataBaseProcessor = new DataBaseProcessor();
        dataBaseProcessor.openConnection();
        Connection connection;
        try {
            connection = dataBaseProcessor.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("Truncate table filepathschem.filetable");
            for (int i = 0; i < this.size(); i++) {
                statement.execute("Insert filepathschem.filetable values(\"" + this.elementAt(i).forSql() + "\")");
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            dataBaseProcessor.closeConnection();
        }
    }

    /**
     * method for reading all fileObject from sql table "filePathSchem.fileTable" in current object
     */
    public void readDB() {
        DataBaseProcessor dataBaseProcessor = new DataBaseProcessor();
        dataBaseProcessor.openConnection();
        Connection connection;
        try {
            connection = dataBaseProcessor.getConnection();
            Statement statement = connection.createStatement();
            this.removeAllElements();
            ResultSet set = statement.executeQuery("select FilePath from filepathschem.filetable");
            while (set.next()) {
                this.addElement(new FileObject(set.getString("FilePath")));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataBaseProcessor.closeConnection();
        }
    }
    /**
     * open file with input index
     * @param index - the index of actual file in list
     * @return - true - if system can open this file
     *          false - if system can't open this file
     */
    public boolean openCurrent(int index){
        return this.get(index).open();
    }

    /**
     * method for refreshing the size of all files in a file list
     */
    public void refreshAll() {
        for (int i = 0; i < this.size(); i++) {
            String path = this.elementAt(i).getPath();
            this.set(i,new FileObject(path));
        }
    }

    /**
     * find file with this expression from sql
     * @param findString - the expression for find in database
     */
    public void find(String findString) {
        if (findString.equals("")) {
            for (int i = 0; i < this.size(); i++) {
                this.elementAt(i).setVisible(true);
            }
            return;
        }
        for (int i = 0; i < this.size(); i++) {
            this.elementAt(i).setVisible(false);
        }
        DataBaseProcessor dataBaseProcessor = new DataBaseProcessor();
        dataBaseProcessor.openConnection();
        Connection connection;
        try {
            connection = dataBaseProcessor.getConnection();
            Statement statement = connection.createStatement();
            String s = "select FilePath from filepathschem.filetable where FilePath like " + "'%" + findString + "%'";
            ResultSet set = statement.executeQuery("select FilePath from filepathschem.filetable where FilePath like " + "'%" + findString + "%'");
            this.removeAllElements();
            FileList list = new FileList();
            while (set.next()) {
                this.addElement(new FileObject(set.getString("FilePath")));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataBaseProcessor.closeConnection();
        }
        return;
    }


    /**
     * add new element in a file list after element with input index
     * @param index - the index, after that input a file
     * @param element - element, that will be adding in a list
     */
    @Override
    public void add(int index, FileObject element) {
        for (int i = 0; i < this.size(); i++) {
            String path = this.elementAt(i).getPath();
            if (path.equals(element.getPath())) return;
        }
        super.add(index, element);
    }
}
