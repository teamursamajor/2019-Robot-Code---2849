package frc.robot;

import java.util.HashMap;

/**
 * This is a map of all of the controls for the robot.
 */
public class ControlMap {

    public HashMap<String, Integer> map = new HashMap<String, Integer>();

    /*
     * PLEASE NOTE: If you change the type of an input (as in Button, Axis, or POV)
     * you have to change the method used to get that input whereever this control
     * is called. Ex: If you change the cargo lift from the triggers to a button,
     * you need to go to cargo and change the xbox method to getButton() too.
     */
    public ControlMap() {
        // Cargo arm automatic lifting
        map.put("cargo_ground", XboxController.BUTTON_X);
        map.put("cargo_bay", XboxController.BUTTON_Y);
        map.put("cargo_rocket", XboxController.BUTTON_B);

        // Cargo arm manual lifting
        map.put("cargo_up", XboxController.AXIS_RIGHTTRIGGER);
        map.put("cargo_down", XboxController.AXIS_LEFTTRIGGER);

        // Cargo wheels intake/outtake
        map.put("cargo_intake", XboxController.BUTTON_LEFTBUMPER);
        map.put("cargo_outtake", XboxController.BUTTON_RIGHTBUMPER);

        // Hatch
        map.put("hatch", XboxController.BUTTON_A);
        
        // Auto Align
        map.put("auto_align", XboxController.POV_UP);
        map.put("cancel_auto_align", XboxController.POV_DOWN);
        map.put("limelight_toggle", XboxController.POV_RIGHT);

        // Climb
        map.put("climb_leadscrew_up", XboxController.BUTTON_START);
        map.put("climb_framewheel", XboxController.BUTTON_BACK);
    }
}