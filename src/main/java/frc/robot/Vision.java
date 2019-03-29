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

    private static double[] cornerY;

    public static Hatch hatch;

    private CvSink cvSink;
    private CvSource outputStream;
    private UsbCamera cargoCam;
    private Mat videoImage = new Mat();

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
}