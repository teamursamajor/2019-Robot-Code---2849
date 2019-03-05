package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.tasks.DriveTask.DriveOrder;

public class Climb implements UrsaRobot {

    /*
     * Climb To Test:
     * Find voltage when cams are perpendicular
     * Find encoder value where wheel should not go any further
     * 
     * Measure how long each part takes
    */
    private Spark climbFrontMotor;
    private Spark climbBackMotor;

    private boolean climbStop;
    private boolean climbIsRunning;

    public static Potentiometer climbPot;
    // private Potentiometer climbPot;

    // TODO determine values
    private double camPerpendicularVoltage = 0.0;
    private double frontArmMaxDistance = 0.0;
    private double distanceTolerance = 5.0;

    // placeholder values
    private final long initialFrontClimbTime = 500; // front moves, back is stopped
    private final long maxFrontClimbTime = 5000; // max time front can move forward for
    private final long maxBackClimbTime = 2000; // max time back can move forward for
    private final long driveTime = 1000; // how long the robot drives forward after climbing up

    // old, don't use
    private static final int frontClimbTimeInit = 1000;
    private static final int frontClimbTimeEnd = 1000;
    private static final int backClimbTime = 1500;

    public Climb() {
        climbFrontMotor = new Spark(CLIMB_FRONT);
        climbBackMotor = new Spark(CLIMB_BACK);
        climbPot = new AnalogPotentiometer(CLIMB_POT_CHANNEL, 360, 0);
    }

    public void climbInit() {
        climbStop = false;
        climbIsRunning = true;
        Thread t = new Thread(new Runnable() {
            public void run() {
                sensorClimb();
            }
        });

        t.start();
    }

    private void sensorClimb(){
        // start the front motor
        long frontStart = System.currentTimeMillis();
        climbFrontMotor.set(Constants.climbPower);
        while((System.currentTimeMillis() - frontStart) < initialFrontClimbTime){
            if(climbStop) {
                stopMotors();
                return;
            }
            try{
                Thread.sleep(20);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // start back
        long backStart = System.currentTimeMillis();
        while(climbPot.get() < camPerpendicularVoltage && (System.currentTimeMillis() - backStart) < maxBackClimbTime){
            if (climbStop) {
                stopMotors();
                return;
            }

            if(frontArmMaxDistance - climbEncoder.getDistance() <= distanceTolerance || (System.currentTimeMillis() - frontStart) > maxFrontClimbTime) {
                climbFrontMotor.set(0.0);
            }
            try{
                Thread.sleep(20);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // stop
        climbFrontMotor.set(0.0);
        climbBackMotor.set(0.0);

        // drive forward
        Drive.setPower(0.4);
        try {
            Thread.sleep(driveTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Drive.stop();
        climbIsRunning = false;
        climbStop = true;
    }

    /**
     * DO NOT USE, DEPRECATED
     */
    private void timedClimb() {
        // start the front motor
        long start = System.currentTimeMillis();
        climbFrontMotor.set(Constants.climbPower);
        while (!climbStop && (System.currentTimeMillis() - start) < frontClimbTimeInit) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (climbStop) {
            stopMotors();
            return;
        }

        // front is moving, now start the back
        climbBackMotor.set(Constants.climbPower);
        while (!climbStop && (System.currentTimeMillis() - start) < frontClimbTimeEnd) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (climbStop) {
            stopMotors();
            return;
        }

        // stop the front
        climbFrontMotor.set(0);

        // but keep the back going
        while (!climbStop && (System.currentTimeMillis() - start) < backClimbTime) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        stopMotors();
        climbIsRunning = false;
    }



    public void cancelClimb() {
        climbStop = true;
    }

    public boolean isClimbing() {
        return climbIsRunning;
    }

    public void setFrontMotor(double power) {
        climbFrontMotor.set(power);
    }

    public void setBackMotor(double power) {
        climbBackMotor.set(power);
    }

    public void stopMotors() {
        climbFrontMotor.set(0.0);
        climbBackMotor.set(0.0);
    }
}


