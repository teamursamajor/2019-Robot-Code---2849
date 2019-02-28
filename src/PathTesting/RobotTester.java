package com.teamursamajor.auto.Testing;

import com.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;



public interface RobotTester {
	// Spark Ports
	public static final int DRIVE_FRONT_LEFT = 3;
	public static final int DRIVE_FRONT_RIGHT = 1;
	public static final int DRIVE_REAR_LEFT = 2;
    public static final int DRIVE_REAR_RIGHT = 0; 

	public static final int ENCODER_A = 0;
	public static final int ENCODER_B = 1;
	public static Encoder testEncoder = new Encoder(ENCODER_A, ENCODER_B);
	
	

}