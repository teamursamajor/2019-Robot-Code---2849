package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Cargo implements Runnable, UrsaRobot{
    private boolean running = false;
    private Spark armMotor;

    public Cargo(Spark motor) {
        AutoTest.debugMessage("I'm running");
        armMotor = motor;
        startCargo();
    }

    private void startCargo() {
        //TODO placeholder, synchronize on an object
        synchronized(Boolean.TRUE){
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