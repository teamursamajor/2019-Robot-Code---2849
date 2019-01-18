package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Hatch implements Runnable, UrsaRobot{
    private Boolean running = new Boolean(false);
    private Spark hatchMotor;

    public Hatch(Spark motor) {
        AutoTest.debugMessage("I'm running");
        hatchMotor = motor;
        startHatch();
    }

    private void startHatch() {
        //TODO placeholder, synchronize on an object
        synchronized(running){
            if (running)
				return;
			running = true;
        }
        new Thread(this, "hatchThread").start();
    }

    public void run() {
        // TODO place runnable code
    }
}