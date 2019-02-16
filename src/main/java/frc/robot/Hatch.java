package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.tasks.*;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    // private AnalogInput hatchPot;
    private Potentiometer hatchPot;

    // Position constants
    // TODO set the encoder distances for these positions
    // determined experimentally
    double intakePosition = 0.0;
    double deployPosition = 0.0;
    double carryPosition = 0.0;

    double defaultPosition = 0.0;
    double currentPosition; // Encoder?
    private long time;

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        hatchPot = new AnalogPotentiometer(1, 360, 0);
        time = System.currentTimeMillis();

        // Number of degrees per pulse (7 pulses in one revolution)
        // hatchEncoder.setDistancePerPulse(HATCH_DEGREES_PER_TICK);
        // hatchEncoder.reset();

        currentPosition = defaultPosition;
    }

    public void runSubsystem() {
        // TODO This will be used once future code is set up
        // updateStateInfo();
        // HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop();
        // hatchMotor.set(hatchOrder.hatchPower);

        // TODO test code, delete
        // if (xbox.getButton(XboxController.BUTTON_B)) { // Goes up
        //     hatchMotor.set(0.50);
        // } else if (xbox.getButton(XboxController.BUTTON_A)) { // Goes down
        //     hatchMotor.set(-0.40);
        // } else {
        //     hatchMotor.set(0.0);
        // }
        // if((System.currentTimeMillis() - time) % 50 == 0)
        //     System.out.println("Pot Voltage: " + hatchPot.get());
        
    }

    public void updateStateInfo() {
        double currentVoltage = hatchPot.get();
        // hatchDistance is our distance along an arc
        double deltaPos = currentVoltage - HatchTask.HatchState.hatchVoltage;
        double deltaTime = System.currentTimeMillis() - HatchTask.HatchState.stateTime;
        double velocity = deltaPos / deltaTime;
        if (deltaPos == 0)
            return;
        HatchTask.HatchState.updateState(velocity, currentVoltage);
    }

}