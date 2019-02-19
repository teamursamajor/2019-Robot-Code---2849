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
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.cameraserver.CameraServer;

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

  // private Piston piston;
  private Drive drive;
  private LazySusan lazySusan;
  private Hatch hatch;
  private Climb climb;
  private Cargo cargo;

  private Constants constants;
  private ColorSensor colorSensor;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
    currentTime = System.currentTimeMillis();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    drive = new Drive();
    drive.initialize("driveThread");
    lazySusan = new LazySusan();
    lazySusan.initialize("susanThread");
    hatch = new Hatch();
    hatch.hatchInit();
    climb = new Climb();
    cargo = new Cargo();
    cargo.initialize("cargoThread");

    constants = new Constants();
    constants.startConstants();

    colorSensor = new ColorSensor(new I2C(I2C.Port.kOnboard, 0x39));

    // Vision.visionInit();

    // piston = new Piston();
    // piston.initialize("pistonThread");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    colorSensor.readColors();
    if ((System.currentTimeMillis() >= currentTime + 500)) {
      // System.out.println(
      // "Red: " + colorSensor.getRed() + " Green: " + colorSensor.getGreen() + "
      // Blue: " + colorSensor.getBlue());
      currentTime = System.currentTimeMillis();

    }

    // try {
    // Thread.sleep(500);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    // System.out.println(colorSensor.getRed() + "," + colorSensor.getGreen() + ","
    // + colorSensor.getBlue() + " FROM COLOR SENSOR");
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard.
   *
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    speed = 0.45;
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  private boolean hitTape = false;
  private double speed = 0.45;

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    drive.setPower(speed);
    if (colorSensor.getRed() >= 200) {
      speed = 0.0;
      // System.out.println(
      // "Red: " + colorSensor.getRed() + " Green: " + colorSensor.getGreen() + "
      // Blue: " + colorSensor.getBlue());
    }

    // switch (m_autoSelected) {
    // case kCustomAuto:
    // // Put custom auto cosde here
    // break;

    // case kDefaultAuto:
    // default:
    // // Put default auto code here
    // break;
    // }
  }

  /**
   * This function is run when teleop mode is first started up and should be used
   * for any teleop initialization code.
   */
  @Override
  public void teleopInit() {
  }

  private boolean climbPressed;

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if(xbox.getAxisGreaterThan(XboxController.AXIS_LEFTTRIGGER, 0.1) || xbox.getAxisGreaterThan(XboxController.AXIS_RIGHTTRIGGER, 0.1)){
      Cargo.automating = false;
    } else if(xbox.getSingleButtonPress(XboxController.BUTTON_X) || xbox.getSingleAxisPress(XboxController.BUTTON_Y)){
      Cargo.automating = true;
    }
    
    // climbPressed = false;
    // boolean climbPressed = false;
    // // POV Up is start all climbing
    // if (xbox.getPOV() == XboxController.POV_UP && !climb.isClimbing()) {
    //   climb.climbInit();
    // }
    // // POV Down is cancel climb
    // else if (xbox.getPOV() == XboxController.POV_DOWN) {
    //   climb.cancelClimb();
    // }
    // // POV Left is to retract the front motor
    // if (xbox.getPOV() == XboxController.POV_LEFT && !climb.isClimbing()) {
    //   climb.setFrontMotor(Constants.climbPower);
    //   climbPressed = true;
    // }
    // // POV Right is to retract the back motor / cam
    // if (xbox.getPOV() == XboxController.POV_RIGHT && !climb.isClimbing()) {
    //   climb.setBackMotor(-Constants.climbPower);
    //   climbPressed = true;
    // }
    // if (!climbPressed && !climb.isClimbing()) {
    //   climb.stopMotors();
    // }

  }

  private double currentTime;

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    colorSensor.readColors();
    // if ((System.currentTimeMillis() - startTime) % 50 == 0) {
    // System.out.println(
    // "Red: " + colorSensor.getRed() + " Green: " + colorSensor.getGreen() + "
    // Blue: " + colorSensor.getBlue());
    // }
  }

  @Override
  public void disabledPeriodic() {
    if (cargo.getCargoVoltage() > 135) {
      cargo.setCargoLift(-0.25);
    }
    else {
      cargo.setCargoLift(0.0);
    }
  }

}