package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Hatch extends Subsystem implements UrsaRobot {

    private Spark hatchMotor;
    private long time;

    public Hatch() {
        hatchMotor = new Spark(HATCH);
    }

    private long runTime = 1050; // how long the wheel spins
    private double power = -0.25;
    public void runSubsystem() {
        if(xbox.getSingleButtonPress(XboxController.BUTTON_A)){
            hatchMotor.set(power);
            try{
                Thread.sleep(runTime);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            hatchMotor.set(0.0);
            power *= -1; // flips power so next time, it comes in
        }
    }
}