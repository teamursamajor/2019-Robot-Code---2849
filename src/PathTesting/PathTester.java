package com.teamursamajor.auto.Testing;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.io.*;

public class PathTester {
    
    public static void main(String args[]){
        JFrame screenFrame = new JFrame();
         
        File ursaMajorBearIcon = new File ((System.getProperty("user.dir") + "/../Icon.png"));
        screenFrame.setIconImage(new ImageIcon(ursaMajorBearIcon.toString()).getImage());
         
        screenFrame.setLayout(null);
        screenFrame.setSize(1000, 850);
        screenFrame.setDefaultCloseOperation(screenFrame.EXIT_ON_CLOSE); 
        screenFrame.setTitle("2019 Path Tester");
        //screenFrame.setVisible(true);


       

        DesiredPoint d = new DesiredPoint(250,0);
        CurrentPoint p1 = new CurrentPoint(450, 250);
        CurrentPoint p2 = new CurrentPoint(400, 450);
        CurrentPoint p3 = new CurrentPoint(150, 375);

        BufferedImage imgA = new BufferedImage(500,500, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D pathGraphicsA = (Graphics2D) imgA.getGraphics();
        pathGraphicsA.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        pathGraphicsA.setColor(Color.GREEN);

        BufferedImage imgB = new BufferedImage(500,500, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D pathGraphicsB = (Graphics2D) imgB.getGraphics();
        pathGraphicsB.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        pathGraphicsB.setColor(Color.BLUE);
        
        BufferedImage imgC = new BufferedImage(500,500, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D pathGraphicsC = (Graphics2D) imgC.getGraphics();
        pathGraphicsC.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        pathGraphicsC.setColor(Color.YELLOW);

        JPanel panel = new JPanel(){
        public void paint(Graphics g) {
                g.drawImage(imgA, 0, 0, 500,500, null);
                g.drawImage(imgB, 0, 0, 500, 500, null);
                g.drawImage(imgC, 0, 0, 500, 500, null);
            }
        };

        // panel.(imgA);
        // panel.add(imgB);
        // panel.add(imgC);

        int [] a = p1.getCoords();
        pathGraphicsA.drawOval(a[0],a[1],10,10);

        int [] b = p2.getCoords();
        pathGraphicsB.drawOval(b[0],b[1],10,10);

        int [] c = p3.getCoords();
        pathGraphicsC.drawOval(c[0],c[1],10,10);


        panel.setSize(500, 500);
        panel.repaint();
        screenFrame.add(panel);
        screenFrame.repaint();
        screenFrame.setVisible(true);
    }
}