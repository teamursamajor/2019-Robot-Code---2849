package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.tasks.HatchTask.HatchMode;

public class Vision implements UrsaRobot {
    // TODO implement visionStop/visionRunning
    private static boolean visionStop;
    public static boolean visionRunning;
    private static VisionDirection turnDirection;

    // TODO implement limelight table stuff correctly
    private static int[] cornerX, cornerY; // TODO temporary

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
        NetworkTableEntry tcornx = limelightTable.getEntry("tcornx");
        NetworkTableEntry tcorny = limelightTable.getEntry("tcorny");


        // TODO this array isnt actually a thing, replace with NetworkTableEntry stuff
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

    private static void visionPID(){
        //TODO copy PID code here, is neater this way
    }
}