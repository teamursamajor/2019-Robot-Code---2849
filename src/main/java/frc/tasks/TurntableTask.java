package frc.tasks;

import frc.robot.Turntable;
import frc.robot.UrsaRobot;
import frc.robot.Constants;
import frc.robot.XboxController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;

public class TurntableTask extends Task implements UrsaRobot {

    public enum TurntableMode {
        //FORWARD, LEFT, RIGHT, 
        AUTO_ALIGN, TRIGGERS, CUSTOM;

        public TurntableOrder callLoop() {
            // TODO remove or write code for FORWARD, LEFT, and RIGHT
            // "this" refers to subsystemMode
            switch (this) {
            //case FORWARD:
                // desiredVoltage = UrsaRobot.forwardVoltage;
                // return autoGoToAngle();
            //case LEFT:
                // desiredVoltage = UrsaRobot.leftVoltage;
                // return autoGoToAngle();
            //case RIGHT:
                // desiredVoltage = UrsaRobot.rightVoltage;
                // return autoGoToAngle();
            case AUTO_ALIGN:
                return autoAlign();
            case TRIGGERS:
                // System.out.println("triggers");
                return triggersBox();
            case CUSTOM:
                // System.out.println("custom");
                return null;
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

            double kpAutoAlign = 1.0 / 100.0; // Proportional coefficient for PID controller
            double autoAlignTolerance = 0.1;
            double autoAlignMinimumPower = 0.15;
            double autoAlignMaximumPower = 0.3;

            double goalPosition = 0.0; // on the limelight, 0.0 is the center

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
            // SmartDashboard.putNumber("LimelightArea", area);


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

            if (Math.abs(outputPower) > autoAlignMaximumPower) {
                outputPower = Math.signum(outputPower) * autoAlignMaximumPower;
            }

            SmartDashboard.putNumber("Testing Turntable outputPower", outputPower);

            return new TurntableOrder(outputPower);

        }

        private TurntableOrder triggersBox() {
            if (xbox.getAxisGreaterThan(controls.map.get("turntable_left"), 0.1)) {
                return new TurntableOrder(Constants.turntablePower);
            } else if (xbox.getAxisGreaterThan(controls.map.get("turntable_right"), 0.1)) {
                return new TurntableOrder(-Constants.turntablePower);
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