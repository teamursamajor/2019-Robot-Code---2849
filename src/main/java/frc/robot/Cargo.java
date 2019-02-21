package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoMode;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {

    private Spark cargoIntake;
    private Spark cargoLift;
    private static Potentiometer cargoPot;
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
        if (automating) {
            // If we need to save button space, then use one button that goes
            // ground -> rocket -> bay -> rocket -> ground
            if (xbox.getSingleButtonPress(controls.map.get("cargo_bay"))) {
               
                if (subsystemMode.equals(CargoMode.GROUND)) {
                    // untested, remove if it breaks the robot lol
                    subsystemMode = CargoMode.LOWROCKET;
                    try{
                        Thread.sleep(250);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    subsystemMode = CargoMode.CARGOBAY;
                }
                else if (subsystemMode.equals(CargoMode.CARGOBAY))
                    subsystemMode = CargoMode.GROUND;

            } else if (xbox.getSingleButtonPress(controls.map.get("cargo_rocket"))) {
               
                if (subsystemMode.equals(CargoMode.GROUND))
                    subsystemMode = CargoMode.LOWROCKET;
                else if (subsystemMode.equals(CargoMode.LOWROCKET))
                    subsystemMode = CargoMode.GROUND;

            } else {
                cargoLift.set(getHoldPower());
            }

            CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();

            if (subsystemMode.equals(CargoMode.GROUND)) {
                cargoLift.set(0.25);
            } else {
                cargoLift.set(cargoOrder.cargoPower);
            }

        } else {            
            if (cargoPot.get() > UrsaRobot.cargoStartVoltage) {
                cargoLift.set(0.20);
            } else if (xbox.getPOV() == controls.map.get("cargo_up")) {
                cargoLift.set(-0.20);
            } else if (xbox.getPOV() == controls.map.get("cargo_down")) {
                cargoLift.set(0.20);
            } else {
                getHoldPower();
            }
        }

        if (xbox.getButton(controls.map.get("cargo_intake"))) {
            cargoIntake.set(Constants.cargoIntakePower);
        } else if (xbox.getButton(controls.map.get("cargo_outtake"))) {
            cargoIntake.set(-Constants.cargoOuttakePower);
        } else {
            cargoIntake.set(0);
        }

        if ((System.currentTimeMillis() - time) % 50 == 0) {
            System.out.println("Pot Voltage: " + cargoPot.get());
            System.out.println(subsystemMode);
        }

    }

    public void updateStateInfo() {
        double currentVoltage = cargoPot.get();
        double deltaVolt = currentVoltage - CargoTask.CargoState.cargoVoltage;
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;

        double velocity = (deltaVolt / deltaTime);

        if (Math.abs(deltaVolt) <= 5 || deltaTime <= 5)
            return;
        CargoTask.CargoState.updateState(velocity, currentVoltage);
    }

    public double getCargoVoltage() {
        return cargoPot.get();
    }

    public void setCargoLift(double speed) {
        cargoLift.set(speed);
    }

    public static double getHoldPower() {
        if (cargoPot.get() >= (UrsaRobot.cargoGroundVoltage + 5) && cargoPot.get() < 190) {
            return -0.25;
        } else if (cargoPot.get() >= 190 && cargoPot.get() < (UrsaRobot.cargoBayVoltage + 5)) {
            return -0.20;
        } else {
            return 0.0;
        }
    }
}