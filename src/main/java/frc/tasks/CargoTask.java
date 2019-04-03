package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Cargo intake mechanism during autonomous.
 */
public class CargoTask extends Task implements UrsaRobot {
    public enum CargoMode {
        IN, OUT, WAIT;
    }

    private long runTime = 1000;
    private Cargo cargo;

    public CargoTask(CargoMode mode, Cargo cargo) {
        this.cargo = cargo;
        cargo.setMode(mode);
        Thread t = new Thread(this, "CargoTask");
        t.start();
    }

    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cargo.setMode(CargoMode.WAIT);
    }
}