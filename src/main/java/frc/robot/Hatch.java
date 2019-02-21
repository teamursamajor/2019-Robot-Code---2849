package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.HatchTask;
import frc.tasks.HatchTask.HatchMode;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    private Servo hatchServo;
    private boolean servoUp = true;
    private long maxRunTime = 1050; // how long the wheel spins

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        hatchServo = new Servo(HATCH_SERVO);
        subsystemMode = HatchMode.WAIT;
    }

    public void runSubsystem() {
        if (xbox.getSingleButtonPress(controls.map.get("hatch_run"))) { // runs hatch and flips servo
            subsystemMode = HatchMode.RUN;
            // // this does the same thing as servoUp = !servoUp
            // servoUp = servoUp ? false : true;
        } else if (xbox.getSingleButtonPress(controls.map.get("hatch_wait"))) { // flips servo, does not run hatch
            subsystemMode = HatchMode.WAIT;
        }

        HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop();

        if(subsystemMode.equals(HatchMode.RUN)){
            long startTime = System.currentTimeMillis();
            if(servoUp) { // this means the servo was originally up and we are dropping off
                try {
                    hatchMotor.set(hatchOrder.hatchPower); // goes out
                    Thread.sleep(maxRunTime); // wait
                    hatchServo.setAngle(180); // servo down
                    servoUp = false;
                    hatchMotor.set(-hatchOrder.hatchPower); // goes in
                    Thread.sleep(maxRunTime); // wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else { // this means the servo was originally down and we are picking up
                hatchMotor.set(hatchOrder.hatchPower);
                while((hatchServo.getAngle() < 90) && (System.currentTimeMillis() - startTime) < maxRunTime){
                    try {
                        Thread.sleep(10);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                long currentRunTime = System.currentTimeMillis() - startTime;
                hatchServo.setAngle(90);
                servoUp = true;
                hatchMotor.set(-hatchOrder.hatchPower);
                try {
                    Thread.sleep(currentRunTime);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            hatchMotor.set(0.0);
        } else{
            hatchMotor.set(0.0);
        }
        subsystemMode = HatchMode.WAIT;
    }

}