package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class ImageThread implements Runnable{
    ImageProcessing imageProcessing;

    public ImageThread(String absolutePath, String file) {
        this.imageProcessing = new ImageProcessing(absolutePath, file);
    }

    @Override
    public void run() {
        imageProcessing.detection();
    }
}
