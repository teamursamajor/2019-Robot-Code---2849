package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;
import frc.tasks.DriveTask.DriveMode;

public class Drive extends Subsystem<DriveTask.DriveMode> implements UrsaRobot {

	private Spark mFrontLeft;
	private Spark mFrontRight;
	private Spark mRearLeft;
	private Spark mRearRight;

	// TODO need?
	private static boolean square;

	private double leftPower;
	private double rightPower;

	/**
	 * Constructor for Drive class. Only one Drive object should be instantiated at
	 * any time.
	 */

	public Drive() {
		subsystemMode = DriveMode.DRIVE_STICKS;

		mFrontLeft = new Spark(DRIVE_FRONT_LEFT);
		mFrontRight = new Spark(DRIVE_FRONT_RIGHT);
		mRearLeft = new Spark(DRIVE_REAR_LEFT);
		mRearRight = new Spark(DRIVE_REAR_RIGHT);

		leftEncoder.setDistancePerPulse(INCHES_PER_TICK);
		rightEncoder.setDistancePerPulse(INCHES_PER_TICK);
		rightEncoder.setReverseDirection(true);

		leftEncoder.reset();
		rightEncoder.reset();	
	}

	/**
	 * Updates the DriveState class (in DriveTask) with current power and position,
	 * then iterates the loop once and sets the motor powers according to the new
	 * results
	 */
	public void runSubsystem() {
		updateStateInfo();
		DriveTask.DriveOrder driveOrder = subsystemMode.callLoop();

		mFrontLeft.set(-driveOrder.leftPower);
		mFrontRight.set(driveOrder.rightPower);
		mRearLeft.set(-driveOrder.leftPower);
		mRearRight.set(driveOrder.rightPower);
	}

	public void updateStateInfo() {
		// TODO update distances to use information from encoders alongside limelight
		// maybe average the encoder distances with limelight? idk
		double leftDistance = limelightTable.getEntry("tx").getDouble(Double.NaN);
		double rightDistance = limelightTable.getEntry("tx").getDouble(Double.NaN);

		// Calculate robot velocity
		double leftVelocity, rightVelocity;

		// For underclassmen, Delta means "change in"
		double leftDeltaPos = leftDistance - DriveTask.DriveState.leftPos;
		double deltaTime = System.currentTimeMillis() - DriveTask.DriveState.stateTime;
		leftVelocity = (leftDeltaPos / deltaTime);

		double rightDeltaPos = rightDistance - DriveTask.DriveState.rightPos;
		rightVelocity = (rightDeltaPos / deltaTime);

		/*
		 * Our loop updates faster than the limelight. If the limelight hasn't updated
		 * yet, then our change in position is 0. In this case, we want to skip this
		 * iteration and wait for the next cycle
		 */
		if (leftDeltaPos == 0 || rightDeltaPos == 0)
			return;

		DriveTask.DriveState.updateState(leftVelocity, rightVelocity, leftDistance,
				rightDistance);
	}

	/**
	 * Since the NavX can return values below 0 or above 360, this fixes it and
	 * returns a proper heading
	 * 
	 * @return Fixed heading from the NavX always between 0 and 360
	 */

	public double getHeading() {
		double angle = ahrs.getAngle();
		angle = fixHeading(angle);
		return angle;
	}

	/**
	 * 
	 * @return The raw angle from the NavX. This can return values below 0 or above
	 *         360
	 */
	public double getRawHeading() {
		return ahrs.getAngle();
	}

	/**
	 * Takes an angle and turns it into a heading which is always between 0 and 360
	 * 
	 * @param heading Raw angle to convert into a heading between 0 and 360
	 * @return Heading between 0 and 360
	 */
	public double fixHeading(double heading) {
		heading %= 360;
		while (heading < 0)
			heading += 360;
		return heading;
	}

	public double getLeftEncoder() {
		return leftEncoder.getDistance();
	}

	public double getRightEncoder() {
		return rightEncoder.getDistance();
	}

	/**
	 * 
	 * @return Get the current rate of the encoder. Units are distance per second as
	 *         scaled by the value from setDistancePerPulse().
	 */
	public double getLeftRate() {
		return leftEncoder.getRate();
	}

	/**
	 * 
	 * @return Get the current rate of the encoder. Units are distance per second as
	 *         scaled by the value from setDistancePerPulse().
	 */
	public double getRightRate() {
		return rightEncoder.getRate();
	}

	/**
	 * Resets the current encoder distance to zero
	 */
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	/**
	 * Resets the Gyro Z axis to a heading of zero. This can be used if there is
	 * significant drift in the gyro and it needs to be recalibrated after it has
	 * been running.
	 */
	public void resetNavx() {
		ahrs.reset();
	}

	/**
	 * Stops all four motors. Remember that robot will still have forward momentum
	 * and slide slightly
	 */
	public void stop() {
		mFrontLeft.stopMotor();
		mFrontRight.stopMotor();
		mRearLeft.stopMotor();
		mRearRight.stopMotor();
	}

	/**
	 * Sets all drive motors to the same power
	 * @param power
	 */
	public void setPower(double power) {
		mFrontRight.set(-power);
		mFrontLeft.set(power);
		mRearRight.set(-power);
		mRearLeft.set(power);
	}

	// TODO Delete eventually, once drive code is perfect in teleop
	public void testDrive() {
		XboxController xbox = new XboxController(0);
		mFrontLeft.set(-xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y));
		mFrontRight.set(xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));
		mRearLeft.set(-xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y));
		mRearRight.set(xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));
	}

	public void debugMessage(String message) {
		message = "DEBUGGING: " + message;
		System.out.println(message);
	}

	/**
	 * As of 3/9/2018 at 5:49 PM this method has been declared sacred and will not
	 * be deleted. Ever. -20XX
	 * 
	 * During the holidays, summonSanta() is an acceptable replacement. -20XX
	 */
	public void summonSatan() {
	}

}