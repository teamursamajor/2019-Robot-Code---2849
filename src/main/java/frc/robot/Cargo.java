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
        //TODO calculate Cargo velocity
        CargoTask.CargoState.updateState(getCargoDistance());
    }
}