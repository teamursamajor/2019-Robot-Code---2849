package frc.robot;

import edu.wpi.first.wpilibj.Spark;



public class AutoArm implements Runnable {

    private boolean running = false;
    private Spark BottomArmMotor, TopArmMotor;

        //defines the motors
    public AutoArm (Spark BottomArmMotor, Spark TopArmMotor) {
        AutoTest.debugMessage("I'm running");
        this.BottomArmMotor = BottomArmMotor;
        this.TopArmMotor = TopArmMotor;


        new Thread(this, "Auto Thread").start(); 
    }

    public void run() {
        //fill runnable code here
    }

}