package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoMotor;

    public Cargo() {
        cargoMotor = new Spark(CARGO_FRONT);
        cargoEncoder.setDistancePerPulse(CARGO_DEGREES_PER_TICK);
        cargoEncoder.reset();
    }

    public void runSubsystem() {
        updateStateInfo();
        CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();
        cargoMotor.set(cargoOrder.cargoPower);
    }

    public double getCargoDistance() {
        return cargoEncoder.getDistance();
    }
    

    public void updateStateInfo() {
        // TODO remove?
		// maybe average the encoder distances with limelight? idk
		// double leftDistance = limelightTable.getEntry("tx").getDouble(Double.NaN);
        // double rightDistance = limelightTable.getEntry("tx").getDouble(Double.NaN);
        
        // Calculate velocity
        // For underclassmen, delta means "change in"
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;
		double deltaPos = getCargoDistance() - CargoTask.CargoState.position;
		double velocity = (deltaPos / deltaTime);

        // TODO remove?
		/*
		 * Our loop updates faster than the limelight. If the limelight hasn't updated
		 * yet, then our change in position is 0. In this case, we want to skip this
		 * iteration and wait for the next cycle
		 */
		if (deltaPos == 0)
            return;
            
        CargoTask.CargoState.updateState(velocity, getCargoDistance());
    }
    
}