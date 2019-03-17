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

        // gets the current cargo voltage (should be for start) so the rest can be
        // calculated relative to it
        cargoStartVoltage = cargoPot.get();
<<<<<<< HEAD
        cargoGroundVoltage = cargoStartVoltage - 2;
        cargoLowRocketVoltage = cargoStartVoltage - 1.5;
        cargoBayVoltage = cargoStartVoltage - .75;
=======
        
        //TODO -
        //writer = new AutoWriter(carg
        
        cargoGroundVoltage = cargoStartVoltage - 8;
        cargoLowRocketVoltage = cargoStartVoltage - 6;
        cargoBayVoltage = cargoStartVoltage - 3;
>>>>>>> 15018ac92814a95392ba2a02823ea24ef5be57cf
        // System.out.println(cargoStartVoltage);
        // System.out.println(cargoGroundVoltage);
        // System.out.println(cargoLowRocketVoltage);
        // System.out.println(cargoBayVoltage);
    }

    public void runSubsystem() {
        updateStateInfo();
        // automated cargo code
        // System.out.println(automating);
        // System.out.println(cargoPot.get());
        if (automating) {
            // if(xbox.getButton(XboxController.BUTTON_X)){
            //     subsystemMode = CargoMode.GROUND;
            // }
            // if (xbox.getButton(XboxController.BUTTON_Y)){
            //     System.out.println("Going up!");
            //     subsystemMode = CargoMode.CARGOBAY;
            // }

            // if (xbox.getSingleButtonPress(controls.map.get("cargo_bay"))) {
            //     System.out.println("cargo bay " + cargoPot.get());
            //     if (Math.abs(cargoPot.get() - cargoGroundVoltage) <= Math.abs(cargoPot.get() - cargoBayVoltage)) {
            //         System.out.println("ground to bay");
            //         subsystemMode = CargoMode.CARGOBAY;
            //     } else if (Math.abs(cargoPot.get() - cargoGroundVoltage) > Math.abs(cargoPot.get() - cargoBayVoltage)) {
            //         System.out.println("bay to ground "  + cargoPot.get());
            //         subsystemMode = CargoMode.GROUND;
            //     } else {
            //         System.out.println("holding somehow");
            //         cargoLift.set(getHoldPower());
            //     }

            // } else if (xbox.getSingleButtonPress(controls.map.get("cargo_rocket"))) {
            //     System.out.println("cargo rocket " + cargoPot.get() + " " +
            //         Math.abs(cargoPot.get() - cargoGroundVoltage) + " " + 
            //         Math.abs(cargoPot.get() - cargoLowRocketVoltage) + " " +
            //         (Math.abs(cargoPot.get() - cargoGroundVoltage) <= Math.abs(cargoPot.get() - cargoLowRocketVoltage))
            //         );
            //     if (Math.abs(cargoPot.get() - cargoGroundVoltage) <= Math.abs(cargoPot.get() - cargoLowRocketVoltage)) {
            //         System.out.println("to rocket " + cargoPot.get());
            //         subsystemMode = CargoMode.LOWROCKET;
            //     } else if (Math.abs(cargoPot.get() - cargoGroundVoltage) > Math.abs(cargoPot.get() - cargoLowRocketVoltage)) {
            //         System.out.println("to ground " + cargoPot.get());
            //         subsystemMode = CargoMode.GROUND;
            //     } else {
            //         System.out.println("holding for some reaon");
            //         cargoLift.set(getHoldPower());
            //     }
            // }
            //System.out.println(subsystemMode);
            CargoTask.CargoOrder cargoOrder = subsystemMode.callLoop();

            cargoLift.set(cargoOrder.cargoPower);

        } else { // end automating
            if (cargoPot.get() > cargoStartVoltage) {
                cargoLift.set(0.20);
            } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_up"), 0.1)) {
                cargoLift.set(getUpPower());
            } else if (xbox.getAxisGreaterThan(controls.map.get("cargo_down"), 0.1)) {
                cargoLift.set(getDownPower());
            } else {
                cargoLift.set(getHoldPower());
            }
        } // end else

        // cargo intake code
        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            // System.out.println("left bumper");
            cargoIntake.set(0.5);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
            // System.out.println("right bumper");
            cargoIntake.set(-1.0);
        } else {
            cargoIntake.set(0.0);
        }
        // if (xbox.getButton(controls.map.get("cargo_intake"))) {
        // cargoIntake.set(Constants.cargoIntakePower);
        // } else if (xbox.getButton(controls.map.get("cargo_outtake"))) {
        // cargoIntake.set(-Constants.cargoOuttakePower);
        // } else {
        // cargoIntake.set(0);
        // }

        if ((System.currentTimeMillis() - time) % 50 == 0) {
            // System.out.println("Pot Voltage: " + cargoPot.get());
            // System.out.println(subsystemMode);
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

    public void setCargoLift(double speed) {
        cargoLift.set(speed);
    }

    public static double getHoldPower() {
        if (cargoPot.get() >= (cargoGroundVoltage) && cargoPot.get() < cargoLowRocketVoltage) {
            // System.out.println("ground/rocket");
            return -0.25;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage-0.2)) {
            // System.out.println("rocket/start");
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
        if (cargoPot.get() >= (cargoGroundVoltage - 0.2) && cargoPot.get() < cargoLowRocketVoltage) {
            return 0.15;
        } else if (cargoPot.get() >= cargoLowRocketVoltage && cargoPot.get() < (cargoStartVoltage)) {
            return 0.23;
        } else if (cargoPot.get() >= cargoStartVoltage) {
            return 0.55;
        } else {
            return 0.0;
        }
    }
    
    /**
    *Returns [max height pot val, min height pot val]
    */
    public static double[] getPotRange(){
        return new double[] {cargoStartVoltage, cargoGroundVoltage};
    }
}
