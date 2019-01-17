package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb implements Runnable {

    private boolean running = false;
    private Spark climbMotor;

    public Climb(Spark motor) {
        AutoTest.debugMessage("I'm running");
        climbMotor = motor;
    }

    private void startClimb() {
        //TODO placeholder, synchronize on an object
        synchronized(Boolean.TRUE){
            if (running)
				return;
			running = true;
        }
        new Thread(this, "climbThread").start();
    }

    public void run() {
        // TODO place runnable code
    }
}