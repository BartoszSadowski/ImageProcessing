package com.company;
import java.io.File;
public class Main {

    public static void main(String[] args) {


        String path = "D:\\JavaProjects\\images\\";
        final File folder = new File(path + "input");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Thread imageThread = new Thread(new ImageThread(path, file.getName()));
                imageThread.start();
            }
        }



    }


}
