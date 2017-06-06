package main;


import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.Color;

public class Walgorithmus {

	BufferedImage img;
	int startx , starty , endx , endy;
	int pixelBreiteDesBlocks;
	double[][] m;
	List<PathPoint> open;
	boolean terrainAware;
	
	public Walgorithmus( BufferedImage image , StartAndEndPoint p , int pb , boolean tA ) {
		
		img = image;
		pixelBreiteDesBlocks = pb;
		startx = p.getstartx()/pixelBreiteDesBlocks;
		starty = p.getstarty()/pixelBreiteDesBlocks;
		endx = p.getendx()/pixelBreiteDesBlocks;
		endy = p.getendy()/pixelBreiteDesBlocks;
		terrainAware = tA;
	
		
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
					
					for(int blockY = 0 ; blockY < ( pixelBreiteDesBlocks - 1 ) ; blockY++) {
						for(int blockX = 0 ; blockX < ( pixelBreiteDesBlocks - 1 ) ; blockX++) {
						
							image.setRGB( ( j * pixelBreiteDesBlocks + blockX ) , ( i * pixelBreiteDesBlocks + blockY ) , 0xFFFF1111 );
					
						}
					}
				}
			}	
		}
		
		
		for(PathPoint point : open) {
			
			
			
			for(int blockY = 0 ; blockY < ( pixelBreiteDesBlocks - 1 ) ; blockY++) {
				for(int blockX = 0 ; blockX < ( pixelBreiteDesBlocks - 1 ) ; blockX++) {
				
					image.setRGB( ( point.getx() * pixelBreiteDesBlocks + blockX ) , ( point.gety() * pixelBreiteDesBlocks + blockY ) , 0xFF1111FF );
			
				}
			}
			
		}	
		
		return image;
	}
	
	public BufferedImage drawPath( BufferedImage img ) {
		
				
		if( foundPath() ) {
			
			int x = endx;
			int y = endy;
			int nextx = x;
			int nexty = y;
				
			while( !(x == startx && y == starty) ) {
						
				for(int blockY = 0 ; blockY < pixelBreiteDesBlocks ; blockY++) {
					for(int blockX = 0 ; blockX < pixelBreiteDesBlocks ; blockX++) {
				
						img.setRGB( ( x * pixelBreiteDesBlocks + blockX ) , ( y * pixelBreiteDesBlocks + blockY ) , 0xFF11FF11 );
			
					}
				}
				
				for(int mY = 0 ; mY < 3 ; mY++) {		
					for(int mX = 0 ; mX < 3 ; mX++) {
						
						if( (y + mY -1) >= 0 && (y + mY -1) < m.length && (x + mX -1) >= 0 && (x + mX -1) < m[0].length && !( (mY == 1) && (mX == 1) ) ) {
							
							if( ( m[ y + mY -1 ][ x + mX -1 ] >= 0 ) && ( m[ y + mY -1 ][ x + mX -1 ] < m[nexty][nextx] ) ) {
								
								nextx = x + mX -1;
								nexty = y + mY -1;
							}
							
						}
						
						
						
					}
				}
				
				x = nextx;
				y = nexty;			
			}	
		
		}
		

		return img;
	}
	

	private boolean inOpen( int x , int y ) {
		
		boolean includet = false;
		
		for(PathPoint point : open){
		
			if( ( point.getx() == x ) && ( point.gety() == y ) ){
				
				includet = true;
				
			}
		
		}
		
		return includet;
		
	}
	
	
	public boolean foundPath() {
		
		boolean p = false;
		
		if( m[endy][endx] >= 0 ) {
			
			p = true;
			
		}
		
		return p;
		
	}
	

	public boolean step(int stepCount) {
		
		PathPoint currentPoint;
		boolean notAtEnd = true;
		double t;
		
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
							
							if( (currentPoint.getx() + blockX - 1) >= 0 && (currentPoint.getx() + blockX - 1) < m[0].length && (currentPoint.gety() + blockY - 1) >= 0 && (currentPoint.gety() + blockY - 1) < m.length && walkable(currentPoint.getx() + blockX - 1 , currentPoint.gety() + blockY - 1 ) && ( ( m[currentPoint.gety() + blockY - 1 ][currentPoint.getx() + blockX - 1] < 0 ) || inOpen( ( currentPoint.getx() + blockX - 1 ) , ( currentPoint.gety() + blockY - 1 ) ) ) ) {
								
								if(  m[currentPoint.gety() + blockY - 1 ][currentPoint.getx() + blockX - 1] < 0  ) {
									
									open.add(new PathPoint( currentPoint.getx() + blockX - 1 , currentPoint.gety()+ blockY - 1 ,  ( Math.sqrt( Math.pow((endx - (currentPoint.getx() + blockX - 1 ) ), 2) + Math.pow((endy - (currentPoint.gety() + blockY - 1 ) ), 2) ) * 1 ) ) );
									
								}
								
								t = terrainAwarness( currentPoint.getx() + blockX - 1 , currentPoint.gety()+ blockY - 1 );
								
								if(blockY == 1 || blockX ==1 ) {
								
									if( ( m[currentPoint.gety() + blockY - 1 ][currentPoint.getx() + blockX - 1] < 0 ) || ( ( m[currentPoint.gety()][currentPoint.getx()] + ( 1 * t )  ) < ( m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] ) ) ) {
										m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] =	 m[currentPoint.gety()][currentPoint.getx()] + ( 1 * t );
									}
								
								}
								else {
									
									if( ( m[currentPoint.gety() + blockY - 1 ][currentPoint.getx() + blockX - 1] < 0 ) || ( ( m[currentPoint.gety()][currentPoint.getx()] + ( Math.sqrt(2) * t ) ) < ( m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] ) )) {
										m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] =	 m[currentPoint.gety()][currentPoint.getx()] + ( Math.sqrt(2) * t );
									}
									
								}		
									
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
	
	public void setTerrainAware( boolean value ) {
		
		terrainAware = value;
			
	}
	

	private double terrainAwarness( int x , int y ) {
		
		double multiplikator;
		int highestValue;
		
		if( terrainAware ) {
		
		Color c = new Color( img.getRGB( ( x * pixelBreiteDesBlocks ) , ( y * pixelBreiteDesBlocks  ) ) );
		
		
		if( c.getRed() > c.getGreen() ) {
			
			highestValue = c.getGreen();
		}
		else {
			
			highestValue = c.getRed();
		}
		
		if( highestValue > c.getBlue() ) {
			
			highestValue = c.getBlue();
		}
		
		multiplikator = ( 50.0 / ( highestValue + 1 ) ) + 0.82;
		
		}
		else {
			
			multiplikator = 1.0;
		}
		
		return multiplikator;
		
	}
	
	private boolean walkable( int x , int y ) {
			
		int p;
	
		boolean w = true;
			
		for(int i = 0 ; i < pixelBreiteDesBlocks ; i++) {
			for(int j = 0 ; j < pixelBreiteDesBlocks ; j++) {
					
				p = img.getRGB( ( x * pixelBreiteDesBlocks + j ) , ( y * pixelBreiteDesBlocks + i ) );
					
				if( p == 0xFF000000) {
					w = false;	
				}
							
			}			
		}
			
		return w;
			
	}
	
}
