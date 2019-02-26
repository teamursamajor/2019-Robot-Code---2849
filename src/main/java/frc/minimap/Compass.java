package frc.minimap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.*;

/**
 * Makes a compass to display the heading of our
 * robot using it's heading value.
 * 
 * @author Christopher Dunne - 2019
 *
 */
public class Compass extends JPanel {
	private int r;//Radius
	private BufferedImage compassImage, compassResImage;
	private TestBot bot;
	private int offset = 3;
	
	public Compass (int radius, TestBot bot){
		this.r = radius;
		this.bot = bot;
		this.setSize((radius+offset)*2, (radius+offset)*2 );
		
		compassImage = new BufferedImage(radius*2, radius*2, BufferedImage.TYPE_4BYTE_ABGR);
		compassResImage = new BufferedImage(radius*2, radius*2, BufferedImage.TYPE_4BYTE_ABGR);

	}
	
	public void paint(Graphics g) {
		super.paint(g);
		paintCompass();
		g.drawImage(compassImage, offset, offset, r*2, r*2, null);
		g.drawImage(compassResImage, offset, offset, r*2, r*2, null);
	}
	
	private void paintCompass(){
		Graphics2D g = (Graphics2D) compassImage.getGraphics();
		
		g.setColor(Color.GRAY);
		g.fillOval(0, 0, r*2, r*2);
		
		g.setColor(Color.WHITE);
		g.drawLine(0, 0, r*2, r*2);
		g.drawLine(r*2, 0, 0, r*2);
		g.drawLine(r, 0, r, r*2);
		g.drawLine(0, r, r*2, r);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		g.drawOval(0, 0, r*2, r*2);
		
		clearRes();
		setRes();

	}
	
	private void clearRes (){
		Graphics2D g = (Graphics2D) compassResImage.getGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		g.clearRect(0, 0, r*2, r*2);
		
	}
	
	private void setRes() {
		Graphics2D g = (Graphics2D) compassResImage.getGraphics();
		g.setColor(Color.GREEN);
		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		AffineTransform rotation = new AffineTransform();
		rotation = rotation.getRotateInstance((Math.toRadians(bot.getHeading() + 0)), (double) r, (double) r);
				
		Rectangle line = new Rectangle(r, r+0, (int)(r*(9/5)), 0);
		
		Shape res = rotation.createTransformedShape(line);
		g.draw(res);
	}

	
	
/*
 * void clearRobotImage() {
		Graphics2D g = (Graphics2D) robotImage.getGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		g.clearRect(0, 0, width, height);
	}
 */
	
}