package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.HatchTask;
import frc.tasks.HatchTask.HatchMode;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    private Servo hatchServo;
    private DigitalInput bumperReleased; // Released returns true
    private boolean servoUp = true;
    private long maxRunTime = 900; // how long the wheel spins
    private long servoTime = 500; // wait time for the servo to move

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        hatchServo = new Servo(HATCH_SERVO);
        subsystemMode = HatchMode.WAIT;
        bumperReleased = new DigitalInput(UrsaRobot.BUMPER_SWITCH_CHANNEL);
    }

    public void runSubsystem() {
        if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) { // runs hatch and flips servo
            subsystemMode = HatchMode.RUN;
        } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) { // flips servo, does not run hatch
            subsystemMode = HatchMode.FLIP;
        }

        System.out.println(bumperReleased.get());

        HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop(); // returns a constant for RUN, 0.0 for FLIP/WAIT

        if (subsystemMode.equals(HatchMode.RUN)) {
            long startTime = System.currentTimeMillis();
            if (servoUp) { // Dropping off
                try {
                    hatchMotor.set(hatchOrder.hatchPower); // goes out
                    Thread.sleep(maxRunTime);

                    hatchMotor.set(0.0);
                    servoUp = false;
                    hatchServo.setAngle(hatchServo.getAngle() - 90); // servo down
                    Thread.sleep(servoTime);

                    // TODO modified
                    hatchMotor.set(-hatchOrder.hatchPower + .05); // comes in
                    Thread.sleep(maxRunTime + 200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else { // servo was originally down and we are picking up
                try {
                    hatchMotor.set(hatchOrder.hatchPower);
                    Thread.sleep(maxRunTime);

                    hatchMotor.set(0.0);
                    servoUp = true;
                    hatchServo.setAngle(hatchServo.getAngle() + 90);
                    Thread.sleep(servoTime);

                    hatchMotor.set(-hatchOrder.hatchPower);

                    while (bumperReleased.get() && System.currentTimeMillis() - startTime < maxRunTime) {
                        Thread.sleep(20);
                    }
                } catch (Exception e) {
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