package frc.minimap;

import frc.robot.Drive;

public class TestBot {
	private double navXValue = 1;
	private double encoderValue = 1;
	public int height = 25;
    public int width = 10;
    //Drive d = new Drive();
	
    private double angle = 0;
    
	public void setHeading(double degree){
        this.navXValue = (double) degree;
        System.out.println(degree);
	}
	
	public void setEncoder(double dist){
        this.encoderValue = (double) dist;
        System.out.println(dist);
    }
    
	public void drive(){
		encoderValue += 1;
	}
	public void turn (int i){
		if (i == 0){
			navXValue += .1;
			navXValue %= 1;
		}
		else {
			navXValue -= .1;
			navXValue %= 1;
		}
	}
		
	public double getHeading(){
		//return 0.0;
        return (int) navXValue;
        //return d.getHeading();
	}
	
	public double getAngle(){
		angle = 360*navXValue;
        return angle;
        
	}
	
	public double getEncoderVal(){
        return encoderValue;
	//	return d.getLeftEncoder();
	}
	
}