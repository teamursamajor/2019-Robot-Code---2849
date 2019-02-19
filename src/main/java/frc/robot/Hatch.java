package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Hatch implements Runnable, UrsaRobot {

    private Spark hatchMotor;
    private Thread hatchThread = null;
    private long runTime = 1050; // how long the wheel spins
    private double power = -0.25; // negative moves out, positive moves in

    public Hatch() {
        hatchMotor = new Spark(HATCH);
    }

    public void hatchInit() {
        hatchThread = new Thread(new Hatch(), "Hatch Thread");
        hatchThread.start();
    }

    public void run() {
        while (true) {
            if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
                hatchMotor.set(power);
                try {
                    Thread.sleep(runTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hatchMotor.set(0.0);
            } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) {
                hatchMotor.set(-power);
                try {
                    Thread.sleep(runTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}