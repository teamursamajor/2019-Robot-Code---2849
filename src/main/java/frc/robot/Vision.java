package frc.robot;

import org.opencv.core.Mat;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Vision implements Runnable {
	private static CvSink cvSink;
	private static CvSource outputStream;
	private static UsbCamera intakeCam;
	private static Mat image = new Mat();
	private static Thread visionRun = null;

	public Vision() {
		intakeCam = new UsbCamera("Intake Camera", 0);

		CameraServer.getInstance().addCamera(intakeCam);

//		intakeCam.setResolution(240, 180);

		cvSink = CameraServer.getInstance().getVideo(intakeCam);
		outputStream = CameraServer.getInstance().putVideo("Intake Camera", 240, 180);
	}

	public static void visionInit() {
		visionRun = new Thread(new Vision(), "Vision Thread");
		visionRun.start();
		// Logger.log("Starting Vision Thread", LogLevel.INFO);
	}

	public void run() {
		while (true) {
			cvSink.grabFrame(image);
			outputStream.putFrame(image);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}