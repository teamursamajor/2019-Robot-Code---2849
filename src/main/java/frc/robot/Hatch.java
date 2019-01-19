package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Hatch implements Runnable, UrsaRobot {
    public static boolean running = false;
    private static Object lock = new Object();

    private Spark hatchMotor;

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        startHatch();
    }

    private void startHatch() {
        synchronized (lock) {
            if (running)
                return;
            running = true;
        }
        // Number of degrees per pulse (7 pulses in one revolution)
        hatchEncoder.setDistancePerPulse(DEGREES_PER_TICK);
        new Thread(this, "hatchThread").start();
    }

    public void run() {
        while (running) {
            //TODO get input from teleop handler
            if (xbox.getButton(XboxController.BUTTON_A)) { // Goes up
                hatchMotor.set(0.40);
            } else if (xbox.getButton(XboxController.BUTTON_B)) { // Goes down
                hatchMotor.set(-0.20);
            } else {
                hatchMotor.set(0.0);
            }
        }
        System.out.println(hatchEncoder.get());
    }
}