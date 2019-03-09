package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoMode;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    public static double cargoGroundVoltage, cargoLowRocketVoltage, cargoBayVoltage, cargoStartVoltage;

    private Spark cargoIntake;
    private Spark cargoLift;
    public static Potentiometer cargoPot;
    private long time;

    public static boolean automating = true;

    public Cargo() {
        cargoLift = new Spark(CARGO_LIFT);
        cargoIntake = new Spark(CARGO_INTAKE);
        cargoPot = new AnalogPotentiometer(CARGO_POT_CHANNEL, 360, 0);
        subsystemMode = CargoMode.GROUND;
        time = System.currentTimeMillis();

        // gets the current cargo voltage (should be for start) so the rest can be
        // calculated relative to it
        cargoStartVoltage = cargoPot.get();
        cargoGroundVoltage = cargoStartVoltage - 5;
        cargoLowRocketVoltage = cargoStartVoltage - 3.4;
        cargoBayVoltage = cargoStartVoltage - 2.4;
    }

    public void runSubsystem() {
        updateStateInfo();

        // automated cargo code
        if (automating) {
            // If we need to save button space, then use one button that goes
            // ground -> rocket -> bay -> rocket -> ground
            if (xbox.getSingleButtonPress(controls.map.get("cargo_bay"))) {

                if (subsystemMode.equals(CargoMode.GROUND)) {
                    System.out.println("ground to bay");
                    subsystemMode = CargoMode.CARGOBAY;
                } else if (subsystemMode.equals(CargoMode.CARGOBAY)) {
                    System.out.println("bay to ground");
                    subsystemMode = CargoMode.GROUND;
                }

            } else if (xbox.getSingleButtonPress(controls.map.get("cargo_rocket"))) {

                if (subsystemMode.equals(CargoMode.GROUND)) {
                    System.out.println("to rocket");
                    subsystemMode = CargoMode.LOWROCKET;
                } else if (subsystemMode.equals(CargoMode.LOWROCKET)) {
                    System.out.println("to ground");
                    subsystemMode = CargoMode.GROUND;

                } else {
                    cargoLift.set(getHoldPower());
                }

                CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();

                if (subsystemMode.equals(CargoMode.GROUND)) {
                    cargoLift.set(cargoOrder.cargoPower);
                } else {
                    cargoLift.set(cargoOrder.cargoPower);
                }

            } else {
                if (cargoPot.get() > cargoStartVoltage) {
                    cargoLift.set(0.20);
                } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_up"), 0.1)) {
                    cargoLift.set(-0.20);
                } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_down"), 0.1)) {
                    cargoLift.set(0.20);
                }

                // manual movement of cargo lift pov
                // else if (xbox.getPOV() == controls.map.get("cargo_up")) {
                // cargoLift.set(-0.20);
                // } else if (xbox.getPOV() == controls.map.get("cargo_down")) {
                // cargoLift.set(0.20);
                // }

                else {
                    getHoldPower();
                }
            }

            // cargo intake code
            if (xbox.getButton(controls.map.get("cargo_intake"))) {
                cargoIntake.set(Constants.cargoIntakePower);
            } else if (xbox.getButton(controls.map.get("cargo_outtake"))) {
                cargoIntake.set(-Constants.cargoOuttakePower);
            } else {
                cargoIntake.set(0);
            }

            if ((System.currentTimeMillis() - time) % 50 == 0) {
                // System.out.println("Pot Voltage: " + cargoPot.get());
                System.out.println(subsystemMode);
            }

        }
    }

    public void updateStateInfo() {
        double currentVoltage = cargoPot.get();
        double deltaVolt = currentVoltage - CargoTask.CargoState.cargoVoltage;
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;

        double velocity = (deltaVolt / deltaTime);
        // System.out.println("Cargo Velocity: " + velocity);
        if (Math.abs(deltaVolt) <= 5 || deltaTime <= 5)
            return;
        CargoTask.CargoState.updateState(velocity, currentVoltage);
    }

    public void setCargoLift(double speed) {
        cargoLift.set(speed);
    }

    public static double getHoldPower() {
        if (cargoPot.get() >= (cargoGroundVoltage + 0.2) && cargoPot.get() < cargoLowRocketVoltage) {
            return -0.25;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage)) {
            return -0.16;
        } else {
            return 0.0;
        }
    }

    public static double getUpPower() {
        if (cargoPot.get() >= (cargoGroundVoltage - 0.2) && cargoPot.get() < cargoLowRocketVoltage) {
            return -0.55;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage - 0.75)) {
            return -0.4;
        } else {
            return 0.0;
        }
    }

    public static double getDownPower() {
        if (cargoPot.get() >= (cargoGroundVoltage - 0.2) && cargoPot.get() < cargoLowRocketVoltage) {
            return 0.10;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage)) {
            return 0.15;
        } else if (cargoPot.get() >= cargoStartVoltage) {
            return 0.35;
        } else if (cargoPot.get() >= (cargoGroundVoltage + 5) && cargoPot.get() < 190) {
            return -0.25;
        } else if (cargoPot.get() >= 190 && cargoPot.get() < (cargoBayVoltage + 5)) {
            return -0.20;
        } else {
            return 0.0;
        }
    }
}