/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.diagnostics.*;
import frc.diagnostics.Logger.LogLevel;
import frc.robot.UrsaRobot;
// TODO uncomment for auto selector/compiler
// import frc.tasks.*;
import frc.tasks.DriveTask.DriveMode;

// CommandRobot?
public class Robot extends TimedRobot implements UrsaRobot {

  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Drive drive;
  private Hatch hatch;
  private ScrewClimb climb;
  private Arm arm;
  private Cargo cargo;

  // private DashboardInfo dashboardInfo;

  // private AutoSelector autoSelect;
  // private AutoCompiler autoCompiler;

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

    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

    // distanceSensor.setEnabled(true);

    drive = new Drive();
    drive.initialize("driveThread");

    cargo = new Cargo();
    cargo.initialize("cargoThread");

    arm = new Arm();
    arm.initialize("armThread");

    hatch = new Hatch(arm);
    hatch.initialize("hatchThread");

    // climb = new ScrewClimb();
    // climb.initialize();

    // dashboardInfo = new DashboardInfo();
    // dashboardInfo.startDashboardInfo();

    // ultra.setEnabled(true);
    // ultra.setAutomaticMode(true);

    debugSelect = new DebugSelector();
    Logger.setLevel(debugSelect.getLevel());

    // autoCompiler = new AutoCompiler(drive, arm, cargo, hatch);

    UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture();
    camera0.setFPS(30);
    camera0.setResolution(225, 225);
  }

  private boolean processedPipeline = true;

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
    // sets manual or automatic arm control
    if (xbox.getAxisGreaterThan(controls.map.get("arm_up"), 0.1)
        || xbox.getAxisGreaterThan(controls.map.get("arm_down"), 0.1)) {
      Arm.automating = false;
      Arm.defense = false;
    } else if (xbox.getSingleButtonPress(controls.map.get("arm_rocket"))
        || xbox.getSingleButtonPress(controls.map.get("arm_bay"))) {
      Arm.automating = true;
    }

    // runs and cancels auto align
    if (xbox.getPOV() == controls.map.get("auto_align")) {
      AutoAlign.autoAlign();
    } else if (xbox.getPOV() == controls.map.get("cancel_auto_align")) {
      AutoAlign.killAutoAlign();
    }

    // toggles limelight between processsed and raw image
    if (xbox.getPOV() == (controls.map.get("limelight_toggle"))) {
      if (processedPipeline)
        limelightTable.getEntry("pipeline").setDouble(0);
      else
        limelightTable.getEntry("pipeline").setDouble(2);
      processedPipeline = !processedPipeline;
    }

    // toggle defense mode
    if(xbox.getSingleButtonPress(XboxController.BUTTON_START)){
      Arm.defense = !Arm.defense;
    }

    // toggle basic arm mode
    if(xbox.getSingleButtonPress(XboxController.BUTTON_BACK)){
      Arm.basic = !Arm.basic;
    }
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
    Arm.armStartVoltage = Arm.armPot.get();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is run when teleop mode is first started up and should be used
   * for any teleop initialization code.
   */
  @Override
  public void teleopInit() {
    Logger.log("Started Teleop mode", LogLevel.INFO);
    robotMode = "Teleop";
    drive.setMode(DriveMode.DRIVE_STICKS);
    Logger.setLevel(debugSelect.getLevel());
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

  }

  /**
   * This function is run when test mode is first started up and should be used
   * for any test initialization code.
   */
  @Override
  public void testInit() {
    Logger.log("Started Test mode", LogLevel.INFO);
    robotMode = "Test";
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    // System.out.println("Distance: " + ultra.getRangeInches());
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
}