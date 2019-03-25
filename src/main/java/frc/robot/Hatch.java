package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Hatch implements Runnable, UrsaRobot {

    public static Servo hatchServo;
    private boolean hatchOpen = true;

    private double defaultAngle = 0.0;
    private double extendAngle = 300.0;

    private long hatchRunTime = 1000;

    private Thread t;

    public enum HatchMode {
        WAIT, RUN
    }

    public static HatchMode hatchMode = HatchMode.WAIT;

    public Hatch() {
        hatchServo = new Servo(HATCH_SERVO);
    }

    public void hatchInit() {
        t = new Thread(this, "Hatch Thread");
        t.start();
    }

    public void run() {
        while (true) {
            if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
                hatchOpen = !hatchOpen;
                hatchMode = HatchMode.RUN;
            }

            if (hatchMode.equals(HatchMode.RUN)) {
                if (hatchOpen) { // close hatch, ready to pick up or drop off
                    runHatch(0.75);
                } else { // open hatch, ready to move
                    runHatch(-0.75);
                }
            } else {
                hatchServo.setPosition(0.5);
            }

            hatchMode = HatchMode.WAIT;

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("DID NOT SLEEP");
            }
        }
    }

    public void runHatch(double power) {
        hatchServo.setPosition(power);
        try {
            Thread.sleep(hatchRunTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hatchServo.set(.5);
    }
}