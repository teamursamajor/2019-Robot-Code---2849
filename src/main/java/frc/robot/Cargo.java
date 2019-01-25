package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Cargo extends Subsystem<UrsaRobot.CargoMode> implements UrsaRobot{

    private Spark cargoMotor;

    public Cargo() {
        super();
        cargoMotor = new Spark(CARGO);
    }

    public void runSubsystem() {
        //TODO Fill this out and add modes
        switch (getMode()) {
            default:
                break;
        }
        //TODO place runnable code
    }
}