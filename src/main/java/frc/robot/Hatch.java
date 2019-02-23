package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.HatchTask;
import frc.tasks.HatchTask.HatchMode;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    private Servo hatchServo;
    private boolean servoUp = true;
    private long maxRunTime = 900; // how long the wheel spins

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        hatchServo = new Servo(HATCH_SERVO);
        subsystemMode = HatchMode.WAIT;
    }

    public void runSubsystem() {
        // TODO dropping off goes well, picking up only flips the servo, not actually run anything
        if (xbox.getSingleButtonPress(controls.map.get("hatch_run"))) { // runs hatch and flips servo
            subsystemMode = HatchMode.RUN;
        } else if (xbox.getSingleButtonPress(controls.map.get("hatch_flip"))) { // flips servo, does not run hatch
            subsystemMode = HatchMode.FLIP; // this works!
        }

        HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop();

        if (subsystemMode.equals(HatchMode.RUN)) {
            long startTime = System.currentTimeMillis();
            if (servoUp) { // servo was originally up and we are dropping off
                try {
                    hatchMotor.set(-hatchOrder.hatchPower); // goes out
                    Thread.sleep(maxRunTime);

                    hatchMotor.set(0.0);
                    servoUp = false;
                    hatchServo.setAngle(hatchServo.getAngle() - 90); // servo down
                    Thread.sleep(500);

                    hatchMotor.set(-hatchOrder.hatchPower); // comes in
                    Thread.sleep(maxRunTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else { // servo was originally down and we are picking up
                hatchMotor.set(hatchOrder.hatchPower);
                // TODO wont work, rewrite with a sensor or something
                while ((hatchServo.getAngle() <= 180 && hatchServo.getAngle() > 90)
                        && (System.currentTimeMillis() - startTime) < maxRunTime) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                long currentRunTime = System.currentTimeMillis() - startTime;
                hatchMotor.set(0.0);

                hatchServo.setAngle(hatchServo.getAngle() + 90);
                servoUp = true;

                hatchMotor.set(-hatchOrder.hatchPower);
                try {
                    Thread.sleep(currentRunTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            hatchMotor.set(0.0);
        } else if (subsystemMode.equals(HatchMode.FLIP)) {
            if (servoUp) {
                hatchServo.setAngle(hatchServo.getAngle() - 90);
                servoUp = false;
            } else {
                hatchServo.setAngle(hatchServo.getAngle() + 90);
                servoUp = true;
            }
            hatchMotor.set(0.0);

        } else {
            hatchMotor.set(0.0);
        }
        subsystemMode = HatchMode.WAIT;
    }

}