package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public interface UrsaRobot {
	//TODO Finalize Ports
	// Spark Ports
	//5 and 8 unused, 3 and 4 are temporary
	public static final int DRIVE_LEFT = 0;
	public static final int DRIVE_RIGHT = 9;

	public static final int CARGO_INTAKE = 2;
	public static final int CARGO_LIFT = 6;

	public static final int HATCH = 3;

	public static final int CLIMB_FRONT = 1;
	public static final int CLIMB_BACK = 8;
	
	public static final int LAZY_SUSAN = 4;

	//temporary spark piston
	public static final int EXTEND = 11;
	public static final int TURNTABLE = 12;

	// Encoders and Sensors Ports
	public static final int CONTROLLER_PORT = 0;

	//Piston Port
	public static final int PISTON_PORT = 5;

	public static final int LEFT_ENCODER_CHANNEL_A = 4;
	public static final int LEFT_ENCODER_CHANNEL_B = 5;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;

	public static final int CLIMB_ENCODER_CHANNEL_A = 0;
	public static final int CLIMB_ENCODER_CHANNEL_B = 1;

	// Encoders
	public static Encoder leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	public static Encoder rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

	public static Encoder climbEncoder = new Encoder(CLIMB_ENCODER_CHANNEL_A, CLIMB_ENCODER_CHANNEL_B);

	// Tells the encoder the value of each tick. Must be set in the corresponding
	// file with encoder.setDistancePerPulse([Name]_PER_TICK);
	public static final double INCHES_PER_TICK = 0.011505d;
	
	public static final double ROBOT_WIDTH_INCHES = 28d;
	public static final double ROBOT_DEPTH_INCHES = 31.5d;

	public static final double ROBOT_WIDTH_FEET = ROBOT_WIDTH_INCHES / 12.0;
	public static final double ROBOT_DEPTH_FEET = ROBOT_WIDTH_FEET / 12.0;

	//Radius of the robot and cargo
	public static final double robotRadius = 15; // temporary TODO what do we do here
	public static final double cargoRadius = 5; //TODO Measure cargo arm

	// Hatch angles

	//Cargo Angles
	public static final double cargoGroundVoltage = 130, cargoBayVoltage = 255;
	public static final double cargoLowRocketVoltage = 225, cargoStartVoltage = 270;

	// Path settings
	public static final double MAX_VELOCITY = 160; // inches / second
	public static final double MAX_ACCELERATION = 80; // inches / second ^2

	// Nav-X
	AHRS ahrs = new AHRS(SPI.Port.kMXP);

	// Limelight
	NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

	// Xbox Controller
	XboxController xbox = new XboxController(0);

	/**
	 * 2/5/19 - This enum has been redeclared sacred and shall n♀t be deleted, no matter 
	 * what Evan may say. - Isåàç
	 */
	public enum SickoMode {
		SICKO, BAMBA, SHECK, TRAVIS
	}
}