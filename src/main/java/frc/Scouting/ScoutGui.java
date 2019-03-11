package frc.Scouting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
//import frc.robot.Drive;

//import minimap.Path.*;

//NAVX returns an accumulative value 0-360 360-720 use val from drive

public class ScoutGui {
	interface Box{
		String[] TEXT_A = {
			"Cargo", "Hatch", "Defend"
		};
		String[] TEXT_B = {
			"Rocket Hatch 1", "Rocket Hatch 2", "Rocket Hatch 3"
		};
		String[] TEXT_C = {
			"Rocket Cargo 1", "Rocket Cargo 2", "Rocket Cargo 3"
		};
		String[] TEXT_D = {
			"Climb Level 1", "Climb Level 2", "Climb Level 3",  "Assisst Climb"
		};
		//TODO - ground pickup
		
		String[] TEXT_E = {
			"Bottom Right", "Bottom Middle", "Bottom Left", "Top Right", "Top Left"
		};
		String[] TEXT_F = {
			"Auto", "Tele-operated", "Mixed        ", "N/A (unknown)" , "None       "
		};
	}
	
	static CheckPanel[] checks = {
		new CheckPanel(Box.TEXT_A), new CheckPanel(Box.TEXT_D), new CheckPanel(Box.TEXT_E, true, "Starting Platform"), 
		new CheckPanel(Box.TEXT_F, true, "Sandstorm Drive")
	};
	static Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

	// static String[] checkBoxText = {"Cargo", "Hatch", "Defend", "Climb Level 2", "Climb Level 3",  "Assisst Climb", "Rocket Cargo 1", "Rocket Cargo 2", "Rocket Cargo 3", "Rocket Hatch 1", "Rocket Hatch 2", "Rocket Hatch 3"};

	public static void main(String[] args) throws IOException{
//		BufferedImage fieldImage = ImageIO.read(new File(System.getProperty("user.dir") + "/../2019 Field.jpg"));
		JFrame frame = new JFrame("Scouting");
		//JPanel panel = new JPanel(new FlowLayout());+
		//Text box for sandstorm info, additional misc info
		//team #
		//Strat	
		//any issues
		//JButton +/- Cargo or Hatch

		//ArrayList<JCheckBox> checks = setCheckBoxes();
		
		//Radio Buttons for sand storm : Auto - Manual - N/A/Dont Know - Didnt Do Anything

		//TODO - menu panel for shortcuts
		JPanel panel = new JPanel();
		
		
		
		int xCoord= 0;
	
		
		for (int i = 0; i < checks.length; i++){
			frame.add(checks[i]);
			checks[i].setLocation(xCoord,0);
			xCoord += checks[i].getWidth();
		}
		
		//Counter hatch = new Counter("HATCH"); TODO- later
		// Counter hatchY = new Counter("Hatch");
		// frame.add(hatchY);
		// Counter hatchN = new Counter("Missed");
		// frame.add(hatchN);
		
		// Counter cargoY = new Counter("Cargo");
		// frame.add(cargoY);
		// Counter cargoN = new Counter("Missed");
		// frame.add(cargoN);
		
		// hatchY.setLocation(0,90);
		// hatchN.setLocation(60,90);
		// cargoY.setLocation(0,167);
		// cargoN.setLocation(60,167);//TODO - make a class for this
		
		TextInfo t1 = new TextInfo("Strategy:");
		frame.add(t1);
		t1.setLocation(0,243);
		
		TextInfo t2 = new TextInfo("Technical Issues:");
		frame.add(t2);
		t2.setLocation(200,243);
		
		TextInfo t3 = new TextInfo("Sand Storm:");
		frame.add(t3);
		t3.setLocation(400,243);
		
		TextInfo t4 = new TextInfo("Misc:");
		frame.add(t4);
		t4.setLocation(000,443);
		t4.setSize(600,200);
		
		JPanel teamNum = new JPanel();
		teamNum.add(new JLabel("Team Number:"));
		JTextField number = new JTextField();
		number.setPreferredSize(new Dimension(75,25));
		teamNum.add(number);
		teamNum.setSize(100,100);
		frame.add(teamNum);
		teamNum.setLocation(xCoord, 00);
		
		ScorePanel s = new ScorePanel();
		frame.add(s);
		s.setLocation(700,00);

		frame.add(panel);
		frame.setSize(1400,700);
		frame.setVisible(true);
		char c1 = '+';
		char c2 = '-';
		System.out.println((int)c1+","+(int)c2);
	}


	// public static void setRadios(){
	// 	radios = new ArrayList<JRadioButton>();
	// 	for (String str : SANDSTORM){
	// 		radios.add(new JRadioButton(str));
	// 	}
	// }

	// public static JPanel makeCheckPanel(int[] index){
	// 	JPanel res = new JPanel();//TODO - CHANGE
	// 	res.setBorder(blackLine);

	// 	for (int i = index[0]; i < index[1] ; i++){
	// 		res.add(checks.get(i));
	// 	}
	// 	res.add(checks.get(index[1]));
	// 	res.setSize((index[1] - index[0])*50, 150);
	// 	return res;

	// }

	// public static void saveRes(String fileName){
	// 	String newLine = System.lineSeparator();
	// 	String toWrite = "";
	// 	toWrite += "# Team Number:" + newLine + "\t" + teamNumber.getText() + newLine;
	// 	toWrite += "# Selected Boxes" + newLine;
	// 	for (JCheckBox box : checks) {
	// 		if (box.isSelected())  toWrite += "\t" + box.getText() + newLine;
	// 	}
	// 	toWrite += "# Sandstorm" + newLine;
	// 	for (JRadioButton radio : radios) {
	// 		if (radio.isSelected()) toWrite += "\t" + radio.getText() + newLine;
	// 	}
	// 	toWrite += "# Technical Issues" + newLine + "\t" + area.getText() + newLine;
	// 	toWrite += "# Strategy" + newLine + "\t" + strategy.getText() + newLine;
	// 	toWrite += "# Misc Info" + newLine + "\t" + misc.getText() + newLine;
	// 	toWrite += "# Cargo Count" + newLine + "\t" + cargo.getCount() + newLine;
	// 	toWrite += "# Hatch Count" + newLine + "\t" + hatch.getCount() + newLine;
	// 	toWrite += "end" + newLine + newLine;
	// 	try {
	// 		Files.write(Paths.get(System.getProperty("user.dir") + "\\" + fileName + ".txt"), toWrite.getBytes(), StandardOpenOption.APPEND);
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// }
	
	public static void makeFile(String fileName) {
		String newLine = System.lineSeparator();
		String toWrite = "# Ursa Major-style Scouting file generated by ScoutGui.java" + newLine + "# Name:" + newLine + "TEST" + newLine;
		try {
			Files.write(Paths.get(System.getProperty("user.dir") + "\\Scouting Files\\" + fileName + ".txt"), toWrite.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}