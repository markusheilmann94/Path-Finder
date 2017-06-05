package main;

public class StartAndEndPoint {
	
	int startx , starty , endx , endy ;
	
	
	public StartAndEndPoint() {
		
		startx = 0;
		starty = 0;
		endx = 0;
		endy = 0;
		
	}
	
	public StartAndEndPoint( int stx , int sty , int enx , int eny ) {
		
		startx = stx;
		starty = sty;
		endx = enx;
		endy = eny;
		
	}
	
	public int getstartx() {
		
		return startx;
		
	}
	
	public int getstarty() {
		
		return starty;
		
	}
	
	public int getendx() {
		
		return endx;
	
	}
	
	public int getendy() {
		
		return endy;
	
	}
	
	public void setstartx( int value) {
		
		startx = value;
		
	}
	
	public void setstarty( int value ) {
		
		starty = value;
		
	}
	
	public void setendx( int value ) {
		
		endx = value;
	
	}
	
	public void setendy( int value) {
		
		endy = value;
	
	}
	
}
