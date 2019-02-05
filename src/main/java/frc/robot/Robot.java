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
import frc.tasks.DriveTask.DriveMode;
import edu.wpi.first.wpilibj.Spark;

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

  private Piston piston;
  private Drive drive;
  private LazySusan lazySusan;
  private Hatch hatch;
  private Climb climb;

  private boolean climbPressed;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    piston = new Piston();

    climb = new Climb();
    drive = new Drive();
    drive.initialize("driveThread");
    lazySusan = new LazySusan();
    lazySusan.initialize("susanThread");
    hatch = new Hatch();
    hatch.initialize("hatchThread");
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
    climbPressed = false;
    double power = 0.75;
    if (xbox.getButton(XboxController.POV_UP)) {
      climb.setFrontMotor(power);
      climbPressed = true;
    }
    if (xbox.getButton(XboxController.POV_DOWN)) {
      climb.setFrontMotor(-power);
      climbPressed = true;
    }
    if (xbox.getButton(XboxController.POV_LEFT)) {
      climb.setBackMotor(power);
      climbPressed = true;
    }
    if (xbox.getButton(XboxController.POV_RIGHT)) {
      climb.setBackMotor(-power);
      climbPressed = true;
    }
    if (!climbPressed) {
      climb.stopMotors();
    }

    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}