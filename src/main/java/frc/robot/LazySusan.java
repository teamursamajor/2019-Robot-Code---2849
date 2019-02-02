package frc.robot;

import frc.tasks.*;
import frc.tasks.SusanTask.SusanMode;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;

public class LazySusan extends Subsystem<SusanTask.SusanMode> implements UrsaRobot {

    private Spark susanMotor;
    private DigitalInput limitSwitch;

    public LazySusan() {
        susanMotor = new Spark(LAZY_SUSAN);
        limitSwitch = new DigitalInput(SUSAN_SWITCH_CHANNEL);
        subsystemMode = SusanMode.FORWARD;
    }

    public void runSubsystem() {
        // TODO this is temporary code. Once the limit switch is on the lazy susan we
        // can use it to track our position and move accordingly

        // I'm thinking we would store our current position, and then use the dpad to
        // move to a specific direction no matter where we start from. Not 100% sure how
        // to do this yet
        //susanMotor.set(0.25);
        if (xbox.getButton(XboxController.BUTTON_LEFTBUMPER)) {
            susanMotor.set(.25);
        } else if (xbox.getButton(XboxController.BUTTON_RIGHTBUMPER)) {
            susanMotor.set(-0.25);
        } else {
            susanMotor.set(0.0);
        }
    }
}