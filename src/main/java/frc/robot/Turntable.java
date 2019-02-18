package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.AnalogInput;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    private Spark turntableMotor;

    // TODO how do you use a potentiometer?
    private AnalogInput turntablePot; // lazy turntable potentiometer

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        turntablePot = new AnalogInput(2);
        subsystemMode = TurntableMode.FORWARD;
    }

    public void runSubsystem() {
        // updateStateInfo();
        // TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        // turntableMotor.set(turntableOrder.power);
        // TODO delete, test code
        if (xbox.getButton(XboxController.BUTTON_A)) {
            turntableMotor.set(-.25);
        } else if (xbox.getButton(XboxController.BUTTON_B)) {
             turntableMotor.set(0.25);
        } else {
            turntableMotor.set(0.0);
        }
    }

    public void updateStateInfo(){
        // TODO use potentiometer
        double currentAngle = 0.0;
        double deltaAngle = currentAngle - 0;
        double deltaTime = System.currentTimeMillis() - TurntableTask.TurntableState.stateTime;
        double velocity = deltaAngle / deltaTime;
        if(deltaAngle == 0)
            return;
        TurntableTask.TurntableState.updateState(turntablePot.getAverageVoltage(), velocity);
    }

    public double getAngle(){
        return 0.0;
    }
}