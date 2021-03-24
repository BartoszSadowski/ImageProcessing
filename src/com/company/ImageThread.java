package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageThread implements Runnable{

    String absolutePath;
    String file;

    public ImageThread(String absolutePath, String file) {
        this.absolutePath=absolutePath;
        this.file=file;
    }

    @Override
    public void run() {
        ImageBuffer imgbuffer = new ImageBuffer();
        BufferedImage img = imgbuffer.getImage(new File(absolutePath + "input\\" + file));
        EdgeDetection edgeDetection = new EdgeDetection();
        edgeDetection.sobelEdgeDetection(img);
        imgbuffer.saveImage(new File(absolutePath + "output\\" + file));
    }
}
