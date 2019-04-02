package frc.tasks;

import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot {
    public enum CargoMode {
        IN, OUT;
        
        public CargoOrder callLoop() {
            switch (this) {
            case IN:
                break;
            case OUT:
                break;
            }
            running = false;
            return new CargoOrder(0.0);
        }
    }

    public static class CargoState {
        public static double cargoVelocity = 0.0, cargoVoltage = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double cargoVelocity, double cargoVoltage) {
            CargoState.cargoVelocity = cargoVelocity;
            CargoState.cargoVoltage = cargoVoltage;
            stateTime = System.currentTimeMillis();
        }
    }

    public static class CargoOrder {
        public double cargoPower;

        public CargoOrder(double power) {
            this.cargoPower = power;
        }
    }

    private static boolean running = true;

    public CargoTask(CargoMode mode, Cargo cargo) {
        running = true;
        cargo.setMode(mode);
        Thread t = new Thread("CargoTask");
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