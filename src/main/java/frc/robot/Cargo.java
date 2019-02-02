package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoMotor;
    private double cargoPower;

    public Cargo() {
        cargoMotor = new Spark(CARGO_FRONT);
        cargoEncoder.setDistancePerPulse(CARGO_DEGREES_PER_TICK);
        cargoEncoder.reset();
    }

    // subsystem code?
    public void runSubsystem() {
        // updateStateInfo();
    }

    /* Encoder for the cargo? */

    public double getCargoEncoder() {
        return cargoEncoder.getDistance();
    }
}