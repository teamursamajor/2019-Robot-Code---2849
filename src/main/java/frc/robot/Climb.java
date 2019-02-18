package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb implements UrsaRobot {

    private Spark climbFrontMotor;
    private Spark climbBackMotor;
    private boolean climbStop;
    private boolean climbIsRunning;

    // in ms
    private static final int frontClimbTimeInit = 1000;
    private static final int frontClimbTimeEnd = 1000;
    private static final int backClimbTime = 1500;

    public Climb() {
        climbFrontMotor = new Spark(CLIMB_FRONT);
        climbBackMotor = new Spark(CLIMB_BACK);
    }

    public void climbInit() {
        climbStop = false;
        climbIsRunning = true;
        Thread t = new Thread(new Runnable() {
            public void run() {
                climb();
            }
        });

        t.start();
    }

    private void climb() {
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