package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot{

    private Spark cargoMotor;

    public Cargo() {
        super("cargoThread");
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