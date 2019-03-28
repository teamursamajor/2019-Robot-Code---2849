package frc.scouting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
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


public class TextInfo extends JPanel{
	
	private JTextArea area;
	private String str;
	
	public TextInfo(String lbl){
		JLabel label = new JLabel(lbl);
		this.add(label);
		str = lbl;
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		area = new JTextArea();
		area.setPreferredSize(new Dimension(170,200));
		area.setLineWrap(true);
		area.setAutoscrolls(true);
		area.setWrapStyleWord(true);
		
		JScrollPane scroll = new JScrollPane(area, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		
		this.add(scroll);
		this.setSize(200,200);

		
	}
	
	public void setSize (int width, int height){
		super.setSize(width, height);
		area = new JTextArea();
		area.setPreferredSize(new Dimension(width-30,height));
		area.setLineWrap(true);
		area.setAutoscrolls(true);
		area.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(area);
		this.removeAll();
		this.add(new JLabel(str));
		this.add(scroll);
	}
}
