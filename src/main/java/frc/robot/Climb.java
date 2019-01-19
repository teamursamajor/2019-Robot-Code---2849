package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb implements Runnable, UrsaRobot {
    public static boolean running = false;
    private static Object lock = new Object();

    private Spark climbMotor;

    public Climb() {
        climbMotor = new Spark(CLIMB);
        startClimb();
    }

    private void startClimb() {
        synchronized(lock){
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