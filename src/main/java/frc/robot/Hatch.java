package frc.robot;

import edu.wpi.first.wpilibj.Servo;

public class Hatch implements UrsaRobot, Runnable {

    Thread t;

    public static Servo hatchServo;
    private boolean hatchOpen = true;
    private double extendAngle = 280.0;

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
                if (hatchOpen) {
                    // openHatch();
                    hatchServo.setAngle(0.0); // closes hatch to drop off
                } else {
                    // closeHatch();
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

    public void openHatch(){
        hatchServo.setSpeed(0.5);
        try{
            Thread.sleep(500);
        } catch(Exception e){
            e.printStackTrace();
        }
        hatchServo.setSpeed(0.0);
    }

    public void closeHatch(){
        hatchServo.setSpeed(-0.5);
        try{
            Thread.sleep(500);
        } catch(Exception e){
            e.printStackTrace();
        }
        hatchServo.setSpeed(0.0);
    }
}