package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;

import edu.wpi.first.wpilibj.Spark;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    public static Spark turntableMotor;

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        subsystemMode = TurntableMode.MANUAL;
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

        TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();

        turntableMotor.set(turntableOrder.power);
    }
}