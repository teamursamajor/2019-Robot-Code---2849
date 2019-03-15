package frc.robot;


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

    private interface MachineLearning{
        String time = "T#";
        String encoderToString = "E#_X_";
        String potToString = "P#";
        String success = "S#";
        //File name
        
        default String encoder(boolean right){
            if (right){
                return encoderToString.replace("_X_", "R");
            }
            return encoderToString.replace("_X_","L");
        }
        
    }
    private interface Minimap {

    }


    public AutoWriter (String autoFile){
        
    }

    
}