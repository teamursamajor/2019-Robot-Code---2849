package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public interface UrsaRobot {

	//TODO update for 2019 season
	public static final int DRIVE_FRONT_LEFT = 1;
	public static final int DRIVE_FRONT_RIGHT = 6;
	public static final int DRIVE_REAR_LEFT = 0;
	public static final int DRIVE_REAR_RIGHT = 7;

	public static final int HATCH = 4;
	
	public static final int CONTROLLER_PORT = 0;
	
	public static final int LEFT_ENCODER_CHANNEL_A = 6;
	public static final int LEFT_ENCODER_CHANNEL_B = 7;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;
	
	public static final int HATCH_ENCODER_CHANNEL_A = 0;
	public static final int HATCH_ENCODER_CHANNEL_B = 1;
	
	public static final int LIFT_LIMIT_SWITCH = 5;

	//7 pulses per revolution
	public static Encoder hatchEncoder = new Encoder(HATCH_ENCODER_CHANNEL_A, HATCH_ENCODER_CHANNEL_B);

	public static final double ROBOT_WIDTH_INCHES  = 32d;/*in*///12;//ft
	public static final double ROBOT_DEPTH_INCHES = 28d;/*in*///12;//ft
	
	public static final double ROBOT_WIDTH_FEET = 32.0 / 12.0;
	public static final double ROBOT_DEPTH_FEET = 28.0 / 12.0;
	
	//Used to be 160
	public static final double MAX_VELOCITY = 160; // inches / second
//	public static final double MAX_ACCELERATION = 160 * 1; // inches / second^2
	public static final double MAX_ACCELERATION = 80;

	//TODO move these to drive
	Spark mFrontLeft = new Spark(DRIVE_FRONT_LEFT);
	Spark mFrontRight = new Spark(DRIVE_FRONT_RIGHT);
	Spark mRearLeft = new Spark(DRIVE_REAR_LEFT);
	Spark mRearRight = new Spark(DRIVE_REAR_RIGHT);

	//ahrs = new AHRS(SPI.Port.kMXP);
	//encL = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	//encR = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

	XboxController xbox = new XboxController(0);

}

