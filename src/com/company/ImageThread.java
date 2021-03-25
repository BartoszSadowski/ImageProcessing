package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageThread implements Runnable{

    String absolutePath;
    String file;
    EdgeDetection edgeDetection;
    public ImageThread(String absolutePath, String file) {
        this.absolutePath=absolutePath;
        this.file=file;
    }

    @Override
    public void run() {
        ImageBuffer imgbuffer = new ImageBuffer();
        BufferedImage img = imgbuffer.getImage(new File(absolutePath + "input\\" + file));
        edgeDetection = new EdgeDetection();
        //System.out.println(file);
        edgeDetection.sobelEdgeDetection(img);
        imgbuffer.saveImage(new File(absolutePath + "output\\" + file));
    }
}
