package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class ClawHatch implements UrsaRobot, Runnable {

    public static Servo hatchServo;
    private boolean hatchOut = true;
    private double extendAngle = 280.0;

    public ClawHatch() {
        hatchServo = new Servo(HATCH_SERVO);
    }

    public void hatchInit() {
        Thread t = new Thread("Hatch Thread");
        t.start();
    }

    public void run() {
        while (true) {
            // if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
            // hatchServo.setAngle(extendAngle);
            // } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) {
            // hatchServo.setAngle(0.0);
            // }

            if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
                if (hatchOut) {
                    hatchServo.setAngle(0.0);
                } else {
                    hatchServo.setAngle(extendAngle);
                }
            }
        }
    }
}