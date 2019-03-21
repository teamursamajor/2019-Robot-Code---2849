package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class ScrewClimb implements UrsaRobot {

    private Spark frameWheel, leadscrew;
    private double distanceTolerance = 2.0; // max distance before the sensor tells the leadscrews to stop
    private double leadscrewSpeed = 0.5;
    private double frameWheelSpeed = 0.5;
    private boolean leadscrewsUp = false;

    // TODO use ultra object for ultrasonic sensor

    public ScrewClimb() {
        frameWheel = new Spark(CLIMB_FRONT);
        leadscrew = new Spark(CLIMB_BACK);
    }

    public void initialize(){
        Thread t = new Thread("Climb Thread");
        t.start();
    }

    public void run(){
        // System.out.println("Distance: " + ultra.getRangeInches());
        if(xbox.getSingleButtonPress(XboxController.BUTTON_START) && !leadscrewsUp){ // start leadscrews
            leadscrewsUp = true;
            leadscrew.set(leadscrewSpeed);
            while (ultra.getRangeInches() <= distanceTolerance) {
                // TODO do we need a sleep here? not sure
                try {
                    Thread.sleep(20);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            // the driver could either wait to see this print or use the camera
            System.out.println("Leadscrews are up! Drive the frame wheel!");

            leadscrew.set(0.0);
        }

        if(xbox.getButton(XboxController.BUTTON_BACK)){ // run frame wheel
            frameWheel.set(frameWheelSpeed);
        } 

        if(xbox.getButton(XboxController.BUTTON_START) && leadscrewsUp) { // brings leadscrews up while the button is pressed
            leadscrew.set(-leadscrewSpeed);
        } else {
            leadscrew.set(0.0);
        }

        try {
            Thread.sleep(20);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}