package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.DriveTask;
import frc.tasks.DriveTask.DriveMode;

public class Drive extends Subsystem<DriveTask.DriveMode> implements UrsaRobot {

	public static Spark mFrontLeft;
	public static Spark mFrontRight;
	public static Spark mRearLeft;
	public static Spark mRearRight;

	public static boolean cargoIsFront = true;

	/**
	 * Constructor for Drive class. Only one Drive object should be instantiated at
	 * any time.
	 */
	public Drive() {
		setMode(DriveMode.DRIVE_STICKS);

		mFrontRight = new Spark(DRIVE_FRONT_RIGHT);
		mFrontLeft = new Spark(DRIVE_FRONT_LEFT);
		// mRearLeft = new Spark(DRIVE_BACK_LEFT);
		// mRearRight = new Spark(DRIVE_BACK_RIGHT);

		mRearLeft = mFrontLeft;
		mRearRight = mFrontRight;
		
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
		double leftDistance = getLeftEncoder();
		double rightDistance = getRightEncoder();

		// Calculate robot velocity
		// For underclassmen, delta means "change in"
		double deltaTime = System.currentTimeMillis() - DriveTask.DriveState.stateTime;

		double leftDeltaPos = leftDistance - DriveTask.DriveState.leftPos;
		double leftVelocity = (leftDeltaPos / deltaTime);

		double rightDeltaPos = rightDistance - DriveTask.DriveState.rightPos;
		double rightVelocity = (rightDeltaPos / deltaTime);

		double averageDeltaPos = (leftDeltaPos + rightDeltaPos) / 2.0;
		if (Math.abs(averageDeltaPos) <= 5 || deltaTime <= 5)
			return;

		DriveTask.DriveState.updateState(leftVelocity, rightVelocity, leftDistance, rightDistance, getHeading());
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
	 * @return The raw angle from the NavX. May be below 0 or above 360
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
	 * @return Get the current rate of the encoder. Units are distance per second as
	 *         scaled by the value from setDistancePerPulse().
	 */
	public double getLeftRate() {
		return leftEncoder.getRate();
	}

	/**
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
	public static void stop() {
		mFrontLeft.stopMotor();
		mFrontRight.stopMotor();
		mRearLeft.stopMotor();
		mRearRight.stopMotor();
	}

	/**
	 * Sets all drive motors to the same power. Accounts for the flip between the
	 * left and right sides
	 * 
	 * @param power
	 */
	public static void setPower(double power) {
		mFrontRight.set(power);
		mFrontLeft.set(-power);
		mRearLeft.set(-power);
		mRearRight.set(power);
	}

	public static void setLeftPower(double power){
		mFrontLeft.set(-power);
		mRearLeft.set(-power);
	}

	public static void setRightPower(double power){
		mFrontRight.set(power);
		mRearRight.set(power);
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