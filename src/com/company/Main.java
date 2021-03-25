package com.company;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers;
        String path = "D:\\JavaProjects\\images\\";
        final File folder = new File(path + "input");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {

                /// run with threads with limits
                workers = Stream
                    .generate(() -> new Thread(new ImageThread(countDownLatch, path, file.getName())))
                    .limit(5)
                    .collect(toList());
                workers.forEach(Thread::start);

                /// run without threads
//                ImageProcessing imageProcessing = new ImageProcessing(path, file.getName());
//                imageProcessing.detection();


                /// run with threads
//                Thread imageThread = new Thread(new ImageThread(path, file.getName()));
//                imageThread.start();


                // run with Executor
                ExecutorService executor = Executors.newFixedThreadPool(5);
                executor.submit(() -> {
                    ImageProcessing imageProcessing = new ImageProcessing(path, file.getName());
                    imageProcessing.detection();
                });
            }
        }
    }

}
