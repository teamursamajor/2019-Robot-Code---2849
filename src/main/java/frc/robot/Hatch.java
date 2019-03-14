package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Hatch implements UrsaRobot, Runnable {

    public static Servo hatchServo;
    private boolean hatchOpen = true;
    private double extendAngle = 280.0;

    public Hatch() {
        hatchServo = new Servo(HATCH_SERVO);
    }

    public void hatchInit() {
        Thread t = new Thread("Hatch Thread");
        t.start();
    }

    public void run() {
        while (true) {
            // if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) { // runs hatch and flips servo
            //     hatchServo.setAngle(extendAngle);
            // } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) { // flips servo, does not run hatch
            //     hatchServo.setAngle(0.0);
            // }

            if(xbox.getSingleButtonPress(controls.map.get("hatch"))){
                if (hatchOpen) {
                    hatchServo.setAngle(0.0); // closes hatch to drop off
                } else {
                    hatchServo.setAngle(extendAngle); // opens hatch to pick up
                }
                hatchOpen = !hatchOpen;
            }
        }
    }
}