package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Hatch implements Runnable, UrsaRobot{
    public static boolean running = false;
    private static Object lock = new Object();

    private Spark hatchMotor;

    public Hatch(Spark motor) {
        AutoTest.debugMessage("I'm running");
        hatchMotor = motor;
        startHatch();
    }

    private void startHatch() {
        synchronized(lock){
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