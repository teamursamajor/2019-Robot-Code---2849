package frc.tasks;

import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot {
    public enum CargoMode {
        GROUND, HATCH, LOWROCKET, CARGOBAY, CLIMB;

        public CargoMode getNext() {
            return this.ordinal() < CargoMode.values().length - 1 ? CargoMode.values()[this.ordinal() + 1] : GROUND;
        }

        public CargoMode getPrevious() {
            return this.ordinal() > 0 ? CargoMode.values()[this.ordinal() - 1] : LOWROCKET;
        }

        public CargoOrder callLoop() {
            switch (this) {
            case GROUND:
                return moveToAngle(Cargo.cargoGroundVoltage);
            case HATCH:
                return moveToAngle(Cargo.cargoGroundVoltage + 12);
            case LOWROCKET:
                return moveToAngle(Cargo.cargoLowRocketVoltage);
            case CARGOBAY:
                return moveToAngle(Cargo.cargoBayVoltage);
            case CLIMB:
                return moveToAngle(Cargo.cargoStartVoltage);
            }
            running = false;
            return new CargoOrder(0.0);
        }

        private CargoOrder moveToAngle(double desiredVoltage) {
            double voltageTolerance = 3;

            if (Math.abs(CargoState.cargoVoltage - desiredVoltage) <= voltageTolerance) {
                running = false;
                return new CargoOrder(feedForward(getCargoAngle()));
            }

            // TODO determine critical point (where it just oscillates forever) and period
            // (time between oscillations)
            double kcCargo = 0.0; // critical point, aka the P value where the arm oscillates forever
            double tcCargo = 0.0; // time per oscillation at the critical point

            double kpCargo = 0.6 * kcCargo;
            // double kiCargo = 1.2 * (kcCargo / tcCargo);
            double kdCargo = (3.0 / 40) * kcCargo * tcCargo;

            // TODO reconfigure these based on new findings
            double cargoDownMinimumPower = 0.15;
            double cargoDownMaxPower = .2;
            double cargoUpMinimumPower = 0.5;

            // Proportional constant * (angle error) + derivative constant * velocity (aka
            // pos / time)
            // System.out.println("Cargo Velocity: " + CargoState.cargoVelocity);

            // TODO eventually add D term
            double cargoPower = kpCargo * (desiredVoltage - CargoState.cargoVoltage)
                    + feedForward(getCargoAngle()) + kdCargo * CargoState.cargoVelocity;

            if (cargoPower < 0 && Math.abs(cargoPower) < cargoUpMinimumPower) { // going up
                System.out.println("PID Power too weak, using cargoMinimumPower");
                cargoPower = Math.signum(cargoPower) * cargoUpMinimumPower;
            } else if (cargoPower > 0 && Math.abs(cargoPower) < cargoDownMinimumPower) {
                System.out.println("PID Power too weak, using cargoMinimumPower");
                cargoPower = Math.signum(cargoPower) * cargoDownMinimumPower;
            }

            if (cargoPower > 0 && Math.abs(cargoPower) > cargoDownMaxPower) {
                System.out.println("PID Power too strong, using cargoMaxPower");
                cargoPower = cargoDownMaxPower;
            }

            System.out.println("Cargo Power: " + cargoPower);
            return new CargoOrder(cargoPower);
        }
    }

    private static final double cargoMass = 4.6947; // kilograms
    private static final double cargoRadius = .365; // meters
    private static final double torqueCoefficient = cargoRadius * cargoMass * 9.81; // r * m * g
    private static final double torqueToVoltRegression = 6.38982;
    private static final double motorRange = 12.0;
    private static final double voltAngleSlope = (90.0 - 0.0) / (100.35 - 0.35);
    private static final double motorEfficiencyFactor = 1;

    public static double getCargoAngle() {
        // y = mx + b
        // b = 0 since angle = 0 when voltage = 0
        return voltAngleSlope * Cargo.cargoPot.get();
    }

    public static double feedForward(double angle) {
        // theta to torque
        double torque = torqueCoefficient * Math.cos(angle);

        // torque to voltage
        // 100:1 gearbox ratio, so divide torque by 100
        double voltage = (torque / 100) * torqueToVoltRegression;

        // voltage to power
        double power = voltage / motorRange;

        /**
         * The power this returns is ~0.08. Our empirically determined values are
         * actually around ~0.2-0.25 This means that the motor is likely not as
         * efficient as a brand new motor which was used for the voltage -> torque
         * regression, so we need to multiply our output by some factor to account for
         * the motor inefficiency
         */
        return power * motorEfficiencyFactor;
    }

    public static class CargoState {
        public static double cargoVelocity = 0.0, cargoVoltage = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double cargoVelocity, double cargoVoltage) {
            CargoState.cargoVelocity = cargoVelocity;
            CargoState.cargoVoltage = cargoVoltage;
            stateTime = System.currentTimeMillis();
        }

    }

    public static class CargoOrder {
        public double cargoPower;

        public CargoOrder(double power) {
            this.cargoPower = power;
        }
    }

    private static boolean running = true;

    public CargoTask(CargoMode mode, Cargo cargo) {
        running = true;
        cargo.setMode(mode);
        Thread t = new Thread("CargoTask");
        t.start();
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}