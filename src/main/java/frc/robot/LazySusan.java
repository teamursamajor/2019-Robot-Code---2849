package frc.robot;

import frc.tasks.*;
import frc.tasks.SusanTask.SusanMode;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.AnalogInput;

public class LazySusan extends Subsystem<SusanTask.SusanMode> implements UrsaRobot {

    private Spark susanMotor;

    private AnalogInput susanPot;

    public LazySusan() {
        susanMotor = new Spark(LAZY_SUSAN);
        susanPot = new AnalogInput(2);
        subsystemMode = SusanMode.FORWARD;
    }

    public void runSubsystem() {
        // updateStateInfo();
        // SusanTask.SusanOrder susanOrder = subsystemMode.callLoop();
        // susanMotor.set(susanOrder.power);
        // TODO delete, test code
        if (xbox.getAxisGreaterThan(XboxController.AXIS_LEFTTRIGGER, 0.1)) {
            susanMotor.set(-.25);
        } else if (xbox.getAxisGreaterThan(XboxController.AXIS_RIGHTTRIGGER, 0.1)){
             susanMotor.set(0.25);
        } else {
            susanMotor.set(0.0);
        }
    }

    public void updateStateInfo(){
        // TODO use potentiometer
        double currentAngle = 0.0;
        double deltaAngle = currentAngle - SusanTask.SusanState.currentAngle;
        double deltaTime = System.currentTimeMillis() - SusanTask.SusanState.stateTime;
        double velocity = deltaAngle / deltaTime;
        if(deltaAngle == 0)
            return;
        SusanTask.SusanState.updateState(velocity, getAngle(), susanPot.getAverageVoltage());
    }

    public double getAngle(){
        return 0.0;
    }
}