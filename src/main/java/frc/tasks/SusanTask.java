package frc.tasks;

import frc.robot.LazySusan;

public class SusanTask extends Task {

    public enum SusanMode {
        FORWARD, LEFT, RIGHT;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public SusanOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case FORWARD:
                return autoGoToAngle();
            case LEFT:
                return autoGoToAngle();
            case RIGHT:
                return autoGoToAngle();
            }
            return new SusanOrder(0.0);
        }

        private SusanOrder autoGoToAngle() {
            double newAngle = desiredAngle - SusanState.currentAngle;
            double angleTolerance = 5; //TODO Determine experimentally
            if (newAngle < angleTolerance) {
                running = false;
                return new SusanOrder(0.0);
            }

            if (newAngle > 90 || newAngle < -90) {
                newAngle = (newAngle > 90) ? 90 : -90;
            } 

            //TODO PD Loop, determine constants
            double susanKp = 1/40;
            double susanKd = 0;

            double velocity = (SusanState.velocity > 0) ? SusanState.velocity : -SusanState.velocity;
            double susanRadius = 5; //TODO Measure radius of lazy susan

            double outputPower = susanKp * newAngle + susanKd * (velocity/susanRadius);

            return new SusanOrder(outputPower);
        }
    }

    private static double desiredAngle = 0.0;

    public SusanTask(SusanMode mode, LazySusan susan, double desiredAngle) {
        running = true;
        this.desiredAngle = desiredAngle;
        susan.setMode(mode);
        Thread t = new Thread("SusanTask");
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
    public static class SusanState {
        public static double velocity = 0.0, currentAngle = 0.0, voltage = 0.0;

        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double velocity, double currentAngle, double voltage) {
            SusanState.velocity = velocity;
            SusanState.currentAngle = currentAngle;
            SusanState.voltage = voltage;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveTask and holds values for the new left and right
     * powers to be set by Drive
     */
    public static class SusanOrder {
        public double power;

        public SusanOrder(double power) {
            this.power = power;
        }
    }
}