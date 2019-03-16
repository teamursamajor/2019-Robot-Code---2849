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

import java.io.FileWriter;

import java.io.File;

import edu.wpi.first.cameraserver.CameraServer;
import frc.diagnostics.*;
import frc.diagnostics.Logger.LogLevel;
import frc.tasks.*;
import edu.wpi.first.networktables.*;
import frc.robot.UrsaRobot;

import edu.wpi.first.wpilibj.Servo;

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

  private Drive drive;
  private Turntable turntable;
  private Hatch hatch;
  private Climb climb;
  private Cargo cargo;

  // private FileWriter writer;

  private Constants constants;

  // TODO integrate AutoSelector
  // private AutoSelector autoSelect;
  private AutoCompiler autoCompiler;

  private DebugSelector debugSelect;
  private String robotMode;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    Logger.setLevel(LogLevel.DEBUG);
    Logger.log("********ROBOT PROGRAM STARTING********", LogLevel.INFO);

    currentTime = System.currentTimeMillis();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // try {
    // writer = new FileWriter(new File("C:/Users/Ursa Major/Desktop/Kill Me.txt"));
    // } catch (Exception e) {
    // System.out.println("COULD NOT CREATE FILE WRITER");
    // }

    // distanceSensor.setEnabled(true);

    drive = new Drive();
    drive.initialize("driveThread");
    cargo = new Cargo();
    cargo.initialize("cargoThread");

    if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_HATCH)) {
      turntable = new Turntable();
      turntable.initialize("turntableThread");

      hatch = new Hatch();
      hatch.hatchInit();
      // auto align
    } else if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_CLIMB)) {
      climb = new Climb();
      // manual climb controls
    } else if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_HATCH_CLIMB)) {
      turntable = new Turntable();
      turntable.initialize("turntableThread");

      hatch = new Hatch();
      hatch.hatchInit();

      // auto align

      climb = new Climb();
    } else {
      climb = new Climb();
    }

    constants = new Constants();
    constants.startConstants();

    autoCompiler = new AutoCompiler(drive, cargo);
    // autoCompiler = new AutoCompiler(drive, cargo, hatch, turntable);
    // autoSelect = new AutoSelector();

    debugSelect = new DebugSelector();
    Logger.setLevel(debugSelect.getLevel());

    // Vision vision = new Vision();

    // On HP laptop, this works on SmartDashboard but NOT DriverStation Dashboard
    // if(ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_CLIMB))
    CameraServer.getInstance().startAutomaticCapture(); // uncomment if vision constructor code doesnt work

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
    // boolean b = true;
    // if (b){
    //   System.load("C:/Users/Ursa Major/git/2019-Robot-Code---2849/src/main/java/frc/minimap/Gui.java");
    //   b=false
    // }
    // System.out.println(distanceSensor.getRangeInches());
    // colorSensor.readColors();
    // if ((System.currentTimeMillis() >= currentTime + 500)) {
    // System.out.println(
    // "Red: " + colorSensor.getRed() + " Green: " + colorSensor.getGreen() + "
    // Blue: " + colorSensor.getBlue());
    // currentTime = System.currentTimeMillis();
    // NetworkTable leftEncoder
    // }

    // str += time elapsed
    // String str = drive.getHeading() + "\n";
    // str += drive.getLeftEncoder() + "\n";
    // str += drive.getRightEncoder() + "";
    // try {
    // writer.write(str);
    // } catch (Exception e) {
    // System.out.println("COULD NOT WRITE TO FILE");
    // }

  }

  /*
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
    Logger.log("Started Autonomous mode", LogLevel.INFO);
    Cargo.cargoStartVoltage = Cargo.cargoPot.get();
    // Hatch.hatchServo.setAngle(Hatch.hatchServo.getAngle() - );
    // robotMode = "Autonomous";
    // speed = 0.45;
    // m_autoSelected = m_chooser.getSelected();
    // // autoSelected = SmartDashboard.getString("Auto Selector",
    // // defaultAuto);
    // Task task = autoCompiler.buildAutoMode(m_autoSelected);
    // task.start();
    // System.out.println(task.toString());
    // System.out.println("Auto selected: " + m_autoSelected);
    // Logger.log("Current Auto Mode: " + m_autoSelected, LogLevel.INFO);
    // Logger.setLevel(debugSelect.getLevel());
  }

  private double speed = 0.45;

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if (xbox.getAxisGreaterThan(controls.map.get("cargo_up"), 0.1)
        || xbox.getAxisGreaterThan(controls.map.get("cargo_down"), 0.1)) {
      Cargo.automating = false;
    } else if (xbox.getSingleButtonPress(controls.map.get("cargo_rocket"))
        || xbox.getSingleButtonPress(controls.map.get("cargo_bay"))) {
      Cargo.automating = true;
    }

    if (xbox.getSingleButtonPress(controls.map.get("reset_head"))) {
      Drive.cargoIsFront = !Drive.cargoIsFront;
    }
    // drive.setPower(speed);
    // if (colorSensor.getRed() >= 200) {
    // speed = 0.0;
    // System.out.println(
    // "Red: " + colorSensor.getRed() + " Green: " + colorSensor.getGreen() + "
    // Blue: " + colorSensor.getBlue());
    // }

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
    // Cargo.cargoStartVoltage = Cargo.cargoPot.get();
    Logger.log("Started Teleop mode", LogLevel.INFO);
    robotMode = "Teleop";
    Logger.setLevel(debugSelect.getLevel());
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if (xbox.getSingleButtonPress(controls.map.get("reset_head"))) {
      Drive.cargoIsFront = !Drive.cargoIsFront;
    }
    // System.out.println("Cargo Voltage: " + Cargo.cargoPot.get());

    // determines if cargo is being moved manually or automatically
    if (xbox.getAxisGreaterThan(controls.map.get("cargo_up"), 0.1)
        || xbox.getAxisGreaterThan(controls.map.get("cargo_down"), 0.1)) {
      Cargo.automating = false;
    } else if (xbox.getSingleButtonPress(controls.map.get("cargo_rocket"))
        || xbox.getSingleButtonPress(controls.map.get("cargo_bay"))) {
      Cargo.automating = true;
    }

    // TODO clean this whole thing up once climb/hatch is more finalized
    if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_HATCH)) {
      // // run and cancel auto align
      // if (xbox.getButton(controls.map.get("auto_align"))) {
      //   System.out.println("Auto align!");
      //   // Vision.autoAlign();
      // } else if (xbox.getButton(controls.map.get("cancel_auto_align"))) {
      //   System.out.println("Canceling auto align :(");
      //   Vision.visionStop = true;
      // }

    } else if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_CLIMB)) {

      // run and kill auto climb
      if (xbox.getButton(controls.map.get("climb_start"))) {
        // climb.climbInit();
      } else if (xbox.getButton(controls.map.get("climb_stop"))) {
        // climb.cancelClimb();
      }

      boolean climbPressed = false;
      // custom climb controls
      // if (xbox.getPOV() == controls.map.get("climb_arm_up") && !climb.isClimbing())
      // { // front arm up
      // climb.setFrontMotor(Constants.climbPower);
      // climbPressed = true;
      // System.out.println(climbEncoder.getDistance());
      // } else if (xbox.getPOV() == controls.map.get("climb_arm_down") &&
      // !climb.isClimbing()) { // front arm down
      // climb.setFrontMotor(-Constants.climbPower);
      // climbPressed = true;
      // System.out.println(climbEncoder.getDistance());
      // } else if (xbox.getPOV() == controls.map.get("cam_up") &&
      // !climb.isClimbing()) { // TODO double check CAM up
      // climb.setBackMotor(-Constants.climbPower);
      // climbPressed = true;
      // System.out.println(Climb.climbPot.get());
      // } else if (xbox.getPOV() == controls.map.get("cam_down") &&
      // !climb.isClimbing()) { // TODO double check CAM down
      // climb.setBackMotor(Constants.climbPower);
      // climbPressed = true;
      // System.out.println(Climb.climbPot.get());
      // } else if (!climbPressed && !climb.isClimbing()) {
      // climb.stopMotors();
      // }

    } else if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_HATCH_CLIMB)) {
      // NO MANUAL CLIMB
      // run and cancel auto align
      if (xbox.getPOV() == controls.map.get("auto_align")) {
        Vision.autoAlign();
      } else if (xbox.getPOV() == controls.map.get("cancel_auto_align")) {
        Vision.visionStop = true;
      }

      // run and kill auto climb
      // if (xbox.getButton(controls.map.get("climb_start"))) {
      // climb.climbInit();
      // } else if (xbox.getButton(controls.map.get("climb_stop"))) {
      // climb.cancelClimb();
      // }

    } else {
      // defaults to allowing user to cancel auto align and climb stop with BACK
      if (xbox.getButton(XboxController.BUTTON_BACK)) {
        Vision.visionStop = true;
        climb.cancelClimb();
      }
    }

  }

  private double currentTime;

  /**
   * This function is run when test mode is first started up and should be used
   * for any test initialization code.
   */
  @Override
  public void testInit() {
    Logger.log("Started Test mode", LogLevel.INFO);
    robotMode = "Test";
  }

  // private Servo vexServo = new Servo(7);

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    // System.out.println("Voltage: " + ScrewClimb.distanceSensor.getVoltage() + "
    // Value: " + ScrewClimb.distanceSensor.getValue());

    // if (xbox.getButton(XboxController.BUTTON_A)) {
    // vexServo.setAngle(160);
    // } else if (xbox.getButton(XboxController.BUTTON_B)) {
    // vexServo.setAngle(-160);
    // } else if (xbox.getButton(XboxController.BUTTON_X)) {
    // vexServo.set(0);
    // } else {
    // vexServo.set(0);
    // }
    // System.out.println(vexServo.getAngle());

    // if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
    // vexServo.setAngle(vexServo.getAngle() + 10);
    // } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) {
    // vexServo.setAngle(vexServo.getAngle() - 10);
    // } else if (xbox.getSingleButtonPress(XboxController.BUTTON_X)) {
    // System.out.println("button x");
    // vexServo.setAngle(0.0);
    // } else if (xbox.getSingleButtonPress(XboxController.BUTTON_Y)) {
    // vexServo.setAngle(180.0);
    // }
    // System.out.println(vexServo.getAngle());

    // colorSensor.readColors();
    // if ((System.currentTimeMillis() - startTime) % 50 == 0) {
    // System.out.println(
    // "Red: " + colorSensor.getRed() + " Green: " + colorSensor.getGreen() + "
    // Blue: " + colorSensor.getBlue());
    // }
  }

  /**
   * This function is run when a mode is initially disabled and should be used for
   * any disabling code.
   */
  @Override
  public void disabledInit() {
    Logger.log("Disabled " + robotMode + " mode", LogLevel.INFO);
    Logger.closeWriters();
  }

  /**
   * This function is called periodically when a mode is disabled.
   */
  @Override
  public void disabledPeriodic() {

  }

  private void writeValues() {

  }

}