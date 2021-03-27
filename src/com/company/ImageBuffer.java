package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBuffer {
    public BufferedImage img;


    public BufferedImage setImage(File filename) {
        // This time, you can use an InputStream to load
        try {
            img = ImageIO.read(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public void saveImage(File filename) {
        try {
            File outputfile = new File(String.valueOf(filename));
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
