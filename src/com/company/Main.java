package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        ImageBuffer imgbuffer = new ImageBuffer();
        BufferedImage img = imgbuffer.getImage(new File("test.jpg"));

        EdgeDetection.sobelEdgeDetection(img);
        imgbuffer.saveImage(new File("save.jpg"));

    }


}
