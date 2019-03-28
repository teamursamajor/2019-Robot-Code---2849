package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import frc.tasks.CargoTask;

public class Hatch implements Runnable, UrsaRobot {

    public static Servo hatchServo;
    private boolean hatchOpen = true;

    private long hatchRunTime = 1000;

    private Cargo cargo;

    private Thread t;

    public enum HatchMode {
        WAIT, RUN
    }

    public static HatchMode hatchMode = HatchMode.WAIT;

    public Hatch(Cargo cargo) {
        hatchServo = new Servo(HATCH_SERVO);
        this.cargo = cargo;
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
                    hatchServo.setPosition(.75);
                    try {
                        Thread.sleep(hatchRunTime + 750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hatchServo.set(.5);
                } else { // open hatch, ready to move
                    hatchServo.setPosition(-.75);
                    try {
                        Thread.sleep(hatchRunTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hatchServo.set(.5);

                    try { // TODO if everything breaks comment this out
                        cargo.setMode(CargoTask.CargoMode.HATCH);
                    } catch(Exception e){
                        e.printStackTrace();
                    }

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
}