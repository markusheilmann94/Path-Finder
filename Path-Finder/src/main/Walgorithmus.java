package main;


import java.awt.image.BufferedImage;
import java.util.*;

public class Walgorithmus {

	BufferedImage img;
	int startx , starty , endx , endy;
	int pixelBreiteDesBlocks;
	double[][] m;
	List<PathPoint> open;
	
	public Walgorithmus( BufferedImage image , int sx , int sy , int ex , int ey , int pb ) {
		
		img = image;
		pixelBreiteDesBlocks = pb;
		startx = sx/pixelBreiteDesBlocks;
		starty = sy/pixelBreiteDesBlocks;
		endx = ex/pixelBreiteDesBlocks;
		endy = ey/pixelBreiteDesBlocks;
		
	
		
		m = new double[img.getHeight()/pixelBreiteDesBlocks][img.getWidth()/pixelBreiteDesBlocks];
		
		for(int i = 0 ; i < img.getHeight()/pixelBreiteDesBlocks ; i++) {
			for(int j = 0 ; j < img.getWidth()/pixelBreiteDesBlocks ; j++) {
			
				m[i][j] = -1;
			
			}
		}
		
		m[starty][startx] = 0;
			
		open = new ArrayList<>();
		
		open.add(new PathPoint(startx,starty, Math.sqrt( Math.pow((endx - startx), 2) + Math.pow((endy - starty), 2)) ) ); // Startpunkt
		
	
	}
	
	
	public BufferedImage draw( BufferedImage image ) {
				
		for(int i = 0 ; i < image.getHeight()/pixelBreiteDesBlocks ; i++) {
			for(int j = 0 ; j < image.getWidth()/pixelBreiteDesBlocks ; j++) {
			
				if( m[i][j] >=0 ) {
					
					for(int blockY = 0 ; blockY < pixelBreiteDesBlocks ; blockY++) {
						for(int blockX = 0 ; blockX < pixelBreiteDesBlocks ; blockX++) {
						
							image.setRGB( ( j * pixelBreiteDesBlocks + blockX ) , ( i * pixelBreiteDesBlocks + blockY ) , 0xFFFF1111 );
					
						}
					}
				}
			}	
		}
		
		return image;
	}
	
	public BufferedImage drawPath( BufferedImage img ) {
		
		
		
		
		
		
		
		return img;
	}
	
	
	
	
	
	
	
	
	
	public boolean step(int stepCount) {
		
		PathPoint currentPoint;
		boolean notAtEnd = true;
		
		for(int i = 0 ; i < stepCount ; i++) {
			
			if( notAtEnd && open.size() != 0 ) {
				
				currentPoint = open.get(0);
			
				for(PathPoint point : open){
					if( ( currentPoint.getcostRemaningDirectWay() + m[currentPoint.gety()][currentPoint.getx()] ) > (point.getcostRemaningDirectWay() + m[point.gety()][point.getx()]) ) {
						currentPoint = point;
					}
				
				}
			
				if( currentPoint.getx() == endx && currentPoint.gety() == endy) {
					
					notAtEnd = false;
					
				}
				else {	
					
					for(int blockY = 0 ; blockY < 3 ; blockY++ ) {
						for(int blockX = 0 ; blockX < 3 ; blockX++ ) {
							
							if( (currentPoint.getx() + blockX - 1) >= 0 && (currentPoint.getx() + blockX - 1) < m[0].length && (currentPoint.gety() + blockY - 1) >= 0 && (currentPoint.gety() + blockY - 1) < m[0].length && walkable(currentPoint.getx() + blockX - 1 , currentPoint.gety() + blockY - 1 ) && m[currentPoint.gety() + blockY - 1 ][currentPoint.getx() + blockX - 1] == -1  ) {
										
									if(blockY == 1 || blockX ==1 ) {
								
										m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] =	 m[currentPoint.gety()][currentPoint.getx()] + 1;
								
									}
									else {
								
										m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] =	 m[currentPoint.gety()][currentPoint.getx()] + Math.sqrt(2);
									}
								
									open.add(new PathPoint( currentPoint.getx() + blockX - 1 , currentPoint.gety()+ blockY - 1 ,  ( Math.sqrt( Math.pow((endx - (currentPoint.getx() + blockX - 1 ) ), 2) + Math.pow((endy - (currentPoint.gety() + blockY - 1 ) ), 2) ) * 1 ) ) );
									
							}
			
						}
					}	
									
					open.remove(currentPoint);
					
				}
								
			}
			else {
				break;
			}
			
		}
		
		return !notAtEnd || open.size() == 0;
				
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
