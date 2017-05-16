package main;


import java.awt.image.BufferedImage;

public class Walgorithmus {

	BufferedImage img;
	int startx , starty , endx , endy;
	int pixelBreiteDesBlocks;
	
	public Walgorithmus( BufferedImage image , int sx , int sy , int ex , int ey , int pb ) {
		
		img = image;
		startx = sx/pixelBreiteDesBlocks;
		starty = sy/pixelBreiteDesBlocks;
		endx = ex/pixelBreiteDesBlocks;
		endx = ey/pixelBreiteDesBlocks;
		pixelBreiteDesBlocks = pb;
	
		
		int[][][] m = new int[img.getHeight()/pixelBreiteDesBlocks][img.getWidth()/pixelBreiteDesBlocks][2];
		
		
		
	}
	
	public BufferedImage step(int stepCount) {
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean walkable( int x , int y ) {
			
		int p;
	
		boolean w = true;
			
		for(int i = 0 ; i < pixelBreiteDesBlocks ; i++) {
			for(int j = 0 ; j < pixelBreiteDesBlocks ; j++) {
					
				p = img.getRGB( ( x * pixelBreiteDesBlocks + j ) , ( y * pixelBreiteDesBlocks + i ) );
					
				if( p != 0xFFFFFFFF) {
					w = false;	
				}
							
			}			
		}
			
		return w;
			
	}
	
}
