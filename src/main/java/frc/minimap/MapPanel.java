package frc.minimap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.*;

public class MapPanel extends JPanel implements FieldMeasurements {
	// private String mode;
	private BufferedImage fieldImage, robotImage, gridOverlay, path;
	private int[] startPoint;// in Pixels, x y
	private int[] previousPoint;// in Pixels, x y, topLeft based?
	static TestBot bot;
	public int width,height;//of map
	private double wConv,hConv;

	/*
	 * cos 90 = 0 -> xCoord shift sin 90 = 1 -> yCoord shift
	 * 
	 */

	//add start point constructor as well
	/**
	 * Displays a mini map of the field
	 * for us to use
	 * 
	 * @param fieldImage An image of this year's field to use
	 * @param robotDimensions
	 * @param t TestBot gathers sensors values for the mini-map to use
	 */
	public MapPanel(BufferedImage fieldImage, TestBot t) {
		this.fieldImage = fieldImage;
		// this.robotDimensions = robotDimensions;
		bot = t;
		int[] coords = { 100, 100 };
		startPoint = coords;
		previousPoint = startPoint;
		/*
		 * set start point = set start point; set heading to current heading;
		 */
		
		width = fieldImage.getWidth()/2;
		height = fieldImage.getHeight()/2;
		this.setSize(width, height);

		robotImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

		// inch -> pixel
		int pixelArea = height * width;
	    wConv = width/FieldMeasurements.width;
		hConv = height/FieldMeasurements.height;
		
		System.out.println("Width: " + wConv +", Height: " + hConv);
	}

	public void paint(Graphics g) {
		super.paint(g);
	//	moveRobot();
		g.drawImage(fieldImage, 0, 0, width, height, null);
		g.drawImage(robotImage, 0, 0, width, height, null);
		//System.out.println("ew");
	}
	
	public void update() {
		moveRobot();
		this.repaint();
		//System.out.println("ew");
	}
	
	@SuppressWarnings("static-access")
	private void moveRobot() {
		Graphics2D g = (Graphics2D) robotImage.getGraphics();

		double xSlope = Math.cos(Math.toRadians(bot.getHeading() + 0));//x
		double ySlope = Math.sin(Math.toRadians(bot.getHeading() + 0));//y

		double xDist = bot.getEncoderVal() * xSlope;
		double yDist = bot.getEncoderVal() * ySlope;
		// bot.clearEncoder(); //TODO

		previousPoint[0] += (int) xDist;
		previousPoint[1] += (int) yDist;
		int[] center = {(int)(previousPoint[0] + ((bot.width*wConv)/2)), (int)(previousPoint[1] + ((bot.height*hConv)/2))};
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

		clearRobotImage();
		

		
		AffineTransform rotation = new AffineTransform();
		rotation = rotation.getRotateInstance((Math.toRadians(bot.getHeading() + 0)), (double) center[0], (double) center[1]);
		
		
		Rectangle rect = new Rectangle(previousPoint[0],previousPoint[1],(int)(bot.width*wConv),(int) (bot.height*hConv));
		Rectangle line = new Rectangle(center[1], center[0]-4, 25, 1);
		//TODO - VARS 4 width*wConv, cntr -centerPoint
		
//Rectangle r = new Rectangle();
		Shape robot = rotation.createTransformedShape(rect);//rotate center point
		//rotation = rotation.getRotateInstance((Math.toRadians(bot.getHeading() + 0)), (double) previousPoint[0] + 5, (double) previousPoint[1]);
		Shape angelLine = rotation.createTransformedShape(line);
		Shape dot = rotation.createTransformedShape(new Rectangle(new Rectangle(center[0], center[1], 1, 1)));
		//Shape line = 
		g.draw(robot);
		g.fill(robot);
		//Draws green heading line - NOT WORKING
//		g.setColor(Color.GREEN);
//		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//		g.draw(angelLine);//change to angle return
		//Draws The Center Point =================================
		g.setColor(Color.MAGENTA);
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.draw(dot); 
	}

	public void sendRobotToReferencePoint(double[] coords) {
		/*
		 * If there is a spot where we KNOW where we are then it will move the
		 * robot image to that spot
		 * 
		 * example: start platforms
		 * 
		 */
		int[] pixCords = {
			(int)(coords[0] * (1/wConv)), (int)(coords[1] *(1/hConv))
		};

		startPoint = pixCords;
		previousPoint = startPoint;
		
		bot.setEncoder(0);
		bot.setHeading(0);
		moveRobot();

		this.repaint();
	}

	private void clearRobotImage() {
		Graphics2D g = (Graphics2D) robotImage.getGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		g.clearRect(0, 0, width, height);
	}

	public void flipField (){
		Graphics2D g = (Graphics2D) fieldImage.getGraphics();
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-fieldImage.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		fieldImage = op.filter(fieldImage, null);
		this.repaint();
	}
}