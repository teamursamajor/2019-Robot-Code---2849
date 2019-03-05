package frc.minimap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
//import frc.robot.Drive;

//import minimap.Path.*;

//NAVX returns an accumulative value 0-360 360-720 use val from drive

public class Gui {

	static Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
	static Border raisedBevel = BorderFactory.createRaisedBevelBorder();
	static Border loweredBevel = BorderFactory.createLoweredBevelBorder();
	static TestBot testBot;
	static MapPanel map;
	static Compass compass;
	
	public static void main(String[] args) throws IOException {
//		BufferedImage fieldImage = ImageIO.read(new File(System.getProperty("user.dir") + "/../2019 Field.jpg"));
		BufferedImage fieldImage = ImageIO.read(new File("C:/Users/Ursa Major/git/2019-Robot-Code---2849/src/main/java/frc/minimap/2019 Field.jpg"));
		testBot = new TestBot();
		map = new MapPanel(fieldImage, testBot);
		compass = new Compass(75, testBot);
		
		JPanel testPanel = setTestPanel();

		JFrame frame = new JFrame("2019 Mini Map");
		frame.setSize(1000, 1000);
		File ursaMajorBearIcon = new File("C:/Users/Ursa Major/git/2019-Robot-Code---2849/src/main/java/frc/minimap/Icon.png");
        frame.setIconImage(new ImageIcon(ursaMajorBearIcon.toString()).getImage());
		frame.setLayout(null);
		setMenu(frame);
		
		//frame.C:\Users\Ursa Major\git\2019-Robot-Code---2849\src\main\java\frc\minimap\Icon.png
		
		frame.add(map);
		
		frame.add(testPanel);
		testPanel.setLocation(400,425);

		frame.add(compass);
		compass.setLocation(550,0);
		Border compoundA = BorderFactory.createCompoundBorder(loweredBevel, blackLine);
		Border compound = BorderFactory.createCompoundBorder(compoundA, raisedBevel);
		compass.setBorder(compound);
		
		frame.setVisible(true);
	}
	
	public static void setMenu(JFrame frame){
		JMenuBar menuBar = new JMenuBar();
		Border compound = BorderFactory.createCompoundBorder(loweredBevel, blackLine);
		menuBar.setBorder(compound);
		ArrayList <JMenu> menus = new ArrayList<JMenu>();
		
		Color red = new Color(215,50,50);
		Color blue = new Color(50,50,215);
		
		JMenu m = new JMenu("PLACEHOLDER");
		menus.add(m);
		
		JMenu visuals = new JMenu("Change Look");
		menus.add(visuals);
		
		JMenu startPoint = new JMenu("Set Start Point");
		menus.add(startPoint);
		
		JMenu color = new JMenu ("Alliance Color");
		color.setOpaque(true);
		color.setBackground(blue);
		menus.add(color);
		
		JMenu scouting = new JMenu("Scouting Menu");
		menus.add(scouting);
		
		JMenu shortCut = new JMenu("Key Board Short Cuts");
		menus.add(shortCut);
		
		for(JMenu menu : menus){
			menu.setBorder(raisedBevel);
			menuBar.add(menu);
			if (menu.getText() == "Alliance Color"){
				menuBar.add(Box.createHorizontalGlue());
			}
		}
		
		ArrayList<JMenuItem> points = new ArrayList<JMenuItem>();
		JMenuItem pointA = new JMenuItem("Top Left Platform");
		points.add(pointA);
		JMenuItem pointB = new JMenuItem("Top Right Platform");
		points.add(pointB);
		JMenuItem pointC = new JMenuItem("Bottom Left Platform");
		points.add(pointC);
		JMenuItem pointD = new JMenuItem("Bottom Middle Platform");
		points.add(pointD);
		JMenuItem pointE = new JMenuItem("Bottom Right Platform");
		points.add(pointE);
		    
		for (JMenuItem menu : points){
			startPoint.add(menu);
			if (menu.getText() == "Top Right Platform"){
				startPoint.addSeparator();
			}
		}
		
		//For alliance color
		JMenuItem swapTeam = new JMenuItem("Swap Team");//TODO - add icon
		
		swapTeam.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				map.flipField();
				if (color.getBackground() == blue){
					color.setBackground(red);
				} else {
					color.setBackground(blue);
				}
			}
		});		
				
		//swapTeam.setBackground(red);
		color.add(swapTeam);
		
		
		JCheckBoxMenuItem center = new JCheckBoxMenuItem("Center Point");
		center.setToolTipText("Whether or not the pink dot will be drawn");
		visuals.add(center);
		
		//For Shortcuts====
		ArrayList<JMenuItem> keys = new ArrayList<JMenuItem>();
		JMenuItem k1 = new JMenuItem("Alt + w : drive forwards");
		keys.add(k1);
		JMenuItem k2 = new JMenuItem("Alt + a/d : turn");
		keys.add(k2);
		
		for (JMenuItem menu : keys){
			shortCut.add(menu);
		}
		
		
		
		frame.setJMenuBar(menuBar);
		//	menuBar.setHelpMenu(m);

	}
	
	public static JPanel setTestPanel(){
		JPanel testPanel = new JPanel();
		
		JButton rotateL = new JButton ("<--");
		rotateL.setMnemonic(KeyEvent.VK_A);//works when yoi press alt + r
		JButton rotateR = new JButton ("-->");
		rotateR.setMnemonic(KeyEvent.VK_D);
		JButton forward = new JButton ("Drive") ;
		forward.setMnemonic(KeyEvent.VK_W);
				
		
		rotateL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//testBot.setHeading(testBot.getHeading() + 10);
				testBot.setEncoder(0);
				map.update();
				compass.repaint();
			}
		});
		
		rotateR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//testBot.setHeading(testBot.getHeading() - 10);
				testBot.setEncoder(0);
				map.update();
				compass.repaint();
			}
		});
		
		forward.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				double d = testBot.getEncoderVal();
				testBot.setEncoder(d+1);
				map.update();
			}
		});		
		
		testPanel.add(forward);
		testPanel.add(rotateL);
		testPanel.add(rotateR);
		testPanel.setSize(100,200);
		
		return testPanel;

	}
	

}