package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public interface UrsaRobot {
	// Ports for every Spark or Encoder channel
	public static final int DRIVE_FRONT_LEFT = 1;
	public static final int DRIVE_FRONT_RIGHT = 2;
	public static final int DRIVE_REAR_LEFT = 0;
	public static final int DRIVE_REAR_RIGHT = 3;

	public static final int CARGO_FRONT = 5;

	public static final int HATCH = 6;
	public static final int CARGO = 5;
	public static final int CLIMB = 7;
	public static final int LAZY_SUSAN = 4;

	public static final int CONTROLLER_PORT = 0;

	public static final int LEFT_ENCODER_CHANNEL_A = 6;
	public static final int LEFT_ENCODER_CHANNEL_B = 7;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;
	

	public static final int CARGO_ENCODER_CHANNEL_A = 8;
	public static final int CARGO_ENCODER_CHANNEL_B = 9;
	public static final int HATCH_ENCODER_CHANNEL_A = 0;
	public static final int HATCH_ENCODER_CHANNEL_B = 1;

	public static final int SUSAN_SWITCH_CHANNEL = 4;

	// 7 pulses per revolution
	public static Encoder hatchEncoder = new Encoder(HATCH_ENCODER_CHANNEL_A, HATCH_ENCODER_CHANNEL_B);

	public static Encoder cargoEncoder = new Encoder(CARGO_ENCODER_CHANNEL_A, CARGO_ENCODER_CHANNEL_B);

	public static Encoder leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	public static Encoder rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

	// Tells the encoder the value of each tick. Must be set in the corresponding
	// file with encoder.setDistancePerPulse([Name]_PER_TICK);
	public static final double INCHES_PER_TICK = 0.011505d;
	public static final double CARGO_DEGREES_PER_TICK = 0.0; //TODO update with encoder
	public static final double DEGREES_PER_TICK = 0.72434608d;

	public static final double ROBOT_WIDTH_INCHES = 28d;
	public static final double ROBOT_DEPTH_INCHES = 31.5d;

	public static final double ROBOT_WIDTH_FEET = 32.0 / 12.0;
	public static final double ROBOT_DEPTH_FEET = 28.0 / 12.0;

	// Path settings
	public static final double MAX_VELOCITY = 160; // inches / second
	public static final double MAX_ACCELERATION = 80; // inches / second ^2

	// Nav-X
	AHRS ahrs = new AHRS(SPI.Port.kMXP);

	// Limelight
	NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

	//Xbox Controller
	XboxController xbox = new XboxController(0);

	// Subsystem Mode Enums
	
	/**
	 * As of 1/25/2019 at 4:32 PM this enum has been declared sacred and will not be
	 * deleted. Ever. -Evan
	 * 
	 * If you're gonna make a meme method you gotta double down and demand that it exist. No ambivalence! -20XX
	 * 
	 * Alright. -Evan
	 * 
	 * At least give it the right naming convention - Chris
	 */
	public enum SickoMode {
		SICKO, BAMBA, TRAVIS, SHECK
	}
}