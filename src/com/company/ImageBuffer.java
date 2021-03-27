package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBuffer {
    private BufferedImage img;

    public void setImage(File filename) {
        try {
            img = ImageIO.read(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
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
