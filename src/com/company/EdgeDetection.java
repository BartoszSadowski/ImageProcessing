package com.company;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class EdgeDetection {
    BufferedImage img;
    int x;
    int y;
    public int[][] edgeColors;
    AtomicInteger maxGradient = new AtomicInteger(-1) ;

    public EdgeDetection(BufferedImage img){
        this.img=img;
        this.x = img.getWidth();
        this.y = img.getHeight();
        edgeColors = new int[x][y];
    }

    public void threadSobelEdgeDetection() {

        Worker runnable = null;
        Thread myThreads[] = new Thread[y];
        for (int i = 1; i < x - 1; i++) {
            runnable = new Worker( y,  i,  img);
            myThreads[i] = new Thread(runnable);
            myThreads[i].start();

            edgeColors[i]= runnable.getEdgeColorsRow();
            maxGradient= runnable.maxGradient;
        }

        for (int i = 1; i < x - 1; i++) {
            try {
                myThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double scale = 255.0 / maxGradient.get();

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int) (edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                img.setRGB(i, j, edgeColor);
            }
        }
    }

    public static int getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
//        int gray = (r + g + b) / 3;

        return gray;
    }


    public void sobelEdgeDetection(){
        for (int i = 1; i < x - 1; i++) {
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

                edgeColors[i][j] = g;
            }
        }
        double scale = 255.0 / maxGradient.get();

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int) (edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                img.setRGB(i, j, edgeColor);
            }
        }
    }

}


//
//CountDownLatch countDownLatch = new CountDownLatch(5);
//    List<Thread> workers;

//    //System.out.println("run");
////            workers = Stream
////                    .generate(() -> new Thread(new Worker(countDownLatch, y, finalI, img)))
////                    .limit(5)
////                    .collect(toList());
////            workers.forEach(Thread::start);
//    //countLine(i);
//    //workers.add(new Thread(new Worker(countDownLatch, y, finalI, img)));
////            System.out.println(workers.);
//    Thread worker = new Thread(new Worker(countDownLatch, y, finalI,img));
//            worker.start();
//
//}
//
//        try {
//                countDownLatch.await();
//                } catch (InterruptedException e) {
//                System.out.println("Something goes wrong with threads");
//                }
//                System.out.println("thread stop123333333333333333");