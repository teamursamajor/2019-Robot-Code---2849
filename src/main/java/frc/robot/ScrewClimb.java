package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Ultrasonic;


public class ScrewClimb implements UrsaRobot {

    private Spark frameWheel, leadscrew;
    private double distanceTolerance = 2.0; // max distance before the sensor tells the leadscrews to stop
    private double leadscrewSpeed = 0.5;
    private double frameWheelSpeed = 0.5;
    private boolean leadscrewsUp = false;
    private Ultrasonic distanceSensor;

    public ScrewClimb() {
        frameWheel = new Spark(CLIMB_FRONT);
        leadscrew = new Spark(CLIMB_BACK);

        // https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599715-ultrasonic-sensors-measuring-robot-distance-to-a-surface
        distanceSensor = new Ultrasonic(1, 1);// Output, input
    }

    public void initialize(){
        Thread t = new Thread("Climb Thread");
        t.start();
    }    


    public void run(){
        System.out.println("Distance: " + distanceSensor.getRangeInches());
        if(xbox.getSingleButtonPress(XboxController.BUTTON_START) && !leadscrewsUp){ // start leadscrews
            leadscrewsUp = true;
            while(distanceSensor.getRangeInches() <= distanceTolerance){
                leadscrew.set(leadscrewSpeed);

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

        if(xbox.getButton(XboxController.BUTTON_BACK) && leadscrewsUp) { // brings leadscrews up while the button is pressed
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