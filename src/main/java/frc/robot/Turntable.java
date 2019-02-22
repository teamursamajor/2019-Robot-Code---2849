package frc.robot;

import frc.tasks.*;
import frc.tasks.TurntableTask.TurntableMode;
import frc.tasks.TurntableTask.TurntableOrder;
import frc.tasks.TurntableTask.TurntableState;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Turntable extends Subsystem<TurntableTask.TurntableMode> implements UrsaRobot {

    private Spark turntableMotor;
    private Potentiometer turntablePot;
    private boolean targetFound;
    private int defaultPipeline = 1;

    public Turntable() {
        turntableMotor = new Spark(TURNTABLE);
        turntablePot = new AnalogPotentiometer(2, 360, 0);
        subsystemMode = TurntableMode.CUSTOM;
        targetFound = false;
    }

    public void runSubsystem() {
        // updateStateInfo();
        // TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        // turntableMotor.set(turntableOrder.power);


        // TODO test code
        if (xbox.getAxisGreaterThan(controls.map.get("turntable_left"), 0.1)) {
            turntableMotor.set(-.4);
        } else if (xbox.getAxisGreaterThan(controls.map.get("turntable_right"), 0.1)) {
            turntableMotor.set(0.4);
        }
        // else if (xbox.getButton(XboxController.BUTTON_LEFTSTICK)){
        //     //Auto mode
        //     NetworkTableEntry entry = UrsaRobot.limelightTable.getEntry("pipeline");
        
        //     if (entry.getDouble(UrsaRobot.defaultCameraPipeline) != UrsaRobot.visionCameraPipeline);
        //     //check auto pipe line in the lime light, enable if need be
        // //see if there are targets in view, if not set motors to zero
        //     //ALSO check to make sure we won't hit the eboard
        //     //
        //     //see if we need to move left or right
        //     //
        // }
        else {
            turntableMotor.set(0.0);
        }
    }

    // TODO shouldn't this all be in TurntableTask instead? @20XX

    private static boolean turning = true;

    private TurntableOrder autoAlign(int matchPairs) {
        // TODO move to a constants java file which communicates with the dashboard/UrsaRobot

        double kdAutoAlign = 2; // Derivative coefficient for PID controller
        double kpAutoAlign = 1.0 / 33.0; // Proportional coefficient for PID controller
        double autoAlignTolerance = 0.1;
        double autoAlignMinimumPower = 0.25;

        double goalPosition = 0.0; // on the limelight, 0.0 is the center

        // Loop through pairs of tape
        int hatchCount = 0; // actual number of hatches
        int count = 0; // general counter variable
        int tapePairPresent;

        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");
        NetworkTableEntry ta = table.getEntry("ta");
        //read values periodically
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        // //post to smart dashboard periodically
        // SmartDashboard.putNumber("LimelightX", x);
        // SmartDashboard.putNumber("LimelightY", y);
        // SmartDashboard.putNumber("LimelightArea", area);e

        while (hatchCount < matchPairs) {
            // Count the number of valid tape pairs we've encountered
            tapePairPresent = (int) limelightTable.getEntry("tv").getDouble(0);
            if (tapePairPresent == 1)
                count++;
            if (count % 2 == 1)
                hatchCount++; // skips "even" pairs to avoid false positives
            System.out.println("Count: " + count);
            System.out.println("Hatch Count: " + hatchCount);
            // Wait before trying to match a pair of tape again
            try {
                Thread.sleep(1000); // TODO adjust if necessary
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // If we're already close enough to the tapes, then simply stop
        double centerPos = limelightTable.getEntry("tx").getDouble(Double.NaN);
        if (Math.abs(centerPos) < autoAlignTolerance) {
            turning = false;
            return new TurntableOrder(0.0);
        }

        // PD equations power = kp * change in distance (aka error) + kd * velocity
        double outputPower = kpAutoAlign * (limelightTable.getEntry("tx").getDouble(Double.NaN) - goalPosition) + kdAutoAlign * TurntableState.velocity;

        if (outputPower == 0) {
            turning = false;
        }

        if (Math.abs(outputPower) < autoAlignMinimumPower) {
            outputPower = Math.signum(outputPower) * autoAlignMinimumPower;
        }

        return new TurntableOrder(outputPower);

    }

    public void updateStateInfo(){
        // TODO update to use voltage
        // TODO use potentiometer
        double currentVoltage = turntablePot.get();
        double deltaVoltage = currentVoltage - TurntableTask.TurntableState.voltage;
        double deltaTime = System.currentTimeMillis() - TurntableTask.TurntableState.stateTime;
        double velocity = deltaVoltage / deltaTime;
        if(Math.abs(deltaVoltage) <= 5 || deltaTime <= 5)
            return;
        TurntableTask.TurntableState.updateState(currentVoltage, velocity);
    }
}