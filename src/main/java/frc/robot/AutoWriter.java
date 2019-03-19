package frc.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class for writing sensor information to be used for
 * auto, minimap, and other jazz... 
 */
public class AutoWriter implements UrsaRobot{
    //left encoder
    //right encoder
    //pot
    //pot range
    //nav x
    //ultrasonic
    //learning file 

    // TODO Hey Chris, figure this out man. Commented out on the HP
    private interface MachineLearning{
        String time = "T#";
        String encoderToString = "E#_X_";
        String potToString = "P#";
        String success = "S#";
        //File name
        String fileName;
        double[] potRange;
        
        default String encoder(boolean right){
            if (right){
                return encoderToString.replace("_X_", "R");
            }
            return encoderToString.replace("_X_","L");
        }
        
    }
    private interface Minimap {

    }


    public AutoWriter (double[] potRange){
        this.potRange = potRange;
        SimpleDateFormat format = new SimpleDateFormat ("yy/MM/dd - hh:mm:ss");
        String str = format.format(new Date());
        //TODO - change
        fileName = ""+(System.getProperty("user.dir") + "/../"+str);
    }
    
    public void writeToFile (double encoderR, double encoderL,
                            double navX, double pot) throws IOException{
        
        File f = new File(fileName);
        FileWriter fileWriter = new FileWriter(f, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write("E#R"+encoderR);
        writer.write("E#L"+encoderL);
        writer.write("NAV"+navX);
        writer.write("POT"+pot);
        


        // writer.write(coords[0] + ", Y: " + coords[1] + "\n");
        writer.flush();
        writer.close();
    }

    
}
