package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoMode;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

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
                }

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
            System.out.println("Cargo Voltage: " + cargoPot.get());
            if (xbox.getAxisGreaterThan(controls.map.get("cargo_up"), 0.1)) {
                // System.out.println("up");
                cargoLift.set(getUpPower());
            } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_down"), 0.1)) {
                cargoLift.set(getDownPower());
                // System.out.println("down");
            } else if (cargoPot.get() > UrsaRobot.cargoStartVoltage + 1) {
                cargoLift.set(0.35);
            } else {
                // System.out.println(getHoldPower());
                cargoLift.set(getHoldPower());
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
        if (cargoPot.get() >= (UrsaRobot.cargoGroundVoltage + 0.2)
                && cargoPot.get() < UrsaRobot.cargoLowRocketVoltage) {
            return -0.25;
        } else if (cargoPot.get() >= UrsaRobot.cargoLowRocketVoltage
                && cargoPot.get() < (UrsaRobot.cargoStartVoltage)) {
            return -0.16;
        } else {
            return 0.0;
        }
    }

    public static double getUpPower() {
        if (cargoPot.get() >= (UrsaRobot.cargoGroundVoltage - 0.2)
                && cargoPot.get() < UrsaRobot.cargoLowRocketVoltage) {
            return -0.55;
        } else if (cargoPot.get() >= UrsaRobot.cargoLowRocketVoltage
                && cargoPot.get() < (UrsaRobot.cargoStartVoltage - 0.75)) {
            return -0.4;
        } else {
            return 0.0;
        }
    }

    public static double getDownPower() {
        if (cargoPot.get() >= (UrsaRobot.cargoGroundVoltage - 0.2)
                && cargoPot.get() < UrsaRobot.cargoLowRocketVoltage) {
            return 0.10;
        } else if (cargoPot.get() >= UrsaRobot.cargoLowRocketVoltage
                && cargoPot.get() < (UrsaRobot.cargoStartVoltage)) {
            return 0.15;
        } else if (cargoPot.get() >= UrsaRobot.cargoStartVoltage) {
            return 0.35;
        } else {
            return 0.0;
        }
    }
}