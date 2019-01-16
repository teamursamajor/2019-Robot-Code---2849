package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climbers implements Runnable {

    private boolean running = false;
    private Spark Motor;

        //defines the motors
    public Climbers (Spark Motor) {
        AutoTest.debugMessage("I'm running");
        this.Motor = Motor;

        //creates a new thread
    new Thread(this, "Thread").start(); 
    }
        public void run() {
        // place runnable code
    }
}