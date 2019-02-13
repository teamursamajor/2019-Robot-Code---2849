package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public interface UrsaRobot {
	// Spark Ports
	// TODO For testing 2/5 DRIVE_FRONT_LEFT is 3. Change to 1 when done.
	public static final int DRIVE_LEFT = 0;
	// TODO For testing 2/5 DRIVE_FRONT_RIGHT is 0. Change to 2 when done.
	public static final int DRIVE_RIGHT = 1; // Originally 0
	// TODO For testing 2/5 DRIVE_REAR_LEFT is 6. Change to 0 when done.
	public static final int DRIVE_REAR_LEFT = 2; //Originally 6; 5 leads to spark 6
	// TODO For testing 2/5 DRIVE_REAR_RIGHT is 1. Change to 3 when done.
	public static final int DRIVE_REAR_RIGHT = 3; 

	// TODO do we need both of these? idk 
	public static final int CARGO = 7;
	public static final int CARGO_FRONT = 8; //Changed from 6

	public static final int HATCH = 9;

	public static final int CLIMB_FRONT = 10; // Swap with 8
	public static final int CLIMB_BACK = 5; //Originally 4
	
	// public static final int LAZY_SUSAN = 4;
	// TODO For testing 2/5 LAZY_SUSAN is 9. Undo when done.
	public static final int LAZY_SUSAN = 6; //Originally 9; 4 leads to spark 9

	// Encoders and Sensors Ports
	public static final int CONTROLLER_PORT = 0;

	//Piston Port
	public static final int PISTON_PORT = 5;

	public static final int LEFT_ENCODER_CHANNEL_A = 0;
	public static final int LEFT_ENCODER_CHANNEL_B = 1;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;

	public static final int CARGO_ENCODER_CHANNEL_A = 8;
	public static final int CARGO_ENCODER_CHANNEL_B = 9;
	public static final int HATCH_ENCODER_CHANNEL_A = 6;
	public static final int HATCH_ENCODER_CHANNEL_B = 7;

	public static final int SUSAN_SWITCH_CHANNEL = 4;

	// Encoders
	public static Encoder hatchEncoder = new Encoder(HATCH_ENCODER_CHANNEL_A, HATCH_ENCODER_CHANNEL_B); // 7 pulses/rev

	public static Encoder cargoEncoder = new Encoder(CARGO_ENCODER_CHANNEL_A, CARGO_ENCODER_CHANNEL_B);

	public static Encoder leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	public static Encoder rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

	// Tells the encoder the value of each tick. Must be set in the corresponding
	// file with encoder.setDistancePerPulse([Name]_PER_TICK);
	public static final double INCHES_PER_TICK = 0.011505d;
	public static final double HATCH_DEGREES_PER_TICK = 0.72434608d;
	public static final double CARGO_DEGREES_PER_TICK = 0.0; // TODO update with encoder

	public static final double ROBOT_WIDTH_INCHES = 28d;
	public static final double ROBOT_DEPTH_INCHES = 31.5d;

	public static final double ROBOT_WIDTH_FEET = 32.0 / 12.0;
	public static final double ROBOT_DEPTH_FEET = 28.0 / 12.0;

	//Radius of the robot and cargo
	public static final double robotRadius = 15; // temporary TODO what do we do here
	public static final double cargoRadius = 5; //TODO Measure cargo arm

	// Hatch angles
	public static final double startAngle = 0, bottomAngle = 0, topAngle = 0;

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
	 * 2/5/19 - This enum has been redeclared sacred and shall not be deleted, no matter 
	 * what Evan may say. - Isaac
	 */
	public enum SickoMode {
		SICKO, BAMBA, SHECK, TRAVIS
	}
}