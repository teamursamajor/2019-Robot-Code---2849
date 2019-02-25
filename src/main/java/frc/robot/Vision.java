package frc.robot;

import org.opencv.core.Mat;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Vision implements Runnable {
	private static CvSink cvSink;
	private static CvSource outputStream;
	private static UsbCamera cargoCam;
	private static Mat image;
	private static Thread visionRun = null;

	public Vision() {
		/*
		 * TODO add the limelight camera
		 * This means adding:
		 * 		a) the camera feed itself
		 * 		b) the functionality to switch pipelines to use limelight for vision processing
		 * 		c) the functionality to see both cameras at once(if possible)
		 * 			i) if b is not possible, be able to switch between both cameras at will
		 * 		d) maybe even the functionality to show the processed pipeline
		*/
		cargoCam = new UsbCamera("Cargo Camera", 0);
		CameraServer.getInstance().addCamera(cargoCam);

		cargoCam.setResolution(240, 180);

		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Cargo Camera", 240, 180);

		image = new Mat();
	}

	public static void visionInit() {
		visionRun = new Thread(new Vision(), "Vision Thread");
		visionRun.start();
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