package main.filter;


import java.awt.*;
import java.awt.image.BufferedImage;

import main.PathPoint;


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
        boolean nearWalkable;
       

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	
            	
            	nearWalkable = false;           	
                Color color = new Color(img.getRGB(x, y));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                if ((red + green + blue) / 3 < requiredTH) {
                
					for(int pY = 0 ; pY < 3 ; pY++ ) {
						for(int pX = 0 ; pX < 3 ; pX++ ) {
							
							if( ( x + pX -1 ) >= 0 && ( x + pX -1 ) < width && ( y + pY -1 ) >= 0 && ( y + pY -1 ) < height && !( ( pX == 1 ) && ( pY == 1 ) ) ) {
							
								
			                color = new Color(img.getRGB( ( x + pX -1 ) , ( y + pY -1 ) ) );
			                red = color.getRed();
			                green = color.getGreen();
			                blue = color.getBlue();
			                
			                	if ((red + green + blue) / 3 > requiredTH) {
			                	
			                	nearWalkable = true; 
			               	
			                	}

							}
		
						}
					}	
					
                	if( nearWalkable ) {
                		
                		convertedImage.setRGB(x,y, 0xFF000000 ); //Schwarz
                	                		
                	}
                	else{
                		
                		
                		convertedImage.setRGB(x,y, img.getRGB(x, y) );
                		
                	}
					
					
					
                } else {
                                	
                	convertedImage.setRGB(x,y,rgbWhite); //Weiß
                }
                
                
                
                
                
            }
        }

        return convertedImage;
    }
}
