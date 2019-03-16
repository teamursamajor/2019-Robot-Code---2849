package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Hatch implements UrsaRobot, Runnable {

    Thread t;

    public static Servo hatchServo;
    private boolean hatchOpen = true;
    // TODO try reducing this angle and switching the smart servo to continuous mode
    private double extendAngle = 360.0;

    public Hatch() {
        hatchServo = new Servo(HATCH_SERVO);
    }

    public void hatchInit() {
        t = new Thread(this, "Hatch Thread");
        t.start();
    }

    public void run() {
        while (true) {
            if(xbox.getSingleButtonPress(XboxController.BUTTON_A)){
                if (hatchOpen) {
                    hatchServo.setAngle(0.0); // closes hatch to drop off
                } else {
                    hatchServo.setAngle(extendAngle); // opens hatch to pick up
                }
                hatchOpen = !hatchOpen;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}