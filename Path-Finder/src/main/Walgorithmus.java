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
		endx = ey/pixelBreiteDesBlocks;
		
	
		
		m = new double[img.getHeight()/pixelBreiteDesBlocks][img.getWidth()/pixelBreiteDesBlocks];
		
		for(int i = 0 ; i < img.getHeight()/pixelBreiteDesBlocks ; i++) {
			for(int j = 0 ; j < img.getWidth()/pixelBreiteDesBlocks ; j++) {
			
				m[i][j] = -1;
			
			}
		}
		
		m[starty][startx] = 0;
			
		open = new ArrayList<>();
		
		open.add(new PathPoint(startx,starty, Math.sqrt( (endx - startx)^2 + (endy - starty)^2))); // Startpunkt
		
	}
	
	
	public int[][] draw() {
		
		int[][] matrix = new int[img.getHeight()][img.getWidth()];
		
		for(int i = 0 ; i < img.getHeight()/pixelBreiteDesBlocks ; i++) {
			for(int j = 0 ; j < img.getHeight()/pixelBreiteDesBlocks ; j++) {
			
				for(int iblock = 0 ; iblock < pixelBreiteDesBlocks ; iblock++) {
					for(int jblock = 0 ; jblock < pixelBreiteDesBlocks ; jblock++) {
							
						if(m[i][j] == -1) {
							
							matrix[i * pixelBreiteDesBlocks + iblock ][j * pixelBreiteDesBlocks + jblock ] = 0;
							
						}
						else {
							
							matrix[i * pixelBreiteDesBlocks + iblock ][j * pixelBreiteDesBlocks + jblock ] = 1;
							
						}
	
					}			
				}
			
			}
		}
		
		return matrix;
	}
	

	
	public boolean step(int stepCount) {
		
		PathPoint currentPoint;
		boolean notAtEnd = true;
		
		for(int i = 0 ; i < stepCount ; i++) {
			
			if( notAtEnd && open.size() != 0 ) {
				
				currentPoint = open.get(0);
			
				for(PathPoint point : open){
					if( ( currentPoint.getcostRemaningDirectWay() + m[currentPoint.gety()][currentPoint.getx()] ) > point.getcostRemaningDirectWay() + m[currentPoint.gety()][currentPoint.getx()] ) {
						currentPoint = point;
					}
				
				}
			
				if( currentPoint.getx() == endx && currentPoint.gety() == endy) {
					
					notAtEnd = false;
					
				}
				else {	
					
					for(int blockY = 0 ; blockY < 3 ; blockY++ ) {
						for(int blockX = 0 ; blockX < 3 ; blockX++ ) {
					
							if( walkable(currentPoint.getx() + blockX - 1 , currentPoint.gety() + blockY - 1 ) && m[currentPoint.gety() + blockY - 1 ][currentPoint.getx() + blockX - 1] == -1 ) {
										
									if(blockY == 1 || blockX ==1 ) {
								
										m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] =	 m[currentPoint.gety()][currentPoint.getx()] + 1;
								
									}
									else {
								
										m[currentPoint.gety()+ blockY - 1 ][currentPoint.getx() + blockX - 1] =	 m[currentPoint.gety()][currentPoint.getx()] + Math.sqrt(2);
									}
								
									open.add(new PathPoint( currentPoint.getx() + blockX - 1 , currentPoint.gety()+ blockY - 1 ,  Math.sqrt( (endx - currentPoint.getx() + blockX - 1 )^2 + (endy - currentPoint.gety() + blockY - 1 )^2) * 2 ) );
										
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
