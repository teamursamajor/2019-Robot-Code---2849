package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.sql.Time;
import java.util.function.DoublePredicate;

//import org.graalvm.compiler.debug.DebugMemUseTracker;

import edu.wpi.first.networktables.NetworkTable;


public class AutoTest implements Runnable {
    
    private boolean running = false;
    private Spark frontRight, frontLeft, backRight, backLeft;

    public AutoTest ( Spark frontRight, Spark frontLeft, Spark backRight, Spark backLeft) {
        debugMessage("I'm running");
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
        
        
        new Thread(this, "Auto Thread").start();
    }

    public void run() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        double tv = table.getEntry("tv").getDouble(-1); //Gets current y-coordinate
        
        //while the y-coordinate is not equal to 1, move forward and stop.
        
        table.getEntry("pipeline").setDouble(0);
        while(tv != 1) {
            driveTest(0.35);

            //THE ROBOT IS NOT SLOWING DOWN AND IS STOPPING AFTER PASSING TAPE
            
            tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        }
        System.out.println("Saw the first tape.");
        debugMessage("We saw the first tape");
        table.getEntry("pipeline").setDouble(1);


        table.getEntry("tv").setDouble(0);
        //TODO - slow down the robot
        //slows down the robot until it sees second tape.
        double tx = table.getEntry("tx").getDouble(-1); //Gets current x-coordinate
        tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        while(tv!=1 && tx >  2) {
            tx = table.getEntry("tx").getDouble(-1); //Gets current x-coordinate
            driveTest(0.3);
            tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        }
        System.out.println("Saw the second tape.");
        debugMessage("Saw second tape");
        //alignment code / control loop 
        tx = table.getEntry("tx").getDouble(42.5);
        System.out.println(tx);

        double kd = .75;
        double lastTx = tx;
        double lastTime = System.currentTimeMillis();
        double currentTime;
        double kp = 1.0/33.0;
        double tolerance = 0.1;
        

        while(Math.abs(tx) > tolerance) {
            //DEBUGGING
            //double output_power;
            currentTime = System.currentTimeMillis();
            tx = table.getEntry("tx").getDouble(42.5);
            //Finding Rate of change in kd
            double rateOfChangeInKD_e = tx - lastTx;
            double rateOfChangeInKD_t = currentTime - lastTime;
            driveTest(kp*tx + kd*(rateOfChangeInKD_e / rateOfChangeInKD_t));
            System.out.println("output power "+kp*tx);
            System.out.println("tx "+tx);
            lastTime = currentTime;
            lastTx = tx;
            //(tx-last_tx)/(current_time-last_time)
            
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

    public void debugMessage(String message){
            message = "DEBUGGING: " + message;
            System.out.println(message);
    }
    
}