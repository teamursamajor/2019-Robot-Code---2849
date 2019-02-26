package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;
import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.Spark;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    private Spark turntableMotor;
    private boolean turntableCamera = false;

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        subsystemMode = TurntableMode.AUTO_ALIGN; // TODO Change back
    }

    public void runSubsystem() {
        TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        turntableMotor.set(turntableOrder.power);

    

        // TODO test code
        // if (xbox.getButton(XboxController.BUTTON_LEFTSTICK)) {
        //     turntableCamera = !turntableCamera;
        // }

        // if (turntableCamera) {
        //     //Auto mode
        //     NetworkTableEntry pipeEntry = UrsaRobot.limelightTable.getEntry("getpipe");
        //     NetworkTableEntry targetEntry = UrsaRobot.limelightTable.getEntry("tv");

        //     if (pipeEntry.getDouble(UrsaRobot.defaultCameraPipeline) != UrsaRobot.visionCameraPipeline) {
        //         UrsaRobot.limelightTable.getEntry("getpipe").setDouble(UrsaRobot.visionCameraPipeline);
        //     }
        //     if (targetEntry.getDouble(0) != 1) {
        //         turntableMotor.set(0.0);
        //     }
        // }
        // // TODO make loop
        // // ALSO check to make sure we won't hit the eboard and see if we need to move left or right

    }

}