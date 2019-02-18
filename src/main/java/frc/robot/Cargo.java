package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoMode;
import frc.tasks.CargoTask.CargoState;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoIntake;
    private Spark cargoLift;
    private static Potentiometer cargoPot;
    private long time;
    // private cargoArmMotorPower;

    public Cargo() {
        cargoLift = new Spark(CARGO_LIFT);
        cargoPot = new AnalogPotentiometer(0, 360, 0);
        cargoIntake = new Spark(CARGO_INTAKE);
        time = System.currentTimeMillis();
    }

    public void runSubsystem() {
        updateStateInfo();

        if (xbox.getButton(XboxController.BUTTON_Y)) {
            subsystemMode = subsystemMode.getNext();
        } else if (xbox.getButton(XboxController.BUTTON_X)) {
            subsystemMode = subsystemMode.getPrevious();
        } else {
            cargoLift.set(getHoldPower());   
        }

        CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();

        cargoLift.set(cargoOrder.cargoPower);

        if (cargoPot.get() <= 130) {
            cargoLift.set(-0.15);
        } else if (cargoPot.get() >= 245) {
            cargoLift.set(0.15);
        }

        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            cargoIntake.set(Constants.cargoIntakePower);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
            cargoIntake.set(-Constants.cargoOuttakePower);
        } else {
            cargoIntake.set(0);
        }

        if ((System.currentTimeMillis() - time) % 50 == 0)
            System.out.println("Pot Voltage: " + cargoPot.get());

    }

    public void updateStateInfo() {
        double currentVoltage = cargoPot.get();
        // Calculate velocity
        // For underclassmen, delta means "change in"
        double deltaVolt = currentVoltage - CargoTask.CargoState.cargoVoltage;
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;
        double velocity = (deltaVolt / deltaTime);
        double power = cargoLift.get();
        if (Math.abs(deltaVolt) <= 5)
            return;

        CargoTask.CargoState.updateState(power, velocity, currentVoltage);
    }

    public double getCargoVoltage() {
        return cargoPot.get();
    }

    public void setCargoLift(double speed) {
        cargoLift.set(speed);
    }

    public static double getHoldPower() {
        if (cargoPot.get() > 130 && cargoPot.get() <= 190) {
            return -0.25;
        } else if (cargoPot.get() > 190 && cargoPot.get() <= 251) {
            return -0.20;
        } else {
            return 0.0;
        }
    }
}