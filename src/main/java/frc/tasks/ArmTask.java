package frc.tasks;

import frc.robot.*;

/**
 * This is a task class for controlling the Arm mechanism during autonomous.
 */
public class ArmTask extends Task implements UrsaRobot {
    public enum ArmMode {
        GROUND, HATCH, LOWROCKET, CARGOBAY, CLIMB;

        public ArmMode getNext() {
            return this.ordinal() < ArmMode.values().length - 1 ? ArmMode.values()[this.ordinal() + 1] : GROUND;
        }

        public ArmMode getPrevious() {
            return this.ordinal() > 0 ? ArmMode.values()[this.ordinal() - 1] : LOWROCKET;
        }

        public ArmOrder callLoop() {
            switch (this) {
            case GROUND:
                return moveToAngle(Arm.armGroundVoltage);
            case HATCH:
                return moveToAngle(Arm.armGroundVoltage + 10);
            case LOWROCKET:
                return moveToAngle(Arm.armLowRocketVoltage);
            case CARGOBAY:
                return moveToAngle(Arm.armBayVoltage);
            case CLIMB:
                // return moveToAngle(Arm.armStartVoltage);
            }
            running = false;
            return new ArmOrder(0.0);
        }

        private ArmOrder moveToAngle(double desiredVoltage) {
            double voltageTolerance = 3;

            if (Math.abs(ArmState.armVoltage - desiredVoltage) <= voltageTolerance) {
                running = false;
                return new ArmOrder(feedForward(getArmAngle()));
            }

            // TODO find critical point (oscillates forever) and period (length
            // of oscillations)
            double kcArm = 1.0 / 85.0; // critical point, aka the P value where the arm oscillates forever
            double tcArm = 1.0; // time per oscillation at the critical point

            double kpArm = 0.6 * kcArm;
            double kdArm = (3.0 / 40) * kcArm * tcArm;
            double kiArm = 1.5 * kcArm;

            // TODO reconfigure these based on new findings
            // double armDownMinimumPower = 0.1;
            double armDownMaxPower = .13;
            // double armUpMinimumPower = 0.2;
            double armUpMaxPower = 0.45;

            System.out.println("Desired: " + desiredVoltage);
            System.out.println("Current: " + ArmState.armVoltage);
            System.out.println("Velocity: " + ArmState.armVelocity);
            System.out.println("Error Sum: " + ArmState.errorSum);

            double error = desiredVoltage - ArmState.armVoltage;
            ArmState.errorSum += error * ArmState.deltaTime;
        
            double armPower = kpArm * (error) + (kdArm * ArmState.armVelocity)
                    + feedForward(getArmAngle()) + kiArm * ArmState.errorSum;
            armPower *= -1;
            System.out.println("Calculated Power: " + armPower);

            // if (armPower < 0 && Math.abs(armPower) < armUpMinimumPower) { // going up
            //     System.out.println("PID Power too weak, using armUpMinimumPower");
            //     armPower = -1.0 * armUpMinimumPower;
            // } else 
            if (armPower < 0 && Math.abs(armPower) > armUpMaxPower) { // going up
                System.out.println("PID Power too strong, using armUpMaxPower");
                armPower = -1 * armUpMaxPower;
            } 
            // else if (armPower > 0 && Math.abs(armPower) < armDownMinimumPower) { // going down
            //     System.out.println("PID Power too weak, using armDowninimumPower");
            //     armPower = armDownMinimumPower;
            // } 
            else if (armPower > 0 && Math.abs(armPower) > armDownMaxPower) { // going down
                System.out.println("PID Power too strong, using armDownMaxPower");
                armPower = armDownMaxPower;
            }

            System.out.println("Arm Power: " + armPower);
            return new ArmOrder(armPower);
        }
    }

    private static final double armMass = 5.8287; // kilograms // was 4.6947 without hatch
    private static final double armRadius = .365; // meters
    private static final double torqueCoefficient = armRadius * armMass * 9.81; // r * m * g
    private static final double torqueToVoltRegression = 6.38982; // slope of torque vs voltage graph
    private static final double motorRange = 12.0;
    private static final double voltAngleSlope = (90.0 - 0.0) / (75.3 - 0.356); // delta angle / delta voltage
    private static double motorEfficiencyFactor = 2; // old motors means calculations aren't always accurate

    public static double getArmAngle() {
        return voltAngleSlope * Arm.armPot.get();
    }

    public static double feedForward(double angle) {
        if (Arm.armPot.get() <= 15) {
            motorEfficiencyFactor = 3.2;
        } else if (Arm.armPot.get() <= 25) {
            motorEfficiencyFactor = 3.25;
        } else if (Arm.armPot.get() <= 45){
            motorEfficiencyFactor = 2.6;
        } else if (Arm.armPot.get() <= 70) {
            motorEfficiencyFactor = 2;
        } else if (Arm.armPot.get() <= 80) {
            motorEfficiencyFactor = 1.5;
        } else if (Arm.armPot.get() > 80) {
            motorEfficiencyFactor = 1;
        }

        if (angle < 0.43) {
            return 0;
        }

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
        return (power * motorEfficiencyFactor) * -1 * Math.signum(power);
    }

    public static class ArmState {
        public static double armVelocity = 0.0, armVoltage = 0.0;
        public static double errorSum = 0.0, deltaTime = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double armVelocity, double armVoltage, double deltaTime) {
            ArmState.armVelocity = armVelocity;
            ArmState.armVoltage = armVoltage;
            stateTime = System.currentTimeMillis();
            ArmState.deltaTime = deltaTime;
        }

    }

    public static class ArmOrder {
        public double armPower;

        public ArmOrder(double power) {
            this.armPower = power;
        }
    }

    private static boolean running = true;

    public ArmTask(ArmMode mode, Arm arm) {
        running = true;
        arm.setMode(mode);
        Thread t = new Thread(this, "ArmTask");
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
