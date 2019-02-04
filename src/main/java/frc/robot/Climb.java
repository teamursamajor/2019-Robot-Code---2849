package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;

public class Climb implements UrsaRobot {

    private Spark climbFrontMotor;
    private Spark climbBackMotor;
    private double power = 0.75;

    public Climb() {
        climbFrontMotor = new Spark(CLIMB_FRONT);
        climbBackMotor = new Spark(CLIMB_BACK);
    }

    public void fowardFrontMotor() {
        climbFrontMotor.set(power);
    }

    public void forwardBackMotor() {
        climbBackMotor.set(power);
    }

    public void backwardFrontMotor() {
        climbFrontMotor.set(-power);
    }

    public void backwardBackMotor() {
        climbBackMotor.set(-power);
    }

    public void stopMotors() {
        climbFrontMotor.set(0.0);
        climbBackMotor.set(0.0);
    }

}