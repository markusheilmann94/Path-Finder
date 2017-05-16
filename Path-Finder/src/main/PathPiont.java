package main;

public class PathPiont {

	int x , y , cost;
	
	public PathPiont(int x , int y) {
		this.x = x;
		this.y = y;	
		cost = 0;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int c) {
		
		cost = c;
		
	}
}
