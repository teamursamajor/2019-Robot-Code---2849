package frc.tasks;

public class ClimbTask extends Task {

    public enum ClimbMode {
        Grounded, Top;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public ClimbOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case Grounded:
                return autoGoToAngle(0);
            case Top:
                return autoGoToAngle(-90.0);
            }
            return new ClimbOrder(0.0);
        }

        private ClimbOrder autoGoToAngle(double angle) {
            System.out.println("Test print");
            return new ClimbOrder(0.0);
        }
    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class ClimbState {
        public static double power = 0.0, angle = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double power, double angle) {
            ClimbState.power = power;
            ClimbState.angle = angle;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveTask and holds values for the new left and right
     * powers to be set by Drive
     */
    public static class ClimbOrder {
        public double power;

        public ClimbOrder(double power) {
            this.power = power;
        }
    }

    public ClimbTask() {
        super();
    }

    public void run() {

    }
    // TODO fill this out
}