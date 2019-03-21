package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.*;
import org.opencv.core.Mat;
// import frc.tasks.HatchTask.HatchMode;

public class Vision implements UrsaRobot, Runnable {
    public static boolean visionStop;
    public static boolean visionRunning;
    public static boolean turning = false;
    private static VisionDirection turnDirection;

    private static double[] cornerY;

    public static Hatch hatch;

    private CvSink cvSink;
    private CvSource outputStream;
    private UsbCamera cargoCam;
    private Mat videoImage = new Mat();

    public enum VisionDirection {
        LEFT, RIGHT;
    }

    public Vision(){
        cargoCam = new UsbCamera("Cargo Camera", 0);
        CameraServer.getInstance().addCamera(cargoCam);
        cargoCam.setFPS(30); // TODO test
        cargoCam.setResolution(320, 240); // TODO test

        cvSink = CameraServer.getInstance().getVideo(cargoCam);
		outputStream = CameraServer.getInstance().putVideo("Cargo Camera", 320, 240);
        visionInit();
    }

    public void visionInit(){
        Thread t = new Thread(this, "Vision Thread");
        t.start();
    }

    public void run(){
        while(true){
            cvSink.grabFrame(videoImage);
            outputStream.putFrame(videoImage);
            try {
                Thread.sleep(20);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
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
        try {
            Thread.sleep(20);
        } catch (Exception e){
            e.printStackTrace();
        }
        // set pipeline to single target
        limelightTable.getEntry("pipeline").setDouble(1);

        // using the coords of the corners, determine what direction to move in
        NetworkTableEntry tcorny = limelightTable.getEntry("tcorny");

        cornerY = tcorny.getDoubleArray(new double[2]);

        // (0,0) is the top left of the camera, so if Y is numerically greater then it
        // is actually physically lower
        try {
            if (cornerY[0] > cornerY[3]) {
                turnDirection = VisionDirection.LEFT;
            } else if (cornerY[0] < cornerY[3]) {
                turnDirection = VisionDirection.RIGHT;
            }
        } catch (Exception e) {
            System.out.println("Error with Vision corner processing!");
            e.printStackTrace();
        }

        // set pipeline to double target
        limelightTable.getEntry("pipeline").setDouble(2);

        // move in that direction until you get a hit
        while (limelightTable.getEntry("tv").getDouble(0.0) != 1.0) {
            if (visionStop) {
                killVision();
                return;
            }
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
            try {
                Thread.sleep(20);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        // stop the motor from moving until the PID loop runs
        Turntable.turntableMotor.set(0.0);

        // PID loop
        visionPID();

        // set pipeline to raw camera
        limelightTable.getEntry("pipeline").setDouble(0);

        if (visionStop) {
            killVision();
            return;
        }

        // TODO run hatch
        visionRunning = false;
    }

    private static void visionPID() {
        turning = true;
        while (turning) {
            if (visionStop) {
                killVision();
                return;
            }
            double kpAutoAlign = 1.0 / 100.0; // Proportional coefficient for PID controller
            double autoAlignTolerance = 0.1;
            double autoAlignMinimumPower = 0.15;
            double autoAlignMaximumPower = 0.3;

            double goalPosition = 0.0; // on the limelight, 0.0 is the center

            NetworkTableEntry tx = limelightTable.getEntry("tx");
            NetworkTableEntry ty = limelightTable.getEntry("ty");
            // NetworkTableEntry ta = limelightTable.getEntry("ta"); // target area

            // read values periodically
            double x = tx.getDouble(0.0);
            // double y = ty.getDouble(0.0);
            // double area = ta.getDouble(0.0);

            // If we're already close enough to the tapes, then simply stop
            double centerPos = x;
            if (Math.abs(centerPos) < autoAlignTolerance) {
                turning = false;
                Turntable.turntableMotor.set(0.0);
                return;
            }

            // PD equations power = kp * change in distance (aka error) + kd * velocity
            double outputPower = kpAutoAlign * (x - goalPosition);

            if (Math.abs(outputPower) <= 0.05) {
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

    private static void killVision() {
        limelightTable.getEntry("pipeline").setDouble(0);
        Turntable.turntableMotor.set(0.0);
        // TODO fix later
        // Vision.hatch.setMode(HatchMode.WAIT);
    }
}