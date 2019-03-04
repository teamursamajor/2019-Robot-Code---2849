package frc.robot;

import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.tasks.HatchTask.HatchMode;

public class Vision implements UrsaRobot {
    // TODO implement visionStop/visionRunning
    private static boolean visionStop;
    public static boolean visionRunning;
    public static boolean turning = false;
    private static VisionDirection turnDirection;

    // TODO implement limelight table stuff correctly
    private static double[] cornerY; // TODO temporary

    public static Hatch hatch;

    public enum VisionDirection {
        LEFT, RIGHT;
    }

    public static void autoAlign() {
        visionStop = false;
        visionRunning = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                runVision();
            }
        }).start();
    }

    private static void runVision() {
        // set pipeline to single target
        limelightTable.getEntry("pipeline").setDouble(1);

        // looking at the corners, determine what direction to move in
        NetworkTableEntry tcorny = limelightTable.getEntry("tcorny");

        cornerY = tcorny.getDoubleArray(new double[2]);
        if (cornerY[0] > cornerY[1]) {
            turnDirection = VisionDirection.RIGHT;
        } else {
            turnDirection = VisionDirection.LEFT;
        }

        // set pipeline to double target
        limelightTable.getEntry("pipeline").setDouble(2);

        // move in that direction until you get a hit
        while (limelightTable.getEntry("tv").getDouble(0.0) != 1.0) {
            switch (turnDirection) {
            case RIGHT:
                Turntable.turntableMotor.set(-0.3);
                break;
            case LEFT:
                Turntable.turntableMotor.set(0.3);
                break;
            default:
                Turntable.turntableMotor.set(0.0);
                break;
            }
        }

        // stop the motor from moving until the PID loop runs
        Turntable.turntableMotor.set(0.0);

        // PID loop
        visionPID();

        // set pipeline to raw camera
        limelightTable.getEntry("pipeline").setDouble(0);

        // run hatch
        hatch.setMode(HatchMode.RUN);
        visionRunning = false;
    }

    private static void visionPID() {
        turning = true;
        while (turning) {
            double kpAutoAlign = 1.0 / 100.0; // Proportional coefficient for PID controller
            double autoAlignTolerance = 0.1;
            double autoAlignMinimumPower = 0.15;
            double autoAlignMaximumPower = 0.3;

            double goalPosition = 0.0; // on the limelight, 0.0 is the center

            NetworkTableEntry tx = limelightTable.getEntry("tx");
            NetworkTableEntry ty = limelightTable.getEntry("ty");
            NetworkTableEntry ta = limelightTable.getEntry("ta");

            // read values periodically
            double x = tx.getDouble(0.0);
            double y = ty.getDouble(0.0);
            double area = ta.getDouble(0.0);

            // If we're already close enough to the tapes, then simply stop
            double centerPos = limelightTable.getEntry("tx").getDouble(Double.NaN);
            if (Math.abs(centerPos) < autoAlignTolerance) {
                turning = false;
                Turntable.turntableMotor.set(0.0);
                return;
            }

            // PD equations power = kp * change in distance (aka error) + kd * velocity
            double outputPower = kpAutoAlign * (limelightTable.getEntry("tx").getDouble(Double.NaN) - goalPosition);

            if (outputPower == 0) {
                turning = false;
                return;
            }

            if (Math.abs(outputPower) < autoAlignMinimumPower) {
                outputPower = Math.signum(outputPower) * autoAlignMinimumPower;
            }

            if (Math.abs(outputPower) > autoAlignMaximumPower) {
                outputPower = Math.signum(outputPower) * autoAlignMaximumPower;
            }

            Turntable.turntableMotor.set(outputPower);
        }
    }
}