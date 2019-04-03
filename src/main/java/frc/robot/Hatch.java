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

    private long hatchRunTime = 1050;
    private double hatchPower = 0.9;

    private Arm arm;

    public Hatch(Arm arm) {
        hatchServo = new Servo(HATCH_SERVO);
        setMode(HatchMode.WAIT);
        this.arm = arm;
    }

    public void runSubsystem() {
        if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
            hatchOpen = !hatchOpen;
            setMode(HatchTask.HatchMode.RUN);
        }

        if (getMode().equals(HatchMode.RUN)) {
            if (hatchOpen) { // close hatch, ready to pick up or drop off
                hatchServo.setPosition(-hatchPower);
                try {
                    Thread.sleep(hatchRunTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hatchServo.set(.5);
            } else { // open hatch, ready to move
                hatchServo.setPosition(hatchPower);
                try {
                    Thread.sleep(hatchRunTime + 85);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hatchServo.set(.5);

                arm.setMode(ArmMode.HATCH);
            }
        } else {
            hatchServo.setPosition(0.5);
        }

        setMode(HatchMode.WAIT);
    }
}
