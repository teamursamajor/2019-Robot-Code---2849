/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
// Suggested to use CommandRobot
public class Robot extends TimedRobot implements UrsaRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //private Piston piston;
  private Drive drive;
  private LazySusan lazySusan;
  private Hatch hatch;
  private Climb climb;
  private Cargo cargo;
  
  private Constants constants;

  private boolean climbPressed;

  private double hatchPower;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    drive = new Drive();
    drive.initialize("driveThread");
    lazySusan = new LazySusan();
    lazySusan.initialize("susanThread");
    hatch = new Hatch();
    hatch.initialize("hatchThread");
    climb = new Climb();
    cargo = new Cargo();
    cargo.initialize("cargoThread");

    constants = new Constants();
    constants.startConstants();

    //piston = new Piston();
    //piston.initialize("pistonThread");
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
   * code works with the Java SmartDashboard.
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
   * This function is run when teleop mode is first started up and should be used
   * for any teleop initialization code.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // TODO Should this be here or in a climber thread?
    climbPressed = false;
    double power = .75;
    if (xbox.getPOV() == XboxController.POV_UP) {
      // climb.setFrontMotor(power);
      System.out.println("climb up working");
      climbPressed = true;
    }
    if (xbox.getPOV() == XboxController.POV_DOWN) {
      // climb.setFrontMotor(-power);
      System.out.println("climb down working");
      climbPressed = true;
    }
    if (xbox.getPOV() == XboxController.POV_LEFT) {
      // climb.setBackMotor(power);
      System.out.println("climb left working");
      climbPressed = true;
    }
    if (xbox.getPOV() == XboxController.POV_RIGHT) {
      // climb.setBackMotor(-power);
      System.out.println("climb right working");
      climbPressed = true;
    }
    if (!climbPressed) {
      climb.stopMotors();
    }
    if (xbox.getButton(XboxController.AXIS_LEFTTRIGGER)) {
      System.out.println("Left Trigger Press");
    }
    if (xbox.getButton(XboxController.AXIS_RIGHTTRIGGER)) {
      System.out.println("Right Trigger Pressed");
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    // System.out.println("Hatch Power" + constants.hatchPower);
  }
  
}