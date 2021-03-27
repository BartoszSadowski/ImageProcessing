package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

// unused class, delete?
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
        imgbuffer.setImage(new File(absolutePath + "input\\" + file));
        edgeDetection = new EdgeDetection(imgbuffer.getImage());
        edgeDetection.sobelEdgeDetection();
        imgbuffer.saveImage(new File(absolutePath + "output\\" + file));
    }
}
