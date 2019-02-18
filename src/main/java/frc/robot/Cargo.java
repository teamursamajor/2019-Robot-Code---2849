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
        cargoPot = new AnalogPotentiometer(0, 360, 0);
        // subsystemMode = CargoMode.START;
        subsystemMode = CargoMode.GROUND; // temporary
        time = System.currentTimeMillis();
    }

    public void runSubsystem() {
        updateStateInfo();
        if (automating) {
            if (xbox.getSingleButtonPress(XboxController.BUTTON_Y)) {
                subsystemMode = subsystemMode.getNext();
            } else if (xbox.getSingleButtonPress(XboxController.BUTTON_X)) {
                subsystemMode = subsystemMode.getPrevious();
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
            if (cargoPot.get() < UrsaRobot.cargoGroundVoltage) {
                cargoLift.set(-0.20);
            } else if (cargoPot.get() > UrsaRobot.startVoltage) {
                cargoLift.set(0.20);
            } else if (xbox.getAxisGreaterThan(XboxController.AXIS_LEFTTRIGGER, 0.1)) {
                cargoLift.set(-0.20);
            } else if (xbox.getAxisGreaterThan(XboxController.AXIS_RIGHTTRIGGER, 0.1)) {
                cargoLift.set(0.20);
            } else {
                getHoldPower();
            }
        }

        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            cargoIntake.set(Constants.cargoIntakePower);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
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

        double power = cargoLift.get();
        if (Math.abs(deltaVolt) <= 5 || deltaTime <= 5)
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
        if (cargoPot.get() >= (UrsaRobot.cargoGroundVoltage + 5) && cargoPot.get() < 190) {
            return -0.25;
        } else if (cargoPot.get() >= 190 && cargoPot.get() < (UrsaRobot.cargoBayVoltage + 5)) {
            return -0.20;
        } else {
            return 0.0;
        }
    }
}