package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Hatch extends Subsystem implements UrsaRobot {

    private Spark hatchMotor;
    private long time;

    public Hatch() {
        hatchMotor = new Spark(HATCH);
    }

    public void runSubsystem() {
        long runTime = 500; // how long the wheel spins
        double power = 0.1;
        
        if(xbox.getSingleButtonPress(XboxController.BUTTON_A)){
            hatchMotor.set(power);
            try{
                Thread.sleep(runTime);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            power *= -1; // flips power so next time, it comes in
        }
    }
}