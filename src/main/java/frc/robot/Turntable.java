package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;
import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.Spark;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    public static Spark turntableMotor;
    private boolean turntableCamera = false;

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        subsystemMode = TurntableMode.TRIGGERS;
    }

    public void runSubsystem() {
        // if(autoAlignButton){
        //      subsystemMode = TurntableMode.AUTO_ALIGN;
        // }

        if(subsystemMode.equals(TurntableMode.AUTO_ALIGN)){
            Vision.autoAlign();

            // waits for auto align to end before proceeding
            while(Vision.visionRunning){
                try{
                    Thread.sleep(20);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            subsystemMode = TurntableMode.TRIGGERS;
        }

        TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        // System.out.println(turntableOrder.power);
        turntableMotor.set(turntableOrder.power);

        // if (xbox.getAxisGreaterThan(XboxController.AXIS_LEFTTRIGGER, 0.1)) {
        // turntableMotor.set(0.3);
        // } else if (xbox.getAxisGreaterThan(XboxController.AXIS_RIGHTTRIGGER, 0.1)) {
        // turntableMotor.set(-0.3);
        // } else {
        // turntableMotor.set(0.0);
        // }

        // TODO test code
        if (xbox.getPOV() == XboxController.POV_RIGHT) {
            turntableCamera = true;
        } else if (xbox.getPOV() == XboxController.POV_LEFT) {
            turntableCamera = false;
        }

        if (turntableCamera) {
            // subsystemMode = TurntableMode.AUTO_ALIGN;
            limelightTable.getEntry("pipeline").setDouble(0);
        } else {
            // subsystemMode = TurntableMode.CUSTOM;
            limelightTable.getEntry("pipeline").setDouble(2);
        }

    }

}