package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import frc.tasks.*;
import frc.tasks.HatchTask.HatchMode;
import frc.tasks.ArmTask.ArmMode;

/**
 * This subsystem class controls the Hatch servo mechanism.
 */
public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    public static Servo hatchServo;
    private boolean hatchOpen = true;

    private Arm arm;

    public Hatch(Arm arm) {
        hatchServo = new Servo(HATCH_SERVO);
        setMode(HatchMode.WAIT);
        this.arm = arm;
    }

    public void runSubsystem() {
        if(hatchOpen){
            System.out.println("Hatch is extended (hold ready)");
        } else {
            System.out.println("Hatch is open (pickup ready)");
        }
        
        if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
            hatchOpen = !hatchOpen;
            setMode(HatchTask.HatchMode.RUN);
        }

        if (getMode().equals(HatchMode.RUN)) {
            if (hatchOpen) { // close hatch, ready to pick up or drop off
                hatchServo.setPosition(0);
            } else { // open hatch, ready to move
                hatchServo.setPosition(1);
                arm.setMode(ArmMode.HATCH);
            }
        }

        setMode(HatchMode.WAIT);
    }
}
