    package frc.minimap;

    import java.awt.SystemTray;
    import java.awt.TrayIcon;
    import java.awt.SystemColor;

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

    public class RunTest {

        static TestBot temp;


        
        public RunTest(TestBot t){
            temp = t;
        }

        
        public void updateBot(TestBot t){
            temp.setEncoder(t.getEncoderVal());
            temp.setHeading(t.getHeading());

        }


        public static void main (String [] args){
        //  System.load("C:\\Users\\teamursamajor\\Desktop\\plsWork\\test.java");
            //System.loadLibrary("Gui");
        // System.console();
        //System.gc() = run garbage collector
        System.out.println(SystemTray.isSupported());
        SystemTray tray = SystemTray.getSystemTray();
        File f = (new File("C:/Users/teamursamajor/git/2019-Robot-Code---2849/src/main/java/frc/minimap/Icon2.png"));
        TrayIcon icon = new TrayIcon(new ImageIcon(f.toString()).getImage());
        icon.setImageAutoSize(true);
        
            icon.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    System.out.println("HI");
                    GuiTest g =  new GuiTest(temp);
                    String[] str = {};
                    try {
                        g.main(str);
                    } catch (Exception err) {
                    
                    }
                    tray.remove(icon);
                    
                }
            });


        try{
            tray.add(icon);
            // icon.
            System.out.println("YES");
        } catch (Exception e){
            System.out.println("ERR");
            
        }
        
        //   TrayIcon();
        }
    }