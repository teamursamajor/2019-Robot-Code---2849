package frc.tasks;

import frc.robot.Turntable;
import frc.robot.UrsaRobot;

public class TurntableTask extends Task {

    public enum TurntableMode {
        FORWARD, LEFT, RIGHT, AUTO_ALIGN, CUSTOM;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return TurntableOrder containing the left and right powers
         */

        public TurntableOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case FORWARD:
                desiredVoltage = UrsaRobot.forwardVoltage;
                return autoGoToAngle();
            case LEFT:
                desiredVoltage = UrsaRobot.leftVoltage;
                return autoGoToAngle();
            case RIGHT:
                desiredVoltage = UrsaRobot.rightVoltage;
                return autoGoToAngle();
            case AUTO_ALIGN:
                return autoGoToAngle();
            case CUSTOM:
                return autoGoToAngle();
            }
            return autoGoToAngle();
        }

        private TurntableOrder autoGoToAngle() {
            return new TurntableOrder(0.0); // TODO write code
        }
    }

    public static double desiredVoltage = 0.0;

    public TurntableTask(TurntableMode mode, Turntable turntable) {
        running = true;
        turntable.setMode(mode);
        Thread t = new Thread("Turntable Task");
        t.start();
    }

    public TurntableTask(double voltage, Turntable turntable) {
        running = true;
        desiredVoltage = voltage;
        turntable.setMode(TurntableMode.CUSTOM);
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
        public static double voltage = 0.0, velocity = 0.0;

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