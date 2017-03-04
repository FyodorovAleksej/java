package com.company.FileObjectPackage;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Alexey on 03.03.2017.
 */
public class FileList extends DefaultListModel<FileObject>{
    public void save() {
        try {
            FileWriter writer = new FileWriter("collection.txt", false);
            String savingString;
            for (int i = 0; i < this.getSize() - 1; i++) {
                savingString = this.getElementAt(i).toSave();
                writer.write(savingString);
                writer.append('\n');
            }
            writer.write(this.getElementAt(this.getSize() - 1).toSave());
            writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static FileList read(){
        FileList list = new FileList();
        FileReader reader;
        try {
            reader = new FileReader("collection.txt");
            int character = (int)'\t';
            do {
                String path = "";
                do {
                    character = reader.read();
                    if (character == -1 || (char)character == '\t') break;
                    path += (char)character;
                } while ((char) character != '\t');
                String size = "";
                do {
                    character = reader.read();
                    if (character == -1 || (char)character == '\n') break;
                    size += (char)character;
                } while ((char) character != '\n');
                if (!(path.equals("") && !(size.equals("")))) {
                    list.add(list.getSize(),new FileObject(path));
                }
            }while (character != -1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean openCurrent(int index){
        return this.get(index).open();
    }
    public void refreshAll() {
        for (int i = 0; i < this.size(); i++) {
            String path = this.elementAt(i).getPath();
            this.set(i,new FileObject(path));
        }
    }

    @Override
    public void add(int index, FileObject element) {
        for (int i = 0; i < this.size(); i++) {
            String path = this.elementAt(i).getPath();
            if (path.equals(element.getPath())) return;
        }
        super.add(index, element);
    }
}
