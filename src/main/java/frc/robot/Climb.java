package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb implements UrsaRobot {


    private Spark climbFrontMotor;
    private Spark climbBackMotor;
    private boolean ClimbStop;
    private boolean ClimbIsRunning;
    // Climb time is in milliseconds. 
    private static final int FrontClimbTimeInit = 1000;
    private static final int FrontClimbTimeEnd = 1000;
    private static final int BackClimbTime = 1500;


    public Climb() {
        climbFrontMotor = new Spark(CLIMB_FRONT);
        climbBackMotor = new Spark(CLIMB_BACK);
    }

    public void climbInit() {
        ClimbStop = false;
        ClimbIsRunning = true;
        Thread t = new Thread(new Runnable(){

            public void run() {
                climb();
            }
            
        });    
        t.start();
    }

    private void climb() 
    {
        //start the front motor
        long start = System.currentTimeMillis();
        climbFrontMotor.set(Constants.climbPower);
        while(!ClimbStop && (System.currentTimeMillis() - start) < FrontClimbTimeInit)
        {
            try
            {
                Thread.sleep(10);
            }
            catch(Exception ex)
            {
            
            }
        }

        if(ClimbStop)
        {
            stopMotors();
            return;
        }

        //front is moving, now start the back
        climbBackMotor.set(Constants.climbPower);
        while(!ClimbStop && (System.currentTimeMillis() - start) < FrontClimbTimeEnd)
        {
            try
            {
                Thread.sleep(10);
            }
            catch(Exception ex)
            {
            
            }
        }

        if(ClimbStop)
        {
            stopMotors();
            return;
        }

        //stop the front
        climbFrontMotor.set(0);
        //but keep the back going
        while(!ClimbStop && (System.currentTimeMillis() - start) < BackClimbTime)
        {
            try
            {
                Thread.sleep(10);
            }
            catch(Exception ex)
            {
            
            }
        }

        stopMotors();
        ClimbIsRunning = false;
    }

    public void cancelClimb()
    {
        ClimbStop = true;
    }

    public boolean isClimbing()
    {
        return ClimbIsRunning;
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