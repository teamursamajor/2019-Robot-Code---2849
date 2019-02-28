package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MainClass {
    static private double [][] pathA = {
        {0,0},{0,20},{0,40}
    };


    static private double[] simple = {0,0, 50, 75};
    static private double xDist, yDist;
    static private PathDrive drive;

    public static void main (String args[]){
        xDist = (simple[2]-simple[0]);
        yDist = (simple[3]-simple[1]);
        //double angleOfError = 
        double currentAngle = drive.getHeading();
        double predictedAngle = null;

        

    }

    public static double calcMag (double[] v1, double [] v2){
        double sumA = 0;
        double sumB = 0;
        for (double d : v1){
            sumA += Math.pow(d, 2);
        }
        
        for (double d : v2){
            sumB += Math.pow(d, 2);
        }

        sumA = Math.sqrt(sumA);
        sumB = Math.sqrt(sumB);

        return sumA*sumB;
    }

/**
 * 
 * v1 = {2,3,5}
 * v2 = {1,6,-4}
 * 
 * mag(v1) = root(2^2 + 3^2 + 5^2)
 * = root(38);
 * 
 * mag(v2) = root(53)
 * 
 * =========
 * 
 * Dot Product:
 * v1[n] * v2[n] 
 * 2*1 = 2
 * 3*6 = 18
 * 5*-4 = -20
 * 2+18-20 = 0
 * 
 * cos x = m;
 * 
 * m = dotProduct / root(38)*root(53)
 * 
 * 
 */




}