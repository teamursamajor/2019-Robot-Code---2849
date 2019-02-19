package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    private Spark turntableMotor;
    private Potentiometer turntablePot;

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        turntablePot = new AnalogPotentiometer(2, 360, 0);
        subsystemMode = TurntableMode.FORWARD;
    }

    public void runSubsystem() {
        // updateStateInfo();
        // TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        // turntableMotor.set(turntableOrder.power);

        // TODO test code
        if (xbox.getButton(XboxController.BUTTON_START)) {
            turntableMotor.set(-.4);
        } else if (xbox.getButton(XboxController.BUTTON_BACK)) {
            turntableMotor.set(0.4);
        } else {
            turntableMotor.set(0.0);
        }
    }

    public void updateStateInfo(){
        // TODO update to use voltage
        // TODO use potentiometer
        double currentVoltage = turntablePot.get();
        double deltaVoltage = currentVoltage - TurntableTask.TurntableState.voltage;
        double deltaTime = System.currentTimeMillis() - TurntableTask.TurntableState.stateTime;
        double velocity = deltaVoltage / deltaTime;
        if(Math.abs(deltaVoltage) <= 5 || deltaTime <= 5)
            return;
        TurntableTask.TurntableState.updateState(currentVoltage, velocity);
    }
}