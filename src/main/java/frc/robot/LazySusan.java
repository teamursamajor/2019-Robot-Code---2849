package frc.robot;

import frc.tasks.*;
import frc.tasks.SusanTask.SusanMode;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.AnalogInput;

public class LazySusan extends Subsystem<SusanTask.SusanMode> implements UrsaRobot {

    private Spark susanMotor;

    // TODO how do you use a potentiometer?
    private AnalogInput susanPot; // lazy susan potentiometer

    public LazySusan() {
        susanMotor = new Spark(LAZY_SUSAN);
        susanPot = new AnalogInput(0);
        subsystemMode = SusanMode.FORWARD;
    }

    public void runSubsystem() {
        // updateStateInfo();
        // SusanTask.SusanOrder susanOrder = subsystemMode.callLoop();
        // susanMotor.set(susanOrder.power);
        // TODO delete, test code
        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            susanMotor.set(.25);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
            susanMotor.set(-0.25);
        } else {
            susanMotor.set(0.0);
        }
    }

    public void updateStateInfo(){
        // TODO use potentiometer
        double currentAngle = 0.0;
        double deltaAngle = currentAngle - SusanTask.SusanState.angle;
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