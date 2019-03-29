package frc.robot;

import edu.wpi.first.wpilibj.Spark;

/**
 * Program for our screw climb/buddy lift
 */
public class ScrewClimb implements UrsaRobot {

    private Spark frameWheel, leadscrew;
    private double distanceTolerance = 2.0; // max distance before the sensor tells the leadscrews to stop
    private double leadscrewSpeed = 0.5;
    private double frameWheelSpeed = 0.5;
    private double climbTimeout = 100000000; // TODO change this
    private boolean leadscrewsUp = false;

    // TODO use ultra object for ultrasonic sensor

    public ScrewClimb() {
        frameWheel = new Spark(CLIMB_FRONT);
        leadscrew = new Spark(CLIMB_BACK);
    }

    public void initialize() {
        Thread t = new Thread("Climb Thread");
        t.start();
    }

    public void run() {
        while (true) {
            // System.out.println("Distance: " + ultra.getRangeInches());
            // initial climb up
            // start button
            if (xbox.getSingleButtonPress(controls.map.get("climb_leadscrew_up")) && !leadscrewsUp) { // start leadscrews
                long startTime = System.currentTimeMillis();
                leadscrewsUp = true;
                leadscrew.set(leadscrewSpeed);
                // runs leadscrew while the distance sensor has not reached or while we are
                // within timeout
                while (ultra.getRangeInches() <= distanceTolerance
                        && (System.currentTimeMillis() - startTime) < climbTimeout) {
                    try {
                        Thread.sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // the driver could either wait to see this print or use the camera
                System.out.println("Leadscrews are up! Drive the frame wheel!");

                // If need be add a thread.sleep

                leadscrew.set(0.0);
            }

            // bottom wheel code
            // back button
            if (xbox.getButton(controls.map.get("climb_framewheel"))
                    && !xbox.getButton(controls.map.get("climb_leadscrew_up")) && leadscrewsUp) { // run frame wheel
                frameWheel.set(frameWheelSpeed);
            }

            // buddy lift code
            // hold start again to bring the lift back up
            if (xbox.getButton(controls.map.get("climb_leadscrew_up"))
                    && !xbox.getButton(controls.map.get("climb_framewheel")) && leadscrewsUp) {
                leadscrew.set(-leadscrewSpeed);
            } else {
                leadscrew.set(0.0);
            }

            // In case something happens to the ultra sonic sensor during a competition

            // Emergency stop
            if (xbox.getButton(controls.map.get("climb_leadscrew_up"))
                    && xbox.getButton(controls.map.get("climb_framewheel"))) {
                leadscrew.set(0.0);
                leadscrewsUp = true;
            }

            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}