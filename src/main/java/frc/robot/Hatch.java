package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.HatchTask;
import frc.tasks.HatchTask.HatchMode;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    private Servo hatchServo;
    private boolean servoUp = true;
    private long runTime = 1050; // how long the wheel spins

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        hatchServo = new Servo(HATCH_SERVO);
        subsystemMode = HatchMode.WAIT;
    }

    public void runSubsystem() {
        if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) { // runs hatch and flips servo
            subsystemMode = HatchMode.RUN;
            // this does the same thing as servoUp = !servoUp
            servoUp = servoUp ? false : true;
        } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) { // flips servo, does not run hatch
            subsystemMode = HatchMode.WAIT;
             // this does the same thing as servoUp = !servoUp
            servoUp = servoUp ? false : true;
        }

        HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop();

        if(subsystemMode.equals(HatchMode.RUN)){
            try {
                hatchMotor.set(hatchOrder.hatchPower); // goes out
                Thread.sleep(runTime); // wait
                hatchServo.setAngle(servoUp ? 90 : 180); // flip servo
                hatchMotor.set(-hatchOrder.hatchPower); // goes in
                Thread.sleep(runTime); // wait
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else{
            hatchMotor.set(0.0);
        }
        subsystemMode = HatchMode.WAIT;
    }

}