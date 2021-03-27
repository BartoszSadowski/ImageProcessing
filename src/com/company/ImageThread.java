package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.CountDownLatch;

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
        BufferedImage img = imgbuffer.setImage(new File(absolutePath + "input\\" + file));
        edgeDetection = new EdgeDetection(img);
        edgeDetection.sobelEdgeDetection();
        imgbuffer.saveImage(new File(absolutePath + "output\\" + file));
    }
}
