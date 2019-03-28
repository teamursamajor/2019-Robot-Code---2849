package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

// TODO are we using this climb design? if not, remove?
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

    private double camPerpendicularVoltage = 0.0;
    private double frontArmMaxDistance = 0.0;
    private double distanceTolerance = 5.0;

    // placeholder values
    private final long initialFrontClimbTime = 500; // front moves, back is stopped
    private final long maxFrontClimbTime = 5000; // max time front can move forward for
    private final long maxBackClimbTime = 2000; // max time back can move forward for
    private final long driveTime = 1000; // how long the robot drives forward after climbing up

    public Climb() {
        climbFrontMotor = new Spark(CLIMB_FRONT);
        climbBackMotor = new Spark(CLIMB_BACK);
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

            // if(frontArmMaxDistance - climbEncoder.getDistance() <= distanceTolerance || (System.currentTimeMillis() - frontStart) > maxFrontClimbTime) {
            //     climbFrontMotor.set(0.0);
            // }
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
        try {
            Thread.sleep(20);
        } catch (Exception e){
            e.printStackTrace();
        }
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