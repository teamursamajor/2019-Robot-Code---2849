package frc.robot;

// import org.usfirst.frc.team2849.autonomous.IntakeTask.IntakeType;
// import org.usfirst.frc.team2849.controls.ControlLayout;
// import org.usfirst.frc.team2849.diagnostics.Logger;
// import org.usfirst.frc.team2849.diagnostics.Logger.LogLevel;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;

public class Drive implements Runnable, UrsaRobot {

	private static Spark mFrontLeft;
	private static Spark mFrontRight;
	private static Spark mRearLeft;
	private static Spark mRearRight;

	private static double leftSpeed;
	private static double rightSpeed;
	private static boolean square;
	private static AHRS ahrs;

	private static Boolean running = new Boolean(false);

	private static Encoder encL;
	private static Encoder encR;

	//private static ControlLayout cont;

	private static final double INCHES_PER_TICK = 0.011505d;

	/**
	 * Constructor for Drive class. Only one Drive object should be instantiated
	 * at any time.
	 * 
	 * @param frontLeft
	 *            Channel number for front left motor
	 * @param frontRight
	 *            Channel number for front right motor
	 * @param rearLeft
	 *            Channel number for rear left motor
	 * @param rearRight
	 *            Channel number for rear right motor
	 */

	public Drive(int frontLeft, int frontRight, int rearLeft, int rearRight) { //, ControlLayout controller) {
		mFrontLeft = new Spark(frontLeft);
		mFrontRight = new Spark(frontRight);
		mRearLeft = new Spark(rearLeft);
		mRearRight = new Spark(rearRight);

		//cont = controller;

		ahrs = new AHRS(SPI.Port.kMXP);

		encL = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
		encR = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

		encL.setDistancePerPulse(INCHES_PER_TICK);
		encR.setDistancePerPulse(INCHES_PER_TICK);
		encR.setReverseDirection(true);

		encL.reset();
		encR.reset();

		startDrive();
	}

	/**
	 * Starts driveThread. Made so that only one driveThread can exist at one
	 * time.
	 */
	private void startDrive() {
		synchronized (running) {
			if (running)
				return;
			running = true;
		}
		new Thread(this, "driveThread").start();
	}

	/**
	 * Run method for driveThread
	 */
	@Override
	// TODO PID
	public void run() {
		while (running) {
			// mFrontLeft.set(-cont.getDrive().getLeftSpeed());
			// mFrontRight.set(cont.getDrive().getRightSpeed());
			// mRearLeft.set(-cont.getDrive().getLeftSpeed());
			// mRearRight.set(cont.getDrive().getRightSpeed());
			
			// if (mFrontLeft.getSpeed() < 0 && mFrontRight.getSpeed() < 0) {
			// 	cont.getIntake().setIntakeType(IntakeType.HOLD);
			// }
			// try {
			// 	Thread.sleep(20);
			// } catch (InterruptedException e) {
			// 	e.printStackTrace();
			// 	Logger.log("Drive run method Thread.sleep call, printStackTrace", LogLevel.ERROR);
			// }
		}
	}

	/**
	 * Kill method for driveThread
	 */
	public void kill() {
		running = false;
	}

	/**
	 * Takes the angle given by the AHRS and turns it into a heading which is
	 * always between 0 and 360 (AHRS can return negative values and values
	 * above 360)
	 */

	public double getHeading() {
		double angle = ahrs.getAngle();
		angle = fixHeading(angle);
		return angle;
	}

	public double getRawHeading() {
		return ahrs.getAngle();
	}

	/**
	 * Takes an angle and turns it into a heading which is always between 0 and
	 * 360
	 * 
	 * @param heading
	 *            Angle to turn into a heading between 0 and 360
	 * @return Heading between 0 and 360
	 */
	public double fixHeading(double heading) {
		heading %= 360;
		while (heading < 0)
			heading += 360;
		return heading;
	}

	public double getLeftEncoder() {
		return encL.getDistance();
	}

	public double getRightEncoder() {
		return encR.getDistance();
	}

	public double getLeftRate() {
		return encL.getRate();
	}

	public double getRightRate() {
		return encR.getRate();
	}

	public void resetEncoders() {
		encL.reset();
		encR.reset();
	}

	public void resetNavx() {
		ahrs.reset();
	}

	/**
	 * Method to stop all four motors
	 */
	public void stop() {
		mFrontLeft.stopMotor();
		mFrontRight.stopMotor();
		mRearLeft.stopMotor();
		mRearRight.stopMotor();
	}

	public static boolean getRunning() {
		return running;
	}

	//As of 3/9/2018 at 5:49 PM this method has been declared sacred and will not be deleted. Ever. -20XX
	public void summonSatan() {
	}

}