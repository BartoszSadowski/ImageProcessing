package com.company;

import java.awt.image.BufferedImage;

public class EdgeDetection {

    public static void sobelEdgeDetection(BufferedImage img) {
        int x = img.getWidth();
        int y = img.getHeight();

        int maxGval = 0;
        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;

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

                if (maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

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
        //int gray = (r + g + b) / 3;

        return gray;
    }

}
