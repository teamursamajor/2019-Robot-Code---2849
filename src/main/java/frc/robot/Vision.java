package frc.robot;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.*;
import org.opencv.core.Mat;

/**
*contains info for the camera as well as setting the 
*resolution and fps of the resulting image.
*/
public class Vision implements UrsaRobot, Runnable {
    public static boolean visionStop, visionRunning;

    private CvSink cvSink;
    private CvSource outputStream;
    private UsbCamera cargoCam;
    private Mat videoImage = new Mat();

    public Vision(){
        cargoCam = new UsbCamera("Cargo Camera", 0);
        CameraServer.getInstance().addCamera(cargoCam);
        cargoCam.setFPS(30);
        cargoCam.setResolution(320, 240);

        cvSink = CameraServer.getInstance().getVideo(cargoCam);
		outputStream = CameraServer.getInstance().putVideo("Cargo Camera", 320, 240);
        visionInit();
    }

    public void visionInit(){
        Thread t = new Thread(this, "Vision Thread");
        t.start();
    }

    /**
    * puts the image received from the screen into an 
    * output stream.
    */
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
