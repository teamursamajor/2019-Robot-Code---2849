package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Drive implements Runnable, UrsaRobot {

	private Spark mFrontLeft;
	private Spark mFrontRight;
	private Spark mRearLeft;
	private Spark mRearRight;

	private static boolean square;

	public static boolean running = false;
	private static Object lock = new Object();

	// TODO move these to UrsaRobot?
	private double leftSpeed;
	private double rightSpeed;
	private double kdAutoAlign = 2;
	private double kpAutoAlign = 1.0 / 33.0;
	private double autoAlignTolerance = 0.1;
	private double autoAlignMinimumPower = 0.25;

	//TODO how would we use an enum/why?
	public enum Modes {
		Auto, DriveSticks
	};

	private static Modes mode;

	/**
	 * Constructor for Drive class. Only one Drive object should be instantiated at
	 * any time.
	 * 
	 * @param frontLeft  Channel number for front left motor
	 * @param frontRight Channel number for front right motor
	 * @param rearLeft   Channel number for rear left motor
	 * @param rearRight  Channel number for rear right motor
	 */

	public Drive() {
		mFrontLeft = new Spark(DRIVE_FRONT_LEFT);
		mFrontRight = new Spark(DRIVE_FRONT_RIGHT);
		mRearLeft = new Spark(DRIVE_REAR_LEFT);
		mRearRight = new Spark(DRIVE_REAR_RIGHT);

		leftEncoder.setDistancePerPulse(INCHES_PER_TICK);
		rightEncoder.setDistancePerPulse(INCHES_PER_TICK);
		rightEncoder.setReverseDirection(true);

		leftEncoder.reset();
		rightEncoder.reset();

		startDrive();
	}

	/**
	 * Starts driveThread. Made so that only one driveThread can exist at one time.
	 */
	private void startDrive() {
		synchronized (lock) {
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
	// TODO Write the run method. It will be a PID loop which drives a specified
	// distance that it tracks using sensors/encoders
	public void run() {
		while (running) {
			//TODO update
			mFrontLeft.set(xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y));
			mRearLeft.set(XboxController.AXIS_LEFTSTICK_Y);
			mFrontRight.set(xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));
			mRearRight.set(xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
				// Logger.log("Drive run method Thread.sleep call, printStackTrace",
				// LogLevel.ERROR);
			}
		}
	}

	/**
	 * Sets the enum mode
	 * 
	 * @param tMode
	 */
	public static void setMode(Modes tMode) {
		mode = tMode;
	}

	/**
	 * Kill method for driveThread
	 */
	public void kill() {
		running = false;
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

	public static boolean getRunning() {
		return running;
	}

	/**
	 * As of 3/9/2018 at 5:49 PM this method has been declared sacred and will not
	 * be deleted. Ever. -20XX
	 */
	public void summonSatan() {
	}

	/**
	 * Auto aligns the robot to the tape
	 */
	public void autoAlign() {
		double tv = table.getEntry("tv").getDouble(-1); // Gets current y-coordinate
		// slows down the robot until it sees second tape.
		tv = table.getEntry("tv").getDouble(-1); // detects the presence of reflective tape.
		while (tv != 1) {
			setPower(0.3);
			tv = table.getEntry("tv").getDouble(-1); // detects the presence of reflective tape.
		}

		debugMessage("Saw second tape");
		// alignment code / control loop
		double tx = table.getEntry("tx").getDouble(42.5);
		System.out.println(tx);

		double lastTx = tx;
		double lastTime = System.currentTimeMillis();
		double currentTime;
		double outputPower;

		while (Math.abs(tx) > autoAlignTolerance) {
			// DEBUGGING
			// double output_power;
			currentTime = System.currentTimeMillis();
			tx = table.getEntry("tx").getDouble(42.5);
			if (tx == lastTx) {
				continue;
			}

			// Finding Rate of change in kd
			double rateOfChangeInKD_e = tx - lastTx;
			double rateOfChangeInKD_t = currentTime - lastTime;
			outputPower = kpAutoAlign * tx + kdAutoAlign * (rateOfChangeInKD_e / rateOfChangeInKD_t);
			if (Math.abs(outputPower) < autoAlignMinimumPower) {
				outputPower = Math.signum(outputPower) * autoAlignMinimumPower;
			}

			setPower(outputPower);
			// System.out.println("output power "+kp*tx + kd*(rateOfChangeInKD_e /
			// rateOfChangeInKD_t));
			System.out.println("tx " + tx);
			System.out.println(outputPower);
			lastTime = currentTime;
			lastTx = tx;
			// (tx-last_tx)/(current_time-last_time)
			
<<<<<<< HEAD
            driveTest(outputPower);
            //System.out.println("output power "+kp*tx + kd*(rateOfChangeInKD_e / rateOfChangeInKD_t));
            System.out.println("tx "+tx);
            System.out.println(outputPower);
            lastTime = currentTime;
            lastTx = tx;
            //(tx-last_tx)/(current_time-last_time)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }
        }
		
        //stops motor
        System.out.println("Stopped.");
		driveTest(0.0);
=======
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {

			}
		}

		System.out.println("Stopped drive");
		setPower(0.0);
		// setPower(>9000);
>>>>>>> d13ce816805e31ad9bf3ace9a2fbd3dbeabc0c0c
		mode = Modes.DriveSticks;
	}

	public void setPower(double power) {
		mFrontRight.set(-power);
		mFrontLeft.set(power);
		mRearRight.set(-power);
		mRearLeft.set(power);
	}

	public void debugMessage(String message) {
		message = "DEBUGGING: " + message;
		System.out.println(message);
	}
}