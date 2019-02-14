package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants implements Runnable {
    // TODO finish this
    // String squirrelsAreAwesome = "This is a fact";
    int i = 50;
    // SmartDashboard.putData("Sicko Squirrel Test", new Squirrel());

    public static boolean running = false;
    private static Object lock = new Object();

    public double hatchPower;
    public double cargoPower;
    public double climbPower;
    public double susanPower;
    //TODO ... other constants here

    public Constants() {
        hatchPower = 0.0;
        SmartDashboard.putNumber("Hatch Power", 0.0);
        cargoPower = 0.0;
        SmartDashboard.putNumber("Cargo Power", 0.0);
        climbPower = 0.0;
        SmartDashboard.putNumber("Climb Power", 0.0);
        susanPower = 0.0;
        SmartDashboard.putNumber("Susan Power", 0.0);
        startConstants();
    }

    public void startConstants() {
        // Used to prevent ("lock") a thread from starting again if constructor is run
        // again
        synchronized (lock) {
            if (running)
                return;
            running = true;
        }
        new Thread(this, "Constants").start();
    }

    public void run() {
        while (running) {
            hatchPower = SmartDashboard.getNumber("Hatch Power", 0.0);
            climbPower = SmartDashboard.getNumber("Climb Power", 0.0);
            susanPower = SmartDashboard.getNumber("Susan Power", 0.0);
            cargoPower = SmartDashboard.getNumber("Cargo Power", 0.0);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // SmartDashboard.putData()
    public void printTest() {
        System.out.println("SMART DASHBOARD: ");
    }

    public void dashTest() {
        String squirrel = "HIIIII";
        SmartDashboard.putString("Squirrel Test", squirrel);
        // SmartDashboard.putData("Function", );
        SmartDashboard.putNumber("HatchPower", .5);
        System.out.println(hatchPower);
        hatchPower = SmartDashboard.getNumber("HatchPower", 0);
        System.out.println(hatchPower);
    }
}