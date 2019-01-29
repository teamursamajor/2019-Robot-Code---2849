package frc.path;

public class AnchorPoint {
	private double xCoord, yCoord;
	private double defaultInflectionPoint;// for flipping over x axis
	private int halfImageHeight = 400; //Half of the image's height
	
	//Width of the image frame/2:
	int width = 800/2;
	
	public AnchorPoint (double xCoord, double yCoord, double inflectionPoint){
		this.xCoord=xCoord;
		this.yCoord=yCoord;
		this.defaultInflectionPoint = inflectionPoint;
	}
	
	public AnchorPoint (double xCoord, double yCoord){
		this.xCoord=xCoord;
		this.yCoord=yCoord;
		defaultInflectionPoint = xCoord;
	}
	

	//A---|---- -> ----|---A
	public double flipByYAxis(){
		return flipByYAxis(xCoord);
	}
	
	//NOT TESTED
	public double flipByYAxis(double x){
		/*
		 * x = frame width - x
		 * return x
		 */
		
		double res = 400 - x;
		return res;
	}
	
	/*
	 * A    |
	 * |    |
	 * - -> -
	 * |    |
	 * |    A
	 */
	public double flipByXAxis(){
		return flipByXAxis(yCoord, defaultInflectionPoint);
	}
	
	//NOTTESTED
	public double flipByXAxis(double y, double inflectionPoint){
		double dist = inflectionPoint - y;
		return inflectionPoint + dist;
	}
	
	//NOTTESTED
	public void moveX (int dist){
		/*
		 * xCoordinate + dist
		 */
	}
	
	//NOTTESTED
	public void moveY (int dist){
		/*
		 * yCoordinate + dist
		 */
	}

	//TODO - feet to pixel method

}
