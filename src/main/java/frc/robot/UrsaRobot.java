package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public interface UrsaRobot {
	// TODO Finalize Ports
	// Spark Ports
	// 5 and 8 unused, 3 and 4 are temporary
	public static final double defaultCameraPipeline = 0;
	public static final double visionCameraPipeline=1;

	public static final int DRIVE_LEFT = 0;
	public static final int DRIVE_RIGHT = 11;

	public static final int CARGO_INTAKE = 2;
	public static final int CARGO_LIFT = 6;

	// TODO originally 3
	public static final int HATCH = 5;

	public static final int CLIMB_FRONT = 1;
	public static final int CLIMB_BACK = 9;

	// TODO originally 4
	public static final int TURNTABLE = 3;

	// Encoders and Sensors Ports
	public static final int CONTROLLER_PORT = 0;

	public static final int LEFT_ENCODER_CHANNEL_A = 4;
	public static final int LEFT_ENCODER_CHANNEL_B = 5;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;

	public static final int CLIMB_ENCODER_CHANNEL_A = 0;
	public static final int CLIMB_ENCODER_CHANNEL_B = 1;

	public static final int CARGO_POT_CHANNEL = 3;

	// Hatch Servo TODO originally 1
	public static final int HATCH_SERVO = 8;

	// Encoders
	public static Encoder leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	public static Encoder rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

	public static Encoder climbEncoder = new Encoder(CLIMB_ENCODER_CHANNEL_A, CLIMB_ENCODER_CHANNEL_B);

	// Tells encoder the value of each tick. Must be set in the corresponding file
	public static final double INCHES_PER_TICK = 0.011505d;

	public static final double ROBOT_WIDTH_INCHES = 28d;
	public static final double ROBOT_DEPTH_INCHES = 31.5d;

	public static final double ROBOT_WIDTH_FEET = ROBOT_WIDTH_INCHES / 12.0;
	public static final double ROBOT_DEPTH_FEET = ROBOT_WIDTH_FEET / 12.0;

	// Radius of the robot and cargo
	// TODO not sure what to do with this radius (ie how to measure it). It is used for the turn control loop
	public static final double robotRadius = 15;

	// Cargo Voltages
	public static final double cargoGroundVoltage = 140, cargoBayVoltage = 255;
	public static final double cargoLowRocketVoltage = 225, cargoStartVoltage = 270;

	// Turntable Voltages
	public static final double forwardVoltage = 0, leftVoltage = 0, rightVoltage = 0;

	// Path settings
	public static final double MAX_VELOCITY = 160; // inches / second
	public static final double MAX_ACCELERATION = 80; // inches / second ^2

	// Nav-X
	AHRS ahrs = new AHRS(SPI.Port.kMXP);

	// Limelight
	NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

	// Xbox Controller
	XboxController xbox = new XboxController(0);

	// Control Map
	ControlMap controls = new ControlMap();

	/**
	 * 2/5/19 - This enum has been redeclared sacred and shall n♀t be deleted, no m
	 *  tter what Evan may say. - Isåàç
	 */
	public enum SickoMode {
		SICKO, BAMBA, SHECK, TRAVIS
	}
}