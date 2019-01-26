package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public interface UrsaRobot {
	public static final int DRIVE_FRONT_LEFT = 1;
	public static final int DRIVE_FRONT_RIGHT = 6;
	public static final int DRIVE_REAR_LEFT = 0;
	public static final int DRIVE_REAR_RIGHT = 7;

	public static final int HATCH = 2;
	public static final int CARGO = 5;
	public static final int CLIMB = 3;

	public static final int CONTROLLER_PORT = 0;

	public static final int LEFT_ENCODER_CHANNEL_A = 6;
	public static final int LEFT_ENCODER_CHANNEL_B = 7;
	public static final int RIGHT_ENCODER_CHANNEL_A = 2;
	public static final int RIGHT_ENCODER_CHANNEL_B = 3;

	public static final int HATCH_ENCODER_CHANNEL_A = 0;
	public static final int HATCH_ENCODER_CHANNEL_B = 1;

	// 7 pulses per revolution
	public static Encoder hatchEncoder = new Encoder(HATCH_ENCODER_CHANNEL_A, HATCH_ENCODER_CHANNEL_B);

	public static Encoder leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B);
	public static Encoder rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B);

	public static final double INCHES_PER_TICK = 0.011505d;
	public static final double DEGREES_PER_TICK = 0.72434608d;

	// TODO update these for 2019 robot
	public static final double ROBOT_WIDTH_INCHES = 32d;/* in */// 12;//ft
	public static final double ROBOT_DEPTH_INCHES = 28d;/* in */// 12;//ft

	public static final double ROBOT_WIDTH_FEET = 32.0 / 12.0;
	public static final double ROBOT_DEPTH_FEET = 28.0 / 12.0;

	public static final double MAX_VELOCITY = 160; // inches / second
	public static final double MAX_ACCELERATION = 80; // inches / second ^2

	AHRS ahrs = new AHRS(SPI.Port.kMXP);

	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

	XboxController xbox = new XboxController(0);

	//Subsystem Mode Enums
	public enum HatchMode {
		Intake, Deploy, Default, Carry
	}
	
	public enum DriveMode {
		Auto, DriveSticks
	}

	//TODO Add modes
	public enum ClimbMode {

	}

	//TODO Add modes
	public enum CargoMode {

	}

	/**
	 * As of 1/25/2019 at 4:32 PM this enum has been declared sacred and will not
	 * be deleted. Ever. -Evan
	 * (ok delete it if you want idc)
	 */
	public enum SickoMode {
		
	}

}