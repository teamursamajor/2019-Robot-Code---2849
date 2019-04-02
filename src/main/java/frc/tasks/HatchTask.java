package frc.tasks;

import frc.robot.*;

public class HatchTask extends Task implements UrsaRobot {
    public enum HatchMode {
        WAIT, RUN;
        
        public HatchOrder callLoop() {
            switch (this) {
            case WAIT:
                break;
            case RUN:
                break;
            }
            running = false;
            return new HatchOrder(0.0);
        }
    }

    public static class HatchState {
        public static double hatchVelocity = 0.0, hatchVoltage = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double hatchVelocity, double hatchVoltage) {
            HatchState.hatchVelocity = hatchVelocity;
            HatchState.hatchVoltage = hatchVoltage;
            stateTime = System.currentTimeMillis();
        }
    }

    public static class HatchOrder {
        public double hatchPower;

        public HatchOrder(double power) {
            this.hatchPower = power;
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