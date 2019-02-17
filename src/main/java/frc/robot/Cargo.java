package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoState;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoIntake;
    private Spark cargoLift;
    private Potentiometer cargoPot;
    private long time;

    public Cargo() {
        cargoLift = new Spark(CARGO_LIFT);
        cargoPot = new AnalogPotentiometer(0, 360, 0);
        cargoIntake = new Spark(CARGO_INTAKE);
        time = System.currentTimeMillis();
    }

    public void runSubsystem() {
        // TODO Fix
        // updateStateInfo();
        // CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();

        //TODO when it goes too high we dont have enough power to move it down

        if(cargoPot.get() <= 130){
            cargoLift.set(-0.15);
        } else if(cargoPot.get() >= 230){
            cargoLift.set(-0.15);
        }

        // TODO Test Code
        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            cargoIntake.set(Constants.cargoIntakePower);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
            cargoIntake.set(-Constants.cargoOuttakePower);
        } else {
            cargoIntake.set(0);
        }

        if (xbox.getButton(XboxController.BUTTON_Y)) {
            cargoLift.set(-Constants.cargoPowerUp);
        } else if (xbox.getButton(XboxController.BUTTON_X)) {
            cargoLift.set(Constants.cargoPowerDown);
        } else {
            if (cargoPot.get() <= 130) {
                cargoLift.set(0.0);
            } else if (cargoPot.get() > 130 && cargoPot.get() <= 190) {
                cargoLift.set(-0.25);
            } else if (cargoPot.get() > 190 && cargoPot.get() <= 251) {
                cargoLift.set(-0.20);
            } 
        }

        if ((System.currentTimeMillis() - time) % 50 == 0)
            System.out.println("Pot Voltage: " + cargoPot.get());

    }

    public void updateStateInfo() {
        double currentVoltage = cargoPot.get();
        // Calculate velocity
        // For underclassmen, delta means "change in"
        double deltaPos = currentVoltage - CargoTask.CargoState.cargoVoltage; // TODO rename position
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;
        double velocity = (deltaPos / deltaTime);
        double power = cargoLift.get();
        if (deltaPos == 0)
            return;

        CargoTask.CargoState.updateState(power, velocity, currentVoltage);
    }

}