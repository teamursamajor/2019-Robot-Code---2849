package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoMode;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    public static double cargoGroundVoltage, cargoLowRocketVoltage, cargoBayVoltage, cargoStartVoltage;

    private Spark cargoIntake, cargoLift;
    public static Potentiometer cargoPot;
    private long time;

    public static boolean automating = false;

    public Cargo() {
        cargoLift = new Spark(CARGO_LIFT);
        cargoIntake = new Spark(CARGO_INTAKE);
        cargoPot = new AnalogPotentiometer(CARGO_POT_CHANNEL, 360, 0);
        subsystemMode = CargoMode.CLIMB;
        time = System.currentTimeMillis();

        // gets the current cargo voltage (should be for start)
        // so the rest can be calculated relative to it
        // TODO update for VEX potentiometer
        cargoStartVoltage = cargoPot.get();
        cargoGroundVoltage = cargoStartVoltage - 2;
        cargoLowRocketVoltage = cargoStartVoltage - 1.25;
        cargoBayVoltage = cargoStartVoltage - 0.75;
    }

    public void runSubsystem() {
        updateStateInfo();
        // automated cargo code
        if (automating) {
            if (xbox.getSingleButtonPress(XboxController.BUTTON_X)) {
                subsystemMode = CargoMode.GROUND;
            } else if (xbox.getSingleButtonPress(XboxController.BUTTON_Y)) {
                subsystemMode = CargoMode.CARGOBAY;
            } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) {
                subsystemMode = CargoMode.LOWROCKET;
            }
            CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();
            cargoLift.set(cargoOrder.cargoPower);

        } else {
            if (cargoPot.get() > cargoStartVoltage) {
                cargoLift.set(0.20);
            } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_up"), 0.1)) {
                cargoLift.set(getUpPower());
            } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_down"), 0.1)) {
                cargoLift.set(getDownPower());
            } else {
                cargoLift.set(CargoTask.feedForward(CargoTask.getCargoAngle()));
            }
        }

        // cargo intake code
        if (xbox.getButton(controls.map.get("cargo_intake"))) {
            cargoIntake.set(0.55);
        } else if (xbox.getButton(controls.map.get("cargo_outtake"))) {
            cargoIntake.set(-1.0);
        } else {
            cargoIntake.set(0.0);
        }

        if ((System.currentTimeMillis() - time) % 50 == 0) {
            // System.out.println("Pot Voltage: " + cargoPot.get());
            System.out.println(CargoTask.feedForward(CargoTask.getCargoAngle()));
            // System.out.println(subsystemMode);
        }

    }

    public void updateStateInfo() {
        double currentVoltage = cargoPot.get();
        double deltaVolt = currentVoltage - CargoTask.CargoState.cargoVoltage;
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;

        if (deltaTime <= 5)
            return;
        double velocity = (deltaVolt / deltaTime);
        CargoTask.CargoState.updateState(velocity, currentVoltage);
    }

    public static double getHoldPower() {
        if (cargoPot.get() >= (cargoGroundVoltage) && cargoPot.get() < cargoLowRocketVoltage) {
            return -0.25;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage)) {
            return -0.20;
        } else {
            return 0.0;
        }
    }

    public static double getUpPower() {
        if (cargoPot.get() < cargoLowRocketVoltage) {
            return -0.6;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage)) {
            return -0.45;
        } else {
            return 0.0;
        }
    }

    public static double getDownPower() {
        if (cargoPot.get() >= (cargoGroundVoltage) && cargoPot.get() < cargoLowRocketVoltage) {
            return 0.15;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage)) {
            return 0.23;
        } else if (cargoPot.get() >= cargoStartVoltage) {
            return 0.55;
        } else {
            return 0.0;
        }
    }
}