package frc.robot;

public class DriveLoops {

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
         * @param driveState Information about the current velocity, power, and position
         * @param xbox       Instance of the xbox controller
         * @return DriveOrder containing the left and right powers
         */

        public DriveOrder callLoop(DriveState driveState, XboxController xbox) {
            switch (this) {
            case Auto:
                return autoCalculator(driveState);
            case DriveSticks:
                return sticksBox(driveState, xbox);
            }
            return new DriveOrder(0.0, 0.0);
        }
    }

    /**
     * Iterates the Auto control loop and calculates the new powers for Drive
     * 
     * @param driveState Current information on the robot: velocity, power, and
     *                   position
     * @return A DriveOrder object containing the new left and right powers
     */
    public static DriveOrder autoCalculator(DriveState driveState) {
        // Calculates what the motor power should be, updates DriveOrder and returns it
        // Does one interation of the control loop, returns where it wants to be
        double leftPower = 0; // temporary
        double rightPower = 0; // temporary
        return new DriveOrder(leftPower, rightPower);
    }

    /**
     * "Iterates" the DriveSticks control loop. This is called a Box because it just
     * takes in the DriveState and returns the Xbox controller axis values. It is
     * not actually calculating anything.
     * 
     * @param driveState Current information on the robot: velocity, power, and
     *                   position
     * @param xbox       Instance of the xbox controller
     * @return DriveOrder containing the values from the XboxController
     */
    public static DriveOrder sticksBox(DriveState driveState, XboxController xbox) {
        return new DriveOrder(xbox.getAxis(XboxController.AXIS_LEFTSTICK_Y),
                xbox.getAxis(XboxController.AXIS_RIGHTSTICK_Y));
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    class DriveState {
        double leftPower = 0.0, rightPower = 0.0, leftVelocity = 0.0, rightVelocity = 0.0, leftPos = 0.0,
                rightPos = 0.0;
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