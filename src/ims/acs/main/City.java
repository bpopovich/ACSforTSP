package ims.acs.main;

public class City {

	private double x,y;
	private String name;
	
	public City(double x, double y, String name) {
		super();
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public double distance(City next){
		double dist =  Math.sqrt(Math.pow(x-next.getX(), 2) + Math.pow(y-next.getY(), 2));
		return dist;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	
}
