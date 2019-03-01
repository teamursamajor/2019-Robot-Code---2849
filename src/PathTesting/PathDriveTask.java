package frc.tasks;

import PathTesting.RobotTester;
import frc.robot.XboxController;
import PathTesting.Drive;

public class PathDriveTask extends Task implements UrsaRobot {

    /*
     * An enum is a list of predefined constants. Ex: Directions - North South East
     * West. In this case we have two Drive Modes, Auto and DriveSticks, to
     * represent Autonomous and Teleop
     */
    public enum DriveMode {
        PATH;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public DriveOrder callLoop() {
            switch (this) {
                case PATH:
                // TODO add path integration here. Needs to return a DriveOrder object with left and right power.
            }
            return new DriveOrder(0.0, 0.0);
        }

        /**
         * Iterates the regular auto control loop and calculates the new powers for
         * Drive
         * 
         * @return A DriveOrder object containing the new left and right powers
         */
        private DriveOrder driveBy(double desiredLocation) {
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

        private DriveOrder turnTo(double desiredAngle) {
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

            double outputPower = turningKp * newAngle + turningKd * (velocity / UrsaRobot.robotRadius);

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

    private static double startDistance = 0.0, direction = 1.0;
    private static boolean driving = true;

    /**
     * Used for driving
     * 
     * @param desiredDistance How far you want to drive. Positive is forward,
     *                        negative is backwards
     * @param drive           Instance of drive object
     */
    public PathDriveTask(double desiredDistance, Drive drive) {
        direction = Math.signum(desiredLocation); // Moving Forwards: 1, Moving Backwards: -1
        startDistance = DriveState.averagePos;
        desiredLocation = startDistance + desiredDistance;

        driving = true;
        drive.setMode(DriveMode.PATH);
        Thread t = new Thread("PathTask");
        t.start();
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