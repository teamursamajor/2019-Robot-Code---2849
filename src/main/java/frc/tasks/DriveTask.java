package frc.tasks;

import frc.robot.UrsaRobot;
import frc.robot.XboxController;
import frc.robot.Drive;

public class DriveTask extends Task implements UrsaRobot {

    /*
     * An enum is a list of predefined constants. Ex: Directions - North South East
     * West. In this case we have two Drive Modes, Auto and DriveSticks, to
     * represent Autonomous and Teleop
     */
    public enum DriveMode {
        AUTO, AUTO_ALIGN, DRIVE_STICKS, TURN_TO;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public DriveOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case AUTO:
                return autoCalculator();
            case AUTO_ALIGN:
                return autoAlign();
            case DRIVE_STICKS:
                return sticksBox();
            case TURN_TO:
                return turnTo();
            }
            return new DriveOrder(0.0, 0.0);
        }

        /**
         * Iterates the regular auto control loop and calculates the new powers for
         * Drive
         * 
         * @return A DriveOrder object containing the new left and right powers
         */
        private DriveOrder autoCalculator() {
            double leftOutputPower = 0.0, rightOutputPower = 0.0;
            double currentDistance = DriveState.averagePos;
            double driveTolerance = 0.0; // TODO set a real value here

            double kdDrive = 2; // Derivative coefficient for PID controller
            double kpDrive = 1.0 / 33.0; // Proportional coefficient for PID controller
            double minimumPower = 0.25;

            // If we are within the driveTolerance, stop
            if (Math.abs(currentDistance - desiredLocation) <= driveTolerance) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            // TODO someone double check this
            if (direction > 0) {
                leftOutputPower = kpDrive * (desiredLocation - DriveState.leftPos) + kdDrive * DriveState.leftVelocity;
                rightOutputPower = kpDrive * (desiredLocation - DriveState.rightPos)
                        + kdDrive * DriveState.rightVelocity;
            } else if (direction < 0) {
                leftOutputPower = kpDrive * (DriveState.leftPos - desiredLocation)
                        + kdDrive * (-1 * DriveState.leftVelocity);
                leftOutputPower *= -1.0;
                rightOutputPower = kpDrive * (DriveState.rightPos - desiredLocation)
                        + kdDrive * (-1 * DriveState.rightVelocity);
                rightOutputPower *= -1.0;
            }

            if (leftOutputPower == 0 && rightOutputPower == 0) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            if (Math.abs(leftOutputPower) < minimumPower) {
                leftOutputPower = Math.signum(leftOutputPower) * minimumPower;
            }

            if (Math.abs(rightOutputPower) < minimumPower) {
                rightOutputPower = Math.signum(rightOutputPower) * minimumPower;
            }

            return new DriveOrder(leftOutputPower, rightOutputPower);
        }

        /**
         * Iterates the AutoAlign control loop and calculates the new powers for Drive
         * 
         * @return A DriveOrder object containing the new left and right powers
         */
        private DriveOrder autoAlign() {
            // TODO move to a constants java file which communicates with the
            // dashboard/UrsaRobot

            double kdAutoAlign = 2; // Derivative coefficient for PID controller
            double kpAutoAlign = 1.0 / 33.0; // Proportional coefficient for PID controller
            double autoAlignTolerance = 0.1;
            double autoAlignMinimumPower = 0.25;

            double centerPos = (DriveState.leftPos + DriveState.rightPos) / 2.0;

            // If we're already close enough to the tapes, then simply stop
            if (Math.abs(centerPos) < autoAlignTolerance) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            double goalPosition = 0.0; // on the limelight, 0.0 is the center

            // PD equations power = kp * change in distance (aka error) + kd * velocity
            double leftOutputPower = kpAutoAlign * (DriveState.leftPos - goalPosition)
                    + kdAutoAlign * DriveState.leftVelocity;
            double rightOutputPower = kpAutoAlign * (DriveState.rightPos - goalPosition)
                    + kdAutoAlign * DriveState.rightVelocity;

            if (leftOutputPower == 0 && rightOutputPower == 0) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            if (Math.abs(leftOutputPower) < autoAlignMinimumPower) {
                leftOutputPower = Math.signum(leftOutputPower) * autoAlignMinimumPower;
            }

            if (Math.abs(rightOutputPower) < autoAlignMinimumPower) {
                rightOutputPower = Math.signum(rightOutputPower) * autoAlignMinimumPower;
            }

            return new DriveOrder(leftOutputPower, rightOutputPower);
        }

        /**
         * "Iterates" the DriveSticks control loop. This is called a Box because it just
         * takes in the DriveState and returns the Xbox controller axis values. It is
         * not actually calculating anything.
         * 
         * @return DriveOrder containing the values from the XboxController
         */
        private DriveOrder sticksBox() {
            return new DriveOrder(xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y),
                    xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));
        }

        private DriveOrder turnTo() {
            double newAngle = desiredAngle - DriveState.currentAngle;
            double angleTolerance = 5; //TODO set experimentally
            if(newAngle < angleTolerance){
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }
            if (newAngle < 0 && Math.abs(newAngle) > 180)
                newAngle += 360;

            //TODO PD Loop
            double turningKp = 1.0/40.0;
            double turningKd = 0.0;

            double velocity = (DriveState.leftVelocity > 0) ? DriveState.leftVelocity : DriveState.rightVelocity;
            double radius = 15; // temporary TODO what do we do here

            double outputPower = turningKp * newAngle + turningKd * (velocity / radius);

            return new DriveOrder(1 * (Math.signum(newAngle) * outputPower),
					-1 * (Math.signum(newAngle)) * outputPower);
        }
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class DriveState {
        public static double leftVelocity = 0.0, rightVelocity = 0.0, leftPos = 0.0, rightPos = 0.0;
        public static double averagePos = 0.0, currentAngle = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double leftVelocity, double rightVelocity, double leftPos, double rightPos, double currentAngle) {
            DriveState.leftVelocity = leftVelocity;
            DriveState.rightVelocity = rightVelocity;
            DriveState.leftPos = leftPos;
            DriveState.rightPos = rightPos;
            DriveState.averagePos = (leftPos + rightPos) / 2.0;
            DriveState.currentAngle = currentAngle;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveTask and holds values for the new left and right
     * powers to be set by Drive
     */
    public static class DriveOrder {
        public double leftPower = 0.0, rightPower = 0.0;

        public DriveOrder(double leftPower, double rightPower) {
            this.leftPower = leftPower;
            this.rightPower = rightPower;
        }
    }

    private static double desiredLocation = 0.0, startDistance = 0.0, direction = 1.0;
    private static boolean driving = true;

    /**
     * Used for driving
     * 
     * @param desiredDistance How far you want to drive. Positive is forward,
     *                        negative is backwards
     * @param drive           Instance of drive object
     */
    public DriveTask(double desiredDistance, Drive drive) {
        direction = Math.signum(desiredLocation); // Moving Forwards: 1, Moving Backwards: -1
        startDistance = DriveState.averagePos;
        desiredLocation = startDistance + desiredDistance;

        driving = true;
        drive.setMode(DriveMode.AUTO); // TODO does this work?
        Thread t = new Thread("DriveTask");
        t.start();
    }

    private static double desiredAngle = 0.0;

    /**
     * Used for turning
     * 
     * @param desiredAngle    The desiredAngle to turn to or turn by
     * @param drive    Instance of the drive object
     * @param turnMode Should be either TURN_TO
     */
    public DriveTask(double desiredAngle, Drive drive, DriveMode turnMode) {
        if (turnMode.equals(DriveMode.TURN_TO)) {
            this.desiredAngle = desiredAngle;
            driving = true;
            drive.setMode(turnMode);
            Thread t = new Thread("TurnTask");
            t.start();
        } else {
            System.out.println(
                    "This constructor is being used incorrectly to drive the robot. It should be used only for turning.");
        }

    }

    public void run() {
        while (driving) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}