package frc.tasks;

public class SusanTask extends Task {

    public enum SusanMode {
        FORWARD, LEFT, RIGHT;

        /**
         * This method takes the current drive state and iterates the control loop then
         * returns the next drive order for Drive to use
         * 
         * @return DriveOrder containing the left and right powers
         */

        public SusanOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case FORWARD:
                return autoGoToAngle(0);
            case LEFT:
                return autoGoToAngle(-90.0);
            case RIGHT: 
                return autoGoToAngle(90.0);
            }
            return new SusanOrder(0.0);
        }

        private SusanOrder autoGoToAngle(double angle) {
            // this will use the potentiometer too
            // this is our PD loop
            /* while (navx.angle != angle) {
                  check the sign of the current angle. (mathsignum?) + more code :P
             }
            
             OR 
             
             if (navx.angle != angle) {
                    Math.signum(navx.getAngle) == 1;
             }

                    //Instead of checking the sign if the target angle is less than the current angle
                    you will subtract
                    OR
                    If the target angle is more than the current angle you will add.
            */
            return new SusanOrder(0.0);
        } 
    }

    public SusanTask(){
        super();
    }

    public void run() {

    }

    /**
     * This holds information about the current state of the robot. It holds values
     * for power, velocity, and position for both the left and right side.
     */
    public static class SusanState {
        public static double velocity = 0.0, angle = 0.0, voltage = 0.0;

        public static long stateTime = System.currentTimeMillis();
        public static void updateState(double velocity, double angle, double voltage) {
            SusanState.velocity = velocity;
            SusanState.angle = angle;
            SusanState.voltage = voltage;
            stateTime = System.currentTimeMillis();
        }

    }

    /**
     * This is returned by the DriveTask and holds values for the new left and
     * right powers to be set by Drive
     */
    public static class SusanOrder {
        public double power;

        public SusanOrder(double power) {
            this.power = power;
        }
    }
}