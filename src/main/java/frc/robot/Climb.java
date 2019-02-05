package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Climb implements UrsaRobot {

    private Spark climbFrontMotor;
    private Spark climbBackMotor;

    public Climb() {
        climbFrontMotor = new Spark(CLIMB_FRONT);
        climbBackMotor = new Spark(CLIMB_BACK);
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