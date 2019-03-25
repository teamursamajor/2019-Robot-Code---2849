package frc.robot;

import frc.tasks.*;
import frc.tasks.AutoAlignTask.TurntableMode;

import edu.wpi.first.wpilibj.Spark;

public class AutoAlign extends Subsystem<AutoAlignTask.TurntableMode> implements UrsaRobot {

    public static Spark turntableMotor;

    public AutoAlign() {
        turntableMotor = new Spark(TURNTABLE);
    }

    public void align(){

    }

    public void runSubsystem() {
        if (Vision.visionRunning) {
            // waits for auto align to end before proceeding
            while (Vision.visionRunning) {
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        AutoAlignTask.TurntableOrder turntableOrder = subsystemMode.callLoop();

        turntableMotor.set(turntableOrder.power);
    }
}