package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.HatchTask;
import frc.tasks.HatchTask.HatchMode;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    private long runTime = 1050; // how long the wheel spins

    public Hatch() {
        hatchMotor = new Spark(HATCH);
        subsystemMode = HatchMode.IN;
    }

    public void runSubsystem() {

        if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) {
            subsystemMode = HatchMode.OUT;
        } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) {
            subsystemMode = HatchMode.IN;
        }

        HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop();

        hatchMotor.set(hatchOrder.hatchPower);
        if(!(hatchOrder.hatchPower == 0)){
            try {
                Thread.sleep(runTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        subsystemMode = HatchMode.WAIT;
    }

}