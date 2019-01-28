package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Cargo extends Subsystem<UrsaRobot.CargoMode> implements UrsaRobot{

    private Spark cargoMotor;
    private double cargoPower;


    /* Encoder for the cargo? */

    public double getCargoEncoder() {
		return cargoEncoder.getDistance();
	}

    /* Cargo class similar to drive */

    public Cargo() {
    super("cargoThread");
    cargoMotor = new Spark(CARGO_FRONT);

    cargoEncoder.setDistancePerPulse(INCHES_PER_TICK);

    cargoEncoder.reset();
    }

    // subsystem code? 
    public void runSubsystem() {
        // updateStateInfo(); 
    }
}