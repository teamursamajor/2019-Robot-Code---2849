package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Hatch implements Runnable, UrsaRobot {

    public static Servo hatchServo;
    private boolean hatchOpen = true;
    private double extendAngle = 360.0;

    private Thread t;

    public enum HatchMode {
        HOLD, OPEN
    }

    public static HatchMode hatchMode = HatchMode.HOLD;

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
                 if(hatchOpen){
                    hatchMode = HatchMode.HOLD;
                 } else {
                    hatchMode = HatchMode.OPEN;
                 }
                 hatchOpen = !hatchOpen;
            }

            if(hatchMode.equals(HatchMode.OPEN)){
                hatchServo.setAngle(0.0); // ready to pick up or drop off
            } else {
                hatchServo.setAngle(extendAngle); // ready to hold hatch and move
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("DID NOT SLEEP");
            }
        }
    }
}