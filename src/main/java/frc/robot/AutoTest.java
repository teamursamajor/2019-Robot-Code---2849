package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;


public class AutoTest implements Runnable {

    private boolean running = false;
    private Spark frontRight, frontLeft, backRight, backLeft;

    public AutoTest (int port0, int port1, int port2, int port3) {
        
        frontRight = new Spark(port2);
        frontLeft = new Spark(port1);
        backRight = new Spark(port3);
        backLeft = new Spark(port0);
        
        new Thread(this, "Auto Thread").start();
    }

    public void run() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        double tv = table.getEntry("tv").getDouble(-1); //Gets current y-coordinate
        
        //while the y-coordinate is not equal to 1, move forward and stop.
        
        table.getEntry("pipeline").setDouble(0);
        while(tv != 1) {
            driveTest(0.35);
            tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        }
        System.out.println("Saw the first tape.");
        table.getEntry("pipeline").setDouble(1);


        table.getEntry("tv").setDouble(0);
        //slows down the robot until it sees second tape.
        double tx = table.getEntry("tx").getDouble(-1); //Gets current x-coordinate
        tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        while(tv!=1 && tx >  2) {
            tx = table.getEntry("tx").getDouble(-1); //Gets current x-coordinate
            driveTest(0.3);
            tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        }
        System.out.println("Saw the second tape.");
        //alignment code / control loop 
        tx = table.getEntry("tx").getDouble(42.5);
        System.out.println(tx);

        double kp = 1.0/33.0;
        double tolerance = 0.1;
        while(Math.abs(tx) > tolerance) {
            tx = table.getEntry("tx").getDouble(42.5);
            driveTest(kp*tx);
            System.out.println("output power "+kp*tx);
            System.out.println("tx "+tx);
        }
        //stops motor
        System.out.println("Stopped.");
        driveTest(0.01);
        

    }
    
    public void driveTest(double power){
            frontRight.set(-power);
            frontLeft.set(power);
            backRight.set(-power);
            backLeft.set(power);


    }
    
}