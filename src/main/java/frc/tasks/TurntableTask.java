package frc.tasks;

import frc.robot.Turntable;
import frc.robot.UrsaRobot;
import frc.robot.Constants;
import frc.robot.XboxController;

import edu.wpi.first.networktables.*;

public class TurntableTask extends Task implements UrsaRobot {

    public enum TurntableMode {
        FORWARD, LEFT, RIGHT, AUTO_ALIGN, CUSTOM;

        public TurntableOrder callLoop() {
            // TODO remove or write code for FORWARD, LEFT, and RIGHT
            // "this" refers to subsystemMode
            switch (this) {
            case FORWARD:
                // desiredVoltage = UrsaRobot.forwardVoltage;
                // return autoGoToAngle();
            case LEFT:
                // desiredVoltage = UrsaRobot.leftVoltage;
                // return autoGoToAngle();
            case RIGHT:
                // desiredVoltage = UrsaRobot.rightVoltage;
                // return autoGoToAngle();
            case AUTO_ALIGN:
                return autoAlign();
            case CUSTOM:
                System.out.println("custom");
                return triggersBox();
            }
            return new TurntableOrder(0.0);
        }

        private static boolean turning = true;

        /**
         * Auto aligns the turntable to the vision targets
         * 
         * @param matchPairs the number of pairs of targets to match before stopping.
         *                   This includes "wrong" pairs, so for "real" pairs use 1, 3,
         *                   or 5
         * @return A TurntableOrder which contains the power for the turntable
         */
        private TurntableOrder autoAlign() {
            int matchPairs = 1; // TODO update

            double kpAutoAlign = 1.0 / 200.0; // Proportional coefficient for PID controller
            double autoAlignTolerance = 0.1;
            double autoAlignMinimumPower = 0.15;

            double goalPosition = 0.0; // on the limelight, 0.0 is the center

            // Loop through pairs of tape
            int hatchCount = 0; // actual number of hatches
            int count = 0; // general counter variable
            int tapePairPresent;

            NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
            NetworkTableEntry tx = table.getEntry("tx");
            NetworkTableEntry ty = table.getEntry("ty");
            NetworkTableEntry ta = table.getEntry("ta");

            // read values periodically
            double x = tx.getDouble(0.0);
            double y = ty.getDouble(0.0);
            double area = ta.getDouble(0.0);

            // //post to smart dashboard periodically
            // SmartDashboard.putNumber("LimelightX", x);
            // SmartDashboard.putNumber("LimelightY", y);
            // SmartDashboard.putNumber("LimelightArea", area);e

            // TODO do we need this? if so i can add a keyword to autocompiler
            // while (hatchCount < matchPairs) {
            //     // Count the number of valid tape pairs we've encountered
            //     tapePairPresent = (int) limelightTable.getEntry("tv").getDouble(0);
            //     if (tapePairPresent == 1)
            //         count++;
            //     if (count % 2 == 1) // if (count is odd)
            //         hatchCount++; // skips "even" pairs to avoid false positives
            //     System.out.println("Count: " + count);
            //     System.out.println("Hatch Count: " + hatchCount);
            //     // Wait before trying to match a pair of tape again
            //     try {
            //         Thread.sleep(250); // TODO adjust if necessary
            //     } catch (InterruptedException e) {
            //         e.printStackTrace();
            //     }
            // }

            // If we're already close enough to the tapes, then simply stop
            double centerPos = limelightTable.getEntry("tx").getDouble(Double.NaN);
            if (Math.abs(centerPos) < autoAlignTolerance) {
                turning = false;
                return new TurntableOrder(0.0);
            }

            // PD equations power = kp * change in distance (aka error) + kd * velocity
            double outputPower = kpAutoAlign * (limelightTable.getEntry("tx").getDouble(Double.NaN) - goalPosition);

            if (outputPower == 0) {
                turning = false;
            }

            if (Math.abs(outputPower) < autoAlignMinimumPower) {
                outputPower = Math.signum(outputPower) * autoAlignMinimumPower;
            }

            return new TurntableOrder(outputPower);

        }

        private TurntableOrder triggersBox() {
            // if(xbox.getButton(XboxController.BUTTON_X)){
            //     return new TurntableOrder(-0.25);
            // } else if (xbox.getButton(XboxController.BUTTON_Y)){
            //     return new TurntableOrder(0.25);
            // }
            if (xbox.getAxisGreaterThan(controls.map.get("turntable_left"), 0.1)) {
                System.out.println("left");
                return new TurntableOrder(-Constants.turntablePower);
            } else if (xbox.getAxisGreaterThan(controls.map.get("turntable_right"), 0.1)) {
                System.out.println("right");
                return new TurntableOrder(Constants.turntablePower);
            } else {
                return new TurntableOrder(0.0);
            }
        }
    }

    // TODO update this to use a timer
    public TurntableTask(TurntableMode mode, Turntable turntable) {
        running = true;
        turntable.setMode(mode);
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
     * This is returned by the TurntableTask and holds values for the new left and
     * right powers to be set by Turntable
     */
    public static class TurntableOrder {
        public double power;

        public TurntableOrder(double power) {
            this.power = power;
        }
    }
}