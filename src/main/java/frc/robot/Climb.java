package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb implements Runnable, UrsaRobot {

    private Boolean running = new Boolean(false);
    private Spark climbMotor;

    public Climb(Spark motor) {
        AutoTest.debugMessage("I'm running");
        climbMotor = motor;
        startClimb();
    }

    private void startClimb() {
        //TODO placeholder, synchronize on an object
        synchronized(running){
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