package com.company;


import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker implements Runnable {
    private int y;
    private int i;
    private BufferedImage img;
    public int[] edgeColorsRow;

    AtomicInteger maxGradient = new AtomicInteger(-1) ;
    public Worker(int y, int i, BufferedImage img) {
        this.y=y;
        this.i=i;
        this.img=img;
        edgeColorsRow = new int[img.getHeight()];
    }

    public synchronized int[] getEdgeColorsRow(){
        return edgeColorsRow;
    }

    @Override
    public void run() {
//        System.out.println("THread run");
        for (int j = 1; j < y - 1; j++) {

            int val00 = getGrayScale(img.getRGB(i - 1, j - 1));
            int val01 = getGrayScale(img.getRGB(i - 1, j));
            int val02 = getGrayScale(img.getRGB(i - 1, j + 1));

            int val10 = getGrayScale(img.getRGB(i, j - 1));
            int val11 = getGrayScale(img.getRGB(i, j));
            int val12 = getGrayScale(img.getRGB(i, j + 1));

            int val20 = getGrayScale(img.getRGB(i + 1, j - 1));
            int val21 = getGrayScale(img.getRGB(i + 1, j));
            int val22 = getGrayScale(img.getRGB(i + 1, j + 1));

            int gx = ((-1 * val00) + (0 * val01) + (1 * val02))
                    + ((-2 * val10) + (0 * val11) + (2 * val12))
                    + ((-1 * val20) + (0 * val21) + (1 * val22));

            int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02))
                    + ((0 * val10) + (0 * val11) + (0 * val12))
                    + ((1 * val20) + (2 * val21) + (1 * val22));

            double gval = Math.sqrt((gx * gx) + (gy * gy));
            int g = (int) gval;

            if (maxGradient.get() < g) {
                maxGradient.set(g);
            }
            edgeColorsRow[j] = g;
        }
//        for(int i = 0; i<edgeColorsRow.length;i++)
//            System.out.println(edgeColorsRow[i]);
        //outputScraper.add(maxGradient);
    }


    private static int getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        //int gray = (r + g + b) / 3;

        return gray;
    }

}
