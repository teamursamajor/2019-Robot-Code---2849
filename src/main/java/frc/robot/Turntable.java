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
        subsystemMode = TurntableMode.CUSTOM; // TODO Change back
    }

    public void runSubsystem() {
        
        // TODO test code
        // if (xbox.getPOV() == XboxController.POV_RIGHT) {
        //     turntableCamera = true;
        // } else if (xbox.getPOV() == XboxController.POV_LEFT) {
        //     turntableCamera = false;
        // }
        
        // if (turntableCamera) {
        //     subsystemMode = TurntableMode.AUTO_ALIGN;
        //     limelightTable.getEntry("pipeline").setDouble(0);
        // } else {
        //     subsystemMode = TurntableMode.CUSTOM;
        //     limelightTable.getEntry("pipeline").setDouble(2);
        // }

        TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        turntableMotor.set(turntableOrder.power);

        // // TODO make loop
        // // ALSO check to make sure we won't hit the eboard and see if we need to move
        // left or right

    }

}