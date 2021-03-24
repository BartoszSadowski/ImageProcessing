package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        String path = "D:\\JavaProjects\\images\\";
        final File folder = new File(path + "input");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
                ImageBuffer imgbuffer = new ImageBuffer();
                BufferedImage img = imgbuffer.getImage(new File(path + "input\\" + file.getName()));
                EdgeDetection edgeDetection = new EdgeDetection();
                edgeDetection.sobelEdgeDetection(img);
                imgbuffer.saveImage(new File(path + "output\\" + file.getName()));
            }
        }



    }


}
