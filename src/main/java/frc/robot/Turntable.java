package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;

import edu.wpi.first.wpilibj.Spark;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    private Spark turntableMotor;
    private boolean targetFound;
    private int defaultPipeline = 1;

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        subsystemMode = TurntableMode.CUSTOM;
        targetFound = false;
    }

    public void runSubsystem() {
        TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        turntableMotor.set(turntableOrder.power);

        // TODO test code
        // else if (xbox.getButton(XboxController.BUTTON_LEFTSTICK)){
        //     //Auto mode
        //     NetworkTableEntry entry = UrsaRobot.limelightTable.getEntry("pipeline");
        
        //     if (entry.getDouble(UrsaRobot.defaultCameraPipeline) != UrsaRobot.visionCameraPipeline);
        //     //check auto pipe line in the lime light, enable if need be
        // //see if there are targets in view, if not set motors to zero
        //     //ALSO check to make sure we won't hit the eboard
        //     //
        //     //see if we need to move left or right
        //     //
        // }
        // else {
        //     turntableMotor.set(0.0);
        // }
    }

}