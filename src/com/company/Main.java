package com.company;

import tools.ConfigReader;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        ConfigReader.Methods method = ConfigReader.getMethod();

        // idk what is this count down latch, but I did not delete that anywhere
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers;
        // images should be stored in static/images/input, just put there whatever images
        String path = "src/static/images/";
        final File folder = new File(path + "input");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {

            switch (method){
                case THREADS:
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            /// run with threads
                            Thread imageThread = new Thread(new ImageThread(countDownLatch, path, file.getName()));
                            imageThread.start();
                        }
                    }
                    break;
                case THREADS_LIMITED:
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            /// run with threads with limits
                            workers = Stream
                                    .generate(() -> new Thread(new ImageThread(countDownLatch, path, file.getName())))
                                    .limit(5)
                                    .collect(toList());
                            workers.forEach(Thread::start);
                        }
                    }
                    break;
                case NO_THREADS:
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            /// run without threads
                            ImageProcessing imageProcessing = new ImageProcessing(path, file.getName());
                            imageProcessing.detection();
                        }
                    }
                    break;
                case EXECUTOR:
                    // run with Executor
                    ExecutorService executor = Executors.newFixedThreadPool(5);
                    executor.submit(() -> {
                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                ImageProcessing imageProcessing = new ImageProcessing(path, file.getName());
                                imageProcessing.detection();
                            }
                        }
                    });
                    try {
                        System.out.println("attempt to shutdown executor");
                        executor.shutdown();
                        executor.awaitTermination(5, TimeUnit.SECONDS);
                    }
                    catch (InterruptedException e) {
                        System.err.println("tasks interrupted");
                    }
                    finally {
                        if (!executor.isTerminated()) {
                            System.err.println("cancel non-finished tasks");
                        }
                        executor.shutdownNow();
                        System.out.println("shutdown finished");
                    }
                    break;
            }
        }else {
            System.out.println("Directory is empty (list of files is null)");
        }
    }

}
