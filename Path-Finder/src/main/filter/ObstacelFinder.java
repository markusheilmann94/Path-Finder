package main.filter;


import java.awt.*;
import java.awt.image.BufferedImage;
import main.StartAndEndPoint;


public class ObstacelFinder {

    public BufferedImage ObstacelFinding(BufferedImage img, int requiredTH) {
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
                
					for(int pY = 0 ; pY < 5 ; pY++ ) {
						for(int pX = 0 ; pX < 5 ; pX++ ) {
							
							if( ( x + pX -2 ) >= 0 && ( x + pX -2 ) < width && ( y + pY -2 ) >= 0 && ( y + pY -2 ) < height && !( ( pX == 2 ) && ( pY == 2 ) ) ) {
							
								
			                color = new Color(img.getRGB( ( x + pX -2 ) , ( y + pY -2 ) ) );
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
    
    
    
    public BufferedImage completObstacelFinding( BufferedImage preProcessed , BufferedImage orignalPicture ) {
 
    	int height = preProcessed.getHeight();
        int width = preProcessed.getWidth();
        Color white = new Color( 255 , 255 , 255 );
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
            	
            	if( preProcessed.getRGB(x, y) == white.getRGB() ) {
            		
            		preProcessed.setRGB(x, y, orignalPicture.getRGB(x, y) );
            		
            	}
            	
            	
            }
        }   
    
        return preProcessed;
    }
    
    public boolean findStartAndEndPoint( BufferedImage originalP , StartAndEndPoint p) {
    	
    	boolean foundStartPoint = false;
    	boolean foundEndPinot = false;
    	int height = originalP.getHeight();
        int width = originalP.getWidth();
    	Color currentPixelColor;
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
    	
            	currentPixelColor = new Color( originalP.getRGB( x , y ) );
            	
            	if( !foundStartPoint && currentPixelColor.getRed() >= 252 && currentPixelColor.getGreen() >= 252 && currentPixelColor.getBlue() >= 124 && currentPixelColor.getBlue() <= 130 ) {
            		
            		p.setstartx( x );
            		p.setstarty( y );
            		
            		foundStartPoint = true;
            		
            		       		
            	}
            	
            	if( !foundEndPinot && currentPixelColor.getRed() >= 124 && currentPixelColor.getRed() <= 130 && currentPixelColor.getGreen() >= 252 && currentPixelColor.getBlue() >= 252 ) {
            		
            		p.setendx( x );
            		p.setendy( y );
            		
            		foundEndPinot = true;
	
           	
            	}
            	         	
            	
            }
        }
    	
        return ( foundStartPoint && foundEndPinot );
    	
    }
}
