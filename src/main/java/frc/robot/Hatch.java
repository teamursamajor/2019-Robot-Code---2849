package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import frc.tasks.HatchTask;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    Thread t;

    public static Servo hatchServo;
    private boolean hatchOpen = true;
    private double extendAngle = 280.0;
    // private boolean goingToClose = true;

    public Hatch() {
        hatchServo = new Servo(HATCH_SERVO);
    }

    // public void hatchInit() {
    //     t = new Thread(this, "Hatch Thread");
    //     t.start();
    // }

    public void runSubsystem() {
        //  while (true) {
            if(xbox.getSingleButtonPress(XboxController.BUTTON_A)){
                if (hatchOpen) {
                    // openHatch();
                    hatchServo.setAngle(0.0); // closes hatch to drop off
                } else {
                    // closeHatch();
                    hatchServo.setAngle(extendAngle); // opens hatch to pick up
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("DID NOT SLEEP");
            }
        }
    // }
}