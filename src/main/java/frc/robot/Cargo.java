package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.AnalogInput;
import frc.tasks.*;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoIntake;
    private Spark cargo;
    private AnalogInput cargoPot;

    public Cargo() {
        cargoIntake = new Spark(CARGO_FRONT);
        cargo = new Spark(CARGO);
        cargoPot = new AnalogInput(1);
    }

    public void runSubsystem() {
        // TODO Fix
        // updateStateInfo();
        // CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();
        // cargoIntake.set(cargoOrder.cargoPower);

        // TODO Test Code
        // if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
        // cargoIntake.set(-Constants.cargoOuttakePower);
        // } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
        // cargoIntake.set(Constants.cargoIntakePower);
        // } else {
        // cargoIntake.set(0);
        // }

        if (xbox.getButton(XboxController.BUTTON_BACK)) {
            cargo.set(-Constants.cargoPower);
        } else if (xbox.getButton(XboxController.BUTTON_START)) {
            cargo.set(Constants.cargoPower);
        } else {
            cargo.set(0.0);
        }
    }

    public void updateStateInfo() {
        // Calculate velocity
        // For underclassmen, delta means "change in"
        double deltaPos = cargoPot.getAverageVoltage() - CargoTask.CargoState.position; // TODO rename position
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;
        double velocity = (deltaPos / deltaTime);

        if (deltaPos == 0)
            return;

        CargoTask.CargoState.updateState(velocity, cargoPot.getAverageVoltage());
    }

}