package frc.robot;

public class DriveLoops implements UrsaRobot {

    /*
     * An enum is a list of predefined constants. Ex: Directions - North South East
     * West. In this case we have two Drive Modes, Auto and DriveSticks, to
     * represent Autonomous and Teleop
     */
    public enum DriveMode {
        Auto, DriveSticks;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @param driveState Information about the current power, velocity, and position
         * @param xbox       Instance of the xbox controller
         * @return DriveOrder containing the left and right powers
         */

        public DriveOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case Auto:
                return autoCalculator();
            case DriveSticks:
                return sticksBox();
            }
            return new DriveOrder(0.0, 0.0);
        }

        /**
         * Iterates the Auto control loop and calculates the new powers for Drive
         * 
         * @param driveState Current information on the robot: power, velocity, and position
         * @return A DriveOrder object containing the new left and right powers
         */
        private DriveOrder autoCalculator() {
            //TODO move to a constants java file which communicates with the dashboard/UrsaRobot
            double kdAutoAlign = 2;
            double kpAutoAlign = 1.0 / 33.0;
            double autoAlignTolerance = 0.1;
            double autoAlignMinimumPower = 0.25;

            // alignment code / control loop

            double centerPos = (DriveState.leftPos + DriveState.rightPos) / 2.0;
            if (Math.abs(centerPos) < autoAlignTolerance) {
                return new DriveOrder(0.0, 0.0);
            }


            // Finding Rate of change in kd
            double leftOutputPower = kpAutoAlign * DriveState.leftPos + kdAutoAlign * DriveState.leftVelocity;
            double rightOutputPower = kpAutoAlign * DriveState.rightPos + kdAutoAlign * DriveState.rightVelocity;

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
         * @param driveState Current information on the robot: power, velocity, and position
         * @param xbox       Instance of the xbox controller
         * @return DriveOrder containing the values from the XboxController
         */
        private DriveOrder sticksBox() {
            return new DriveOrder(xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y),
                    xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));
        }
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    static class DriveState {
        static double leftPower = 0.0, rightPower = 0.0, leftVelocity = 0.0, rightVelocity = 0.0, leftPos = 0.0, rightPos = 0.0;

        static long stateTime = System.currentTimeMillis();
        public static void updateState(double leftPower, double rightPower, double leftVelocity, double rightVelocity, double leftPos, double rightPos) {
            DriveLoops.DriveState.leftPower = leftPower;
            DriveLoops.DriveState.rightPower = rightPower;
            DriveLoops.DriveState.leftVelocity = leftVelocity;
            DriveLoops.DriveState.rightVelocity = rightVelocity;
            DriveLoops.DriveState.leftPos = leftPos;
            DriveLoops.DriveState.rightPos = rightPos;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveLoops and holds values for the new left and
     * right powers to be set by Drive
     */
    static class DriveOrder {
        double leftPower = 0.0, rightPower = 0.0;

        public DriveOrder(double leftPower, double rightPower) {
            this.leftPower = leftPower;
            this.rightPower = rightPower;
        }
    }
}