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
        AUTO, ALIGN, DRIVE_STICKS, TURN, PATH;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public DriveOrder callLoop() {
            // "this" refers to the subsystemMode that is calling this method
            switch (this) {
            case AUTO:
                return autoCalculator();
            case ALIGN:
                return autoAlign();
            case DRIVE_STICKS:
                return sticksBox();
            case TURN:
                return turnTo();
            case PATH:
                // return pathIterate(pathReader);
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
            // TODO test driveTolerance, kpDrive, and kdDrive
            double leftOutputPower = 0.0, rightOutputPower = 0.0;
            double currentDistance = DriveState.averagePos;
            double driveTolerance = 4.0;

            double kdDrive = 2; // Derivative coefficient for PID controller
            double kpDrive = 1.0 / 33.0; // Proportional coefficient for PID controller
            double minimumPower = 0.25;

            // If we are within the driveTolerance, stop
            if (Math.abs(currentDistance - desiredLocation) <= driveTolerance) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            // TODO someone double check this
            // If moving forward, everything is like normal
            if (direction > 0) {
                leftOutputPower = kpDrive * (desiredLocation - DriveState.leftPos) + kdDrive * DriveState.leftVelocity;
                rightOutputPower = kpDrive * (desiredLocation - DriveState.rightPos)
                        + kdDrive * DriveState.rightVelocity;
            }
            // If moving backwards, find our error term minus velocity term
            else if (direction < 0) {
                leftOutputPower = kpDrive * (DriveState.leftPos - desiredLocation)
                        + kdDrive * (-1 * DriveState.leftVelocity);
                // Cant remember why I did this, but I think it might be necessary. Test?
                leftOutputPower *= -1.0;
                rightOutputPower = kpDrive * (DriveState.rightPos - desiredLocation)
                        + kdDrive * (-1 * DriveState.rightVelocity);
                // Cant remember why I did this, but I think it might be necessary. Test?
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

            double goalPosition = 0.0; // on the limelight, 0.0 is the center

            // Loop through pairs of tape
            int hatchCount = 0; // actual number of hatches
            int count = 0; // general counter variable
            int tapePairPresent;
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
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            // PD equations power = kp * change in distance (aka error) + kd * velocity
            double leftOutputPower = kpAutoAlign * (limelightTable.getEntry("tx").getDouble(Double.NaN) - goalPosition)
                    + kdAutoAlign * DriveState.leftVelocity;
            double rightOutputPower = kpAutoAlign * (limelightTable.getEntry("tx").getDouble(Double.NaN) - goalPosition)
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
            // Arcade Drive
            // TODO test using squared axis and see how it feels. Might be better for small
            // movements
            double leftStickY = xbox.getSquaredAxis(XboxController.AXIS_LEFTSTICK_Y);
            double rightStickX = xbox.getSquaredAxis(XboxController.AXIS_RIGHTSTICK_X);
            // double leftStickY = xbox.getAxis(controls.map.get("drive_straight")) * (0.75);
            // double rightStickX = -xbox.getAxis(controls.map.get("drive_turn")) * (0.75);
            double leftSpeed = leftStickY + rightStickX;
            double rightSpeed = leftStickY - rightStickX;
            double max = Math.max(leftSpeed, rightSpeed);
            double min = Math.min(leftSpeed, rightSpeed);
            if (max > 1) {
                leftSpeed /= max;
                rightSpeed /= max;
            } else if (min < -1) {
                leftSpeed /= -min;
                rightSpeed /= -min;
            }
            return new DriveOrder(leftSpeed, rightSpeed);
        }

        private DriveOrder turnTo() {
            double newAngle = desiredAngle - DriveState.currentHeading;
            double angleTolerance = 5; // TODO set experimentally
            if (newAngle < angleTolerance) {
                driving = false;
                return new DriveOrder(0.0, 0.0);
            }

            if (newAngle < 0 && Math.abs(newAngle) > 180)
                newAngle += 360;

            // TODO PD Loop
            double turningKp = 1.0 / 40.0;
            double turningKd = 0.0;

            double velocity = (DriveState.leftVelocity > 0) ? DriveState.leftVelocity : DriveState.rightVelocity;

            double outputPower = turningKp * newAngle + turningKd * (velocity / UrsaRobot.robotRadius);

            return new DriveOrder(1 * (Math.signum(newAngle) * outputPower),
                    -1 * (Math.signum(newAngle)) * outputPower);
        }

        // TODO Add Chris's code when ready
        // private DriveOrder pathIterate(String pathName) {
        //     return null;
        // }
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class DriveState {
        public static double leftVelocity = 0.0, rightVelocity = 0.0, leftPos = 0.0, rightPos = 0.0;
        public static double averagePos = 0.0, currentHeading = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double leftVelocity, double rightVelocity, double leftPos, double rightPos,
                double currentHeading) {
            DriveState.leftVelocity = leftVelocity;
            DriveState.rightVelocity = rightVelocity;
            DriveState.leftPos = leftPos;
            DriveState.rightPos = rightPos;
            DriveState.averagePos = (leftPos + rightPos) / 2.0;
            DriveState.currentHeading = currentHeading;
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
    private static int matchPairs = 0;
    // private static PathReader;

    /**
     * 
     * Used for turning or aligning
     * 
     * @param argument  The desired angle to turn to or the number of times to check
     *                  for tape pairs
     * @param drive     Instance of the drive object
     * @param otherMode Should be TURN or ALIGN
     */
    public DriveTask(double argument, Drive drive, DriveMode otherMode) {
        switch (otherMode) {
        case TURN:
            desiredAngle = argument;
            driving = true;
            drive.setMode(DriveMode.TURN);
            Thread turnThread = new Thread("TurnTask");
            turnThread.start();
            break;
        case ALIGN:
            matchPairs = (int) argument;
            driving = true;
            drive.setMode(DriveMode.ALIGN);
            Thread alignThread = new Thread("AlignTask");
            alignThread.start();
            break;
        default:
            System.out.println(
                    "This constructor is being used incorrectly to drive the robot. It should be used only for turning or aligning.");
            break;
        }
    }

    // TODO this when it's ready

    /**
     * Used for following paths
     * 
     * @param pathName The name of the path file to follow
     * @param drive    Instance of the drive object
     */
    // public DriveTask(PathReader pathReader, Drive drive) {
    // this.pathReader = pathReader;
    // driving = true;
    // drive.setMode(DriveMode.PATH);
    // Thread pathThread = new Thread("PathTask");
    // pathThread.start();
    // }

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