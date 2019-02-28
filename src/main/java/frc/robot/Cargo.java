package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoIntake;
    private Spark cargo;

    public Cargo() {
        cargoIntake = new Spark(CARGO_FRONT);
        cargo = new Spark(CARGO);
        cargoEncoder.setDistancePerPulse(CARGO_DEGREES_PER_TICK);
        cargoEncoder.reset();
    }

    public void runSubsystem() {
        //TODO Fix
        // updateStateInfo();
        // CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();
        // cargoIntake.set(cargoOrder.cargoPower);

        //TODO Test Code
        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            cargoIntake.set(-0.75);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
            cargoIntake.set(0.5);
        } else {
            cargoIntake.set(0);
        }

        if (xbox.getButton(XboxController.BUTTON_BACK)) {
            cargo.set(-0.35);
        } else if (xbox.getButton(XboxController.BUTTON_START)) {
            cargo.set(0.35);
        } else {
            cargo.set(-0.2);
        }
    }

    public double getCargoDistance() {
        return cargoEncoder.getDistance();
    }
    

    public void updateStateInfo() {
        // TODO remove?
		
        
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