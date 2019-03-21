package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants implements Runnable {
    public static boolean running = false;

    public static double hatchPower;
    public static double cargoPowerUp;
    public static double cargoPowerDown;
    public static double cargoIntakePower;
    public static double cargoOuttakePower;
    public static double climbPower;
    public static double turntablePower;

    public Constants() {
        hatchPower = 0.30;
        SmartDashboard.putNumber("Hatch Power", hatchPower);
        cargoPowerUp = -.75;
        SmartDashboard.putNumber("Cargo Power Up", cargoPowerUp);
        cargoPowerDown = 0.15;
        SmartDashboard.putNumber("Cargo Power Down", cargoPowerDown);
        cargoIntakePower = 0.5;
        SmartDashboard.putNumber("Cargo Intake Power", cargoIntakePower);
        cargoOuttakePower = 1.0;
        SmartDashboard.putNumber("Cargo Outtake Power", cargoOuttakePower);
        climbPower = 0.90;
        SmartDashboard.putNumber("Climb Power", climbPower);
        turntablePower = 0.3;
        SmartDashboard.putNumber("Turntable Power", turntablePower);
        
        startConstants();
    }

    public void startConstants() {
        running = true;
        new Thread(this, "Constants").start();
    }

    public void run() {
        while (running) {
            hatchPower = SmartDashboard.getNumber("Hatch Power", 0.3);
            cargoPowerUp = SmartDashboard.getNumber("Cargo Power Up", -0.75);
            cargoPowerDown = SmartDashboard.getNumber("Cargo Power Down", 0.15);
            cargoIntakePower = SmartDashboard.getNumber("Cargo Intake Power", 0.5);
            cargoIntakePower = SmartDashboard.getNumber("Cargo Outtake Power", 1.0);
            climbPower = SmartDashboard.getNumber("Climb Power", 0.9);
            turntablePower = SmartDashboard.getNumber("Turntable Power", 0.3);
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //printTest();
        }
    }

    public void printTest() {
        System.out.println("SMART DASHBOARD: ");
        System.out.println("Hatch Power" + hatchPower);
        System.out.println("Cargo Power Up" + cargoPowerUp);
        System.out.println("Cargo Power Down" + cargoPowerUp);
        System.out.println("Cargo Intake Power" + cargoIntakePower);
        System.out.println("Cargo Outtake Power" + cargoOuttakePower);
        System.out.println("Climb Power" + climbPower);
        System.out.println("Turntable Power" + turntablePower);
    }
}