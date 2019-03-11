package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Ultrasonic;

//<p><b>TESTED</b></p> = tested but not finished
//<p><b>UN-TESTED</b></p> = latest change not finished
//<p><b>FINISHED</b></p> = finished and working
//<p><b>NOT WRITTEN</b></p> = need to write
//<p><b>FINISHED</b></p> = finished and working 100% optimally
//<p><b>NOT OPTIMIZED</b></p> = finished but not working 100% optimally
public class ScrewClimb implements UrsaRobot {
    /**
     * The value that the ultrasonic sensor should be in the range of WHILE climbing
     * (in inches)
     */
    private double wallDist = 2.0;

    /**
     * This interface contains the motors/sensors used for climb <b>TODO - CHANGE
     * SPARK VALUES FOR WHATEVER WE CALL THEM IN URSA ROBOT</b>
     */
    private interface ClimbMechanism {
        // Sparks
        Spark platformWheel = new Spark(CLIMB_FRONT);// This wheel is on the platform - drives the screws
        Spark screwSpark = new Spark(CLIMB_BACK);

        // Sensors
        // Ultra sonic
        // https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599715-ultrasonic-sensors-measuring-robot-distance-to-a-surface
        Ultrasonic sensor = new Ultrasonic(1, 1);// Output, input
        double noise = 0;// how exact it needs to be
        // Ohms law voltage = crnt in * resistance

    }

    /**
     * <p>
     * <b>NOT WRITTEN</b>
     * </p>
     * Climbs <b>NEED TO TEST; NEED TO WRITE</b>
     */
    public void climb() {
        runSensor(true);
        while (isClimbing()) {
            extendPlatform(true);
        }
        runSensor(false);
        // TODO - until we start driving
        while (true) {
            extendPlatform(false);
        }
    }

    /**
     * <p>
     * <b>NOT WRITTEN</b>
     * </p>
     * Whether or not we are high enough
     */
    private boolean isClimbing() {
        if (ClimbMechanism.sensor.getRangeInches() < wallDist - ClimbMechanism.noise) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * moves the platform down/up; true = down
     */
    private void extendPlatform(boolean down) {
        System.out.println(ClimbMechanism.sensor.getRangeInches());
        if (down) {
            System.out.println("Down");
            ClimbMechanism.screwSpark.set(1);
        } else {
            System.out.println("Up");
            ClimbMechanism.screwSpark.set(-1);
        }
    }

    // TODO - add timer just in case
    private void runSensor(boolean on) {
        ClimbMechanism.sensor.setAutomaticMode(true);
    }
}