package frc.tasks;

import frc.robot.Turntable;

public class TurntableTask extends Task {

    public enum TurntableMode {
        FORWARD, LEFT, RIGHT;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public TurntableOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case FORWARD:
                desiredAngle = 0.0;
                return autoGoToAngle();
            case LEFT:
                desiredAngle = -90.0;
                return autoGoToAngle();
            case RIGHT:
                desiredAngle = 90.0;
                return autoGoToAngle();
            }
            return autoGoToAngle();
        }

        private TurntableOrder autoGoToAngle() {
            double newAngle = desiredAngle - TurntableState.voltage;
            double angleTolerance = 5; //TODO Determine experimentally
            if (newAngle < angleTolerance) {
                running = false;
                return new TurntableOrder(0.0);
            }

            //Assuming use of encoders
            if (newAngle > 90 || newAngle < -90) {
                newAngle = (newAngle > 90) ? 90 : -90;
            } 

            //TODO PD Loop, determine constants
            double turntableKp = 1/40;
            double turntableKd = 0;

            double velocity = (TurntableState.velocity > 0) ? TurntableState.velocity : -TurntableState.velocity;
            double outputPower = turntableKp * newAngle + turntableKd * velocity;

            return new TurntableOrder(outputPower);
        }
    }

    private static double desiredAngle = 0.0;

    public TurntableTask(TurntableMode mode, Turntable turntable) {
        running = true;
        turntable.setMode(mode);
        Thread t = new Thread("Turntable Task");
        t.start();
    }

    public TurntableTask(double angle, Turntable turntable) {
        running = true;
        desiredAngle = angle;
        Thread t = new Thread("Turntable Task");
        t.start();
    }

    public static boolean running = true;

    public void run() {
        while (running) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class TurntableState {
        public static double voltage = 0.0, velocity;

        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double voltage, double velocity) {
            TurntableState.voltage = voltage;
            TurntableState.velocity = velocity;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveTask and holds values for the new left and right
     * powers to be set by Drive
     */
    public static class TurntableOrder {
        public double power;

        public TurntableOrder(double power) {
            this.power = power;
        }
    }
}