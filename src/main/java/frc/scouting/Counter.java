package frc.scouting;

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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class Counter extends JPanel{
    private JButton plus,minus;
	private int total = 0;
	private JLabel label;
	String labelStr;
	
	public Counter (String str){
		this.setSize(60, 77);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label = new JLabel(str+":"+total);
		labelStr = str+":";
		
		setButtons();
		this.add(plus);
		this.add(label);
		this.add(minus);
	}
	
	
	
	private void setButtons(){
		plus = new JButton("  +  ");//43
		plus.setBorder(BorderFactory.createLoweredBevelBorder());
		plus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				total++;
				label.setText(labelStr+total);
			}
		});
		
		minus = new JButton(" - ");//45
		minus.setBorder(BorderFactory.createLoweredBevelBorder());
		minus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				total--;
				label.setText(labelStr+total);

			}
		});
	}
	

}