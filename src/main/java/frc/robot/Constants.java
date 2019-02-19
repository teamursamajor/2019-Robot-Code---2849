package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants implements Runnable {
    // SmartDashboard.putData("Sicko Squirrel Test", new Squirrel());

    public static boolean running = false;
    private static Object lock = new Object();

    public static double hatchPower;
    public static double cargoPowerUp;
    public static double cargoPowerDown;
    public static double cargoIntakePower;
    public static double cargoOuttakePower;
    public static double climbPower;
    public static double susanPower;
    //TODO ... other constants here

    public Constants() {
        hatchPower = 0.25;
        SmartDashboard.putNumber("Hatch Power", 0.5);
        cargoPowerUp = 1.0;
        cargoPowerDown = -0.10;
        SmartDashboard.putNumber("Cargo Power", 0.35);
        cargoIntakePower = 0.5;
        SmartDashboard.putNumber("Cargo Intake Power", 0.5);
        cargoOuttakePower = 1;
        SmartDashboard.putNumber("Cargo Outtake Power", 1);
        climbPower = 0.90;
        SmartDashboard.putNumber("Climb Power", 1);
        susanPower = 0.25;
        SmartDashboard.putNumber("Susan Power", 0.25);
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
            cargoIntakePower = SmartDashboard.getNumber("Cargo Intake Power", 0.0);
            susanPower = SmartDashboard.getNumber("Susan Power", 0.0);
            //TODO add cargoPowerDown
            cargoPowerUp = SmartDashboard.getNumber("Cargo Power", 0.0);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //printTest();
        }
    }

    // SmartDashboard.putData()
    public void printTest() {
        System.out.println("SMART DASHBOARD: ");
        System.out.println("Cargo Power" + cargoPowerUp);
        System.out.println("Cargo Intake Power" + cargoIntakePower);
        System.out.println("Cargo Outtake Power" + cargoOuttakePower);
        System.out.println("Hatch Power" + hatchPower);
        System.out.println("Climb Power" + climbPower);
        System.out.println("Susan Power" + susanPower);
    }
}