package frc.tasks;

import frc.robot.*;

public class HatchTask extends Task implements UrsaRobot {
    public enum HatchMode {
        RUN, WAIT;

        public HatchOrder callLoop() {
            switch (this) {
            case RUN:
                return new HatchOrder(-Constants.hatchPower);
            case WAIT:
                return new HatchOrder(0.0);
            }
            running = false;
            return new HatchOrder(0.0);
        }
    }

    public static class HatchOrder {
        public double hatchPower, servoAngle;

        public HatchOrder(double hatchPower) {
            this.hatchPower = hatchPower;
        }
    }

    private static boolean running = true;

    public HatchTask(HatchMode mode, Hatch hatch) {
        running = true;
        hatch.setMode(mode);
        Thread t = new Thread("HatchTask");
        t.start();
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}