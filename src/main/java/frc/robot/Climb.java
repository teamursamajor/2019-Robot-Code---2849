package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb extends Subsystem<UrsaRobot.ClimbMode> implements UrsaRobot {

    private Spark climbMotor;

    public Climb() {
        super("climbThread");
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