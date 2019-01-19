package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Cargo implements Runnable, UrsaRobot{
    public static boolean running = false;
    private static Object lock = new Object();
    
    private Spark cargoMotor;

    public Cargo() {
        cargoMotor = new Spark(CARGO);
        startCargo();
    }

    private void startCargo() {
        synchronized(lock){
            if (running)
				return;
			running = true;
        }
        new Thread(this, "cargoThread").start();
    }

    public void run() {
        // TODO place runnable code
    }
}