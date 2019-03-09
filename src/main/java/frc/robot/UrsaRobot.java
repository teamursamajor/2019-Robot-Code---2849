package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public interface UrsaRobot {
	// Arcade vs Tank drive
	public static boolean isArcadeDrive = true;

	// Spark Ports
	// 5 and 7 unused
	public static final int DRIVE_FRONT_LEFT = 0;
	public static final int DRIVE_BACK_LEFT = 0;
	public static final int DRIVE_FRONT_RIGHT = 9;
	public static final int DRIVE_BACK_RIGHT = 9;

	public static final int CARGO_INTAKE = 1;
	public static final int CARGO_LIFT = 6;
	
	public static final int CLIMB_FRONT = 2;
	public static final int CLIMB_BACK = 8;
	
	public static final int HATCH = 4;
	public static final int TURNTABLE = 3;

	public static final int HATCH_SERVO = 7;

	// Encoders and Sensors Ports
	public static final int CONTROLLER_PORT = 0;

	public static final int LEFT_ENCODER_CHANNEL_A = 4;
	public static final int LEFT_ENCODER_CHANNEL_B = 5;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;

	public static final int CLIMB_ENCODER_CHANNEL_A = 0;
	public static final int CLIMB_ENCODER_CHANNEL_B = 1;

	public static final int CARGO_POT_CHANNEL = 3;
	public static final int CLIMB_POT_CHANNEL = 0;

	public static final int BUMPER_SWITCH_CHANNEL = 6;

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

	// Turntable Voltages
	public static final double forwardVoltage = 0, leftVoltage = 0, rightVoltage = 0;

	// public static final double cargoGroundVoltage = 17.1, cargoBayVoltage = 19.4;
	// public static final double cargoLowRocketVoltage = 18.4, cargoStartVoltage = 21.8;

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

	public static final double defaultCameraPipeline = 0;
	public static final double visionCameraSinglePipeline = 1;
	public static final double visionCameraDoublePipeline = 2;


	/**
	 * 2/5/19 - This enum has been redeclared sacred and shall n♀t be deleted, no m
	 *  tter what Evan may say. - Isåàç
	 */
	public enum SickoMode {
		SICKO, BAMBA, SHECK, TRAVIS
	}
}
