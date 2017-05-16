package main.filter;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Threshold {

    public BufferedImage thImage(BufferedImage img, int requiredTH) {
        int height = img.getHeight();
        int width = img.getWidth();

        Color white = new Color(255, 255, 255);
        int rgbWhite = white.getRGB();

        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int red = 0;
        int green = 0;
        int blue = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(img.getRGB(x, y));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                if ((red + green + blue) / 3 < requiredTH) {
                    convertedImage.setRGB(x,y,0); //Schwarz
                } else {
                    convertedImage.setRGB(x,y,rgbWhite); //Weiß
                }
            }
        }

        return convertedImage;
    }
}
