package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessing {
    String absolutePath;
    String file;
    EdgeDetection edgeDetection;
    public ImageProcessing(String absolutePath, String file) {
        this.absolutePath=absolutePath;
        this.file=file;
    }

    public void detection(){
        ImageBuffer imgbuffer = new ImageBuffer();
        BufferedImage img = imgbuffer.setImage(new File(absolutePath + "input\\" + file));
        edgeDetection = new EdgeDetection(img);
        edgeDetection.sobelEdgeDetection();
        imgbuffer.saveImage(new File(absolutePath + "output\\" + file));
    }
}
