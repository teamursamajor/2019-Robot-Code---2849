package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoState;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoIntake;
    private Spark cargo;
    private Potentiometer cargoPot;
    private long time;

    public Cargo() {
        cargo = new Spark(CARGO_LIFT);
        cargoPot = new AnalogPotentiometer(0, 360, 0);
        cargoIntake = new Spark(CARGO);
        time = System.currentTimeMillis();
    }

    public void runSubsystem() {
        // TODO Fix
        updateStateInfo();
        // CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();

        // TODO Test Code
        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
        cargoIntake.set(-Constants.cargoOuttakePower);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
        cargoIntake.set(Constants.cargoIntakePower);
        } else {
        cargoIntake.set(0);
        }

        if (xbox.getButton(XboxController.BUTTON_BACK)) {
            cargo.set(-Constants.cargoPower);
        } else if (xbox.getButton(XboxController.BUTTON_START)) {
            cargo.set(Constants.cargoPower);
        } else {
            cargo.set(CargoState.cargoPower);
        }
        if((System.currentTimeMillis() - time) % 50 == 0)
            System.out.println("Pot Voltage: " + cargoPot.get());
        
    }

    public void updateStateInfo() {
        double currentVoltage = cargoPot.get();
        // Calculate velocity
        // For underclassmen, delta means "change in"
        double deltaPos = currentVoltage - CargoTask.CargoState.cargoVoltage; // TODO rename position
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;
        double velocity = (deltaPos / deltaTime);
        double power = cargo.get();
        if (deltaPos == 0)
            return;

       CargoTask.CargoState.updateState(power, velocity, currentVoltage);
    }

}