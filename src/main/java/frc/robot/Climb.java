package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Climb extends Subsystem<ClimbTask.ClimbMode> implements UrsaRobot {

    private Spark climbMotor;

    public Climb() {
        climbMotor = new Spark(CLIMB);
    }

    public void runSubsystem() {
        //TODO Fill this out and add modes
        switch (getMode()) {
            default:
                break;
        }
        //TODO Place runnable code
    }

}