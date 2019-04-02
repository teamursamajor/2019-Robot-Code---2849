package frc.tasks;

import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot {
    public enum CargoMode {
        IN, OUT, WAIT;
    }

    private long runTime = 1000;
    private Cargo cargo;

    public CargoTask(CargoMode mode, Cargo cargo, long runTime) {
        this.cargo = cargo;
        this.runTime = runTime;
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