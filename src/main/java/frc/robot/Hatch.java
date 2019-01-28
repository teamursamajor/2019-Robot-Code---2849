package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {
    
    /* Positions for the arm:
     * -Intaking a hatch
     * -Deploying a hatch (Rocket/Cargo Bay)
     * -Default position
     * -Carry position
     * 
     * These values are measured as angles
     * Can't be set in code
     * Do have getters for these positions
     * Static, dont change
     */

    private Spark hatchMotor;

    //Position constants
    double intakePosition = 0.0; //TODO set angle where arm intakes hatches
    double deployPosition = 0.0; //TODO set angle where arm deploys hatches
    double carryPosition = 0.0;  //TODO set angle where arm carries hatches

    double defaultPosition = 0.0; //TODO set angle that arm will default to
    double currentPosition; //Encoder?

    public Hatch() {
        super("hatchThread");
        hatchMotor = new Spark(HATCH);
        //Number of degrees per pulse (7 pulses in one revolution)
        hatchEncoder.setDistancePerPulse(DEGREES_PER_TICK);
        currentPosition = defaultPosition;
    }

    public void runSubsystem() {
        switch (getMode()) {
            case Intake:
                setAngle(intakePosition);
                break;
            case Deploy:
                setAngle(deployPosition);
                break;
            case Carry:
                setAngle(carryPosition);
                break;
            case Default:
                setAngle(defaultPosition);
                break;
            default:
                setAngle(defaultPosition);
                break;
        }
        
        if (xbox.getButton(XboxController.BUTTON_A)) { //Goes up
            hatchMotor.set(0.25);
        } else if (xbox.getButton(XboxController.BUTTON_B)) { //Goes down
            hatchMotor.set(-0.20);
        } else if (xbox.getButton(XboxController.BUTTON_X)){
            hatchMotor.set(0.10);
        } else {
            hatchMotor.set(0.0);
        }
        System.out.println(hatchEncoder.get());

    }

    public void customPos (double angle){
        angle %= 360.0;
        this.setAngle(angle);
    }

    private void setAngle (double angle){
        /* 1. Increase/Decrease motor power
         * 2. PID Loop
         * 3. Stay at position (part of PID Loop)
         */
    }

}