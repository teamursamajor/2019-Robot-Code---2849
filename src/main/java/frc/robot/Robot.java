/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import frc.robot.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
<<<<<<< HEAD
// TODO Suggested to use CommandRobot
public class Robot extends TimedRobot {
=======
// Suggested to use CommandRobot
<<<<<<< HEAD
public class Robot extends TimedRobot implements UrsaRobot {
=======
public class Robot extends TimedRobot implements UrsaRobot{
>>>>>>> 8009bbe5fe3c45bb9b034b09c8d9569487537944
>>>>>>> 24443200d99027310f70feec7f128fbe37c8684c
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

<<<<<<< HEAD
  private Spark rearLeftMotor;
  private Spark rearRightMotor;
  private Spark frontLeftMotor;
  private Spark frontRightMotor;

  //TODO remove
  private Spark hatchMotor;

  private XboxController xbox;
=======
>>>>>>> 8009bbe5fe3c45bb9b034b09c8d9569487537944
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;

  private Hatch hatch;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

<<<<<<< HEAD
=======
<<<<<<< HEAD
    rearLeftMotor = new Spark(3);
    rearRightMotor = new Spark(2);
    frontLeftMotor = new Spark(0);
    frontRightMotor = new Spark(1);

    //TODO remove
    hatchMotor = new Spark(4);

    xbox = new XboxController(0);

=======
>>>>>>> 8009bbe5fe3c45bb9b034b09c8d9569487537944
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
>>>>>>> 24443200d99027310f70feec7f128fbe37c8684c
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

<<<<<<< HEAD
=======
    AutoTest autotest = new AutoTest(frontRightMotor, frontLeftMotor, rearRightMotor, rearLeftMotor);
>>>>>>> 24443200d99027310f70feec7f128fbe37c8684c
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;

    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
<<<<<<< HEAD
    // mRearLeft.set(-xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y));
    // mFrontLeft.set(-xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y));
    // mRearRight.set(xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y));
    // mFrontRight.set(xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y));

=======
<<<<<<< HEAD
    // rearLeftMotor.set(-xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y));
    // frontLeftMotor.set(-xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y));
    // rearRightMotor.set(xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y));
    // frontRightMotor.set(xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y));

    if(xbox.getButton(1)){
      hatchMotor.set(0.2);
    } else if(xbox.getButton(2)){
      hatchMotor.set(-0.2);
    } else{
      hatchMotor.set(0);
    }
    
    System.out.println("tx: " + tx.getDouble(0));
    System.out.println("ty: " + ty.getDouble(0));
    System.out.println("ta: " + ta.getDouble(0));
=======
    mRearLeft.set(-xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y));
    mFrontLeft.set(-xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y));
    mRearRight.set(xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y));
    mFrontRight.set(xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_Y));
>>>>>>> 24443200d99027310f70feec7f128fbe37c8684c

    System.out.println("tx: " + tx.getDouble(0));
    System.out.println("ty: " + ty.getDouble(0));
    System.out.println("ta: " + ta.getDouble(0));

<<<<<<< HEAD
    // if (xbox.getRawButtonPressed(2)) {
    //   System.out.println("Switching to Auto");
    //   Drive.setMode(Modes.Auto);
    // }
=======
    if (xbox.getRawButtonPressed(2)) {
      System.out.println("Switching to Auto");
      Drive.setMode(Modes.Auto);
    }
>>>>>>> 8009bbe5fe3c45bb9b034b09c8d9569487537944
>>>>>>> 24443200d99027310f70feec7f128fbe37c8684c
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
