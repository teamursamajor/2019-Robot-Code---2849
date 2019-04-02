package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.*;
import frc.tasks.ArmTask.ArmMode;

/**
*This controls how high up our arm is
*  as well as it's intake/outake
*/
public class Arm extends Subsystem<ArmTask.ArmMode> implements UrsaRobot {
    
    public static double armGroundVoltage, armLowRocketVoltage, armBayVoltage, armStartVoltage;

    public static Spark armLift;
    public static Potentiometer armPot;
    private long time;

    public static boolean automating = false;

    public Arm() {
        armLift = new Spark(ARM_LIFT);
        armPot = new AnalogPotentiometer(CARGO_POT_CHANNEL, 360, 0);
        subsystemMode = ArmMode.CLIMB;
        time = System.currentTimeMillis();

        // gets the current arm voltage (should be for start) so the rest can be
        // calculated relative to it
        armStartVoltage = armPot.get();
        armGroundVoltage = armStartVoltage - 98;
        armLowRocketVoltage = armStartVoltage - 40;
        armBayVoltage = armStartVoltage - 20;
    }

    public void runSubsystem() {
        updateStateInfo();
        
        // Automated Arm
        if (automating) {
            if (xbox.getSingleButtonPress(controls.map.get("arm_ground"))) {
                subsystemMode = ArmMode.GROUND;
            } else if (xbox.getSingleButtonPress(controls.map.get("arm_bay"))) {
                subsystemMode = ArmMode.CARGOBAY;
            } else if (xbox.getSingleButtonPress(controls.map.get("arm_rocket"))) {
                subsystemMode = ArmMode.LOWROCKET;
            }
            ArmTask.ArmOrder armOrder = subsystemMode.callLoop();
            // armLift.set(armOrder.armPower);

        } 
        
        // Manual Arm
        else {
            if (armPot.get() > armStartVoltage) {
                // armLift.set(0.20);
            } else if (xbox.getAxisGreaterThan(controls.map.get("arm_up"), 0.1)) {
                armLift.set(getUpPower());
            } else if (xbox.getAxisGreaterThan(controls.map.get("arm_down"), 0.1)) {
                armLift.set(getDownPower());
            } else {
                armLift.set(0.0);
                // armLift.set(ArmTask.feedForward(ArmTask.getArmAngle()));
            }
        }

        if ((System.currentTimeMillis() - time) % 50 == 0) {
            System.out.println("Pot Voltage: " + armPot.get());
        }

    }

    /**
    * Updates the following items:
    *<ul>
    *<li><b>Potentiometer voltage</b> - used for calculating the arm's current angle</li>
    *<li>The <b>speed</b> of the arm</li>
    *</ul>
    */
    public void updateStateInfo() {
        double currentVoltage = armPot.get();
        double deltaVolt = currentVoltage - ArmTask.ArmState.armVoltage;
        double deltaTime = System.currentTimeMillis() - ArmTask.ArmState.stateTime;

        if (deltaTime <= 5)
            return;
        double velocity = (deltaVolt / deltaTime);
        ArmTask.ArmState.updateState(velocity, currentVoltage);
    }

    public static double getUpPower() {
        if (armPot.get() < armLowRocketVoltage) {
            return -0.6;
        } else if (armPot.get() >= armLowRocketVoltage && armPot.get() < (armStartVoltage)) {
            return -0.45;
        } else {
            return 0.0;
        }
    }

    public static double getDownPower() {
        if (armPot.get() >= (armGroundVoltage) && armPot.get() < armLowRocketVoltage) {
            return 0.15;
        } else if (armPot.get() >= armLowRocketVoltage && armPot.get() < (armStartVoltage)) {
            return 0.23;
        } else if (armPot.get() >= armStartVoltage) {
            return 0.55;
        } else {
            return 0.0;
        }
    }

    /**
     * Deprecated, use ArmTask.feedForward(double angle) instead
     */
    public static double getHoldPower() {
        if (armPot.get() >= (armGroundVoltage) && armPot.get() < armLowRocketVoltage) {
            return -0.25;
        } else if (armPot.get() >= armLowRocketVoltage && armPot.get() < (armStartVoltage)) {
            return -0.20;
        } else {
            return 0.0;
        }
    }

}
