package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;

public class Drive implements Runnable, UrsaRobot {
<<<<<<< HEAD
	
	
	
=======

	// TODO move these to UrsaRobot?
	private static Spark mFrontLeft;
	private static Spark mFrontRight;
	private static Spark mRearLeft;
	private static Spark mRearRight;

>>>>>>> 1e8218de4245b45e2cd27e7fe4e4bd9edc6710ab
	private static double leftSpeed;
	private static double rightSpeed;
	private static boolean square;
	private static AHRS ahrs;
	
	private static Boolean running = new Boolean(false);
	
	private static Encoder encL;
	private static Encoder encR;
<<<<<<< HEAD
	
	//private static ControlLayout cont;
	
=======

>>>>>>> 1e8218de4245b45e2cd27e7fe4e4bd9edc6710ab
	private static final double INCHES_PER_TICK = 0.011505d;
	
	private double kdAutoAlign = 2;
	private double kpAutoAlign = 1.0/33.0;
	private double toleranceAutoAlign = 0.1;
	private double minimumPowerAutoAlign = 0.25;

	public enum Modes {Auto, DriveSticks};

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

<<<<<<< HEAD
	public Drive(int frontLeft, int frontRight, int rearLeft, int rearRight) { //, ControlLayout controller) {
		
=======
	public Drive(int frontLeft, int frontRight, int rearLeft, int rearRight) { // , ControlLayout controller) {
		mFrontLeft = new Spark(frontLeft);
		mFrontRight = new Spark(frontRight);
		mRearLeft = new Spark(rearLeft);
		mRearRight = new Spark(rearRight);
>>>>>>> 1e8218de4245b45e2cd27e7fe4e4bd9edc6710ab

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
	 * Starts driveThread. Made so that only one driveThread can exist at one time.
	 */
	private void startDrive() {
		//TODO we shouldn't be synchronizing on a boolean, but rather an object. We need to update this throughout the project
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
	// TODO Write the run method. It will be a PID loop which drives a specified distance that it tracks using sensors/encoders
	public void run() {
		while (running) {
			// mFrontLeft.set(-cont.getDrive().getLeftSpeed());
			// mFrontRight.set(cont.getDrive().getRightSpeed());
			// mRearLeft.set(-cont.getDrive().getLeftSpeed());
			// mRearRight.set(cont.getDrive().getRightSpeed());

			// if (mFrontLeft.getSpeed() < 0 && mFrontRight.getSpeed() < 0) {
			// cont.getIntake().setIntakeType(IntakeType.HOLD);
			// }
<<<<<<< HEAD

			switch (mode) {
			case Auto:	
				System.out.println("Mode: Auto");
				AutoAlign();
				mode = Modes.DriveSticks;
				break;
			case DriveSticks:
				System.out.println("Mode: DriveSticks");
				break;
			default: 
				mode = Modes.DriveSticks;
				break;
=======
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
				// Logger.log("Drive run method Thread.sleep call, printStackTrace",
				// LogLevel.ERROR);
>>>>>>> 1e8218de4245b45e2cd27e7fe4e4bd9edc6710ab
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
		return encL.getDistance();
	}

	public double getRightEncoder() {
		return encR.getDistance();
	}

	/**
	 * 
	 * @return Get the current rate of the encoder. Units are distance per second as
	 *         scaled by the value from setDistancePerPulse().
	 */
	public double getLeftRate() {
		return encL.getRate();
	}

	/**
	 * 
	 * @return Get the current rate of the encoder. Units are distance per second as
	 *         scaled by the value from setDistancePerPulse().
	 */
	public double getRightRate() {
		return encR.getRate();
	}

	/**
	 * Resets the current encoder distance to zero
	 */
	public void resetEncoders() {
		encL.reset();
		encR.reset();
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

	public void AutoAlign() { 
        double tv = table.getEntry("tv").getDouble(-1); //Gets current y-coordinate
        //slows down the robot until it sees second tape.
        tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
        while(tv!=1) {
            driveTest(0.3);
            tv = table.getEntry("tv").getDouble(-1); //detects the presence of reflective tape.
		}
		
        debugMessage("Saw second tape");
        //alignment code / control loop 
        double tx = table.getEntry("tx").getDouble(42.5);
        System.out.println(tx);
		
        double lastTx = tx;
        double lastTime = System.currentTimeMillis();
        double currentTime;
        double outputPower; 
        
        while(Math.abs(tx) > toleranceAutoAlign) {
            //DEBUGGING
            //double output_power;
            currentTime = System.currentTimeMillis();
            tx = table.getEntry("tx").getDouble(42.5);
            if(tx == lastTx) {
                continue;
            }
        
            //Finding Rate of change in kd
            double rateOfChangeInKD_e = tx - lastTx;
            double rateOfChangeInKD_t = currentTime - lastTime;
            outputPower = kpAutoAlign*tx + kdAutoAlign*(rateOfChangeInKD_e / rateOfChangeInKD_t);
            if (Math.abs(outputPower) < minimumPowerAutoAlign) {
                outputPower = Math.signum(outputPower)*minimumPowerAutoAlign;
			}
			
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
		driveTest(0.01);
		mode = Modes.DriveSticks;
    }
    
    public void driveTest(double power) {
        mFrontRight.set(-power);
        mFrontLeft.set(power);
        mRearRight.set(-power);
        mRearLeft.set(power);
    }

    public void debugMessage(String message){
        message = "DEBUGGING: " + message;
        System.out.println(message);
    }
}