package frc.scouting;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;

public class CheckPanel extends JPanel {
	private String longestOption = "";
	private ArrayList<String> options;
	private ArrayList<JCheckBox> checks;
	private ArrayList<JRadioButton> radios;
	private boolean title = false;

	private interface Scale{
		int X = 3;//12, 2.5
		int Y = 30;

	}
	
	public CheckPanel (String [] options){
		this(options, false, "");
	}

	public CheckPanel (String[] options, boolean radio, String label){
		this.options = new ArrayList<String>();
		//checks = new ArrayList<>();
		
		if (radio){
			this.add(new JLabel(label));
			radios = new ArrayList<JRadioButton>();
			for (String text : options){
				this.options.add(text);
				radios.add(new JRadioButton(text));
				this.add(radios.get(radios.size() - 1));
				isLongest(text);
				title = true;
			}
		} else {
			checks = new ArrayList<JCheckBox>();
			for (String text : options){
				this.options.add(text);
				checks.add(new JCheckBox(text));
				this.add(checks.get(checks.size()-1));
				isLongest(text);
			}
			
		}
		
		calcSize();
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	private void isLongest(String str){
		if(str.length() > longestOption.length()){
			longestOption = str;
		}
	}
	
	private void calcSize(){
		//- (int)(longestOption.length())
		//3^3.89278926 = 72, 3.89 = (6*12)log base 3

		int labelHeight = 0;
		if (title){
			labelHeight = Scale.Y;
		}

		this.setSize((int)(Math.pow(Scale.X, Math.log(longestOption.length()*12)/Math.log(3))+1), Scale.Y * options.size() + labelHeight);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		
	}
}
