package main;

public class PathPoint {

	int x , y ;
	double costRemaningDirectWay;
	
	public PathPoint(int x , int y , double costRemaningDirectWay) {
		this.x = x;
		this.y = y;	
		this.costRemaningDirectWay = costRemaningDirectWay;
	}
	
	public double getcostRemaningDirectWay() {
		return costRemaningDirectWay;
	}
	
	
	public int getx() {
		 return x;
	}
	
	public int gety() {
		 return y;
	}
	
}
