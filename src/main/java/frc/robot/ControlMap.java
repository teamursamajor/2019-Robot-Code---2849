package frc.robot;

import java.util.HashMap;

/**
 * This is a map of all of the controls for the robot.
 */
public class ControlMap {

    static HashMap<String, Integer> controlMap = new HashMap<String,Integer>();

    public ControlMap() {

        // Cargo intake/outtake wheels
        controlMap.put("cargo_intake", XboxController.BUTTON_LEFTBUMPER);
        controlMap.put("cargo_outtake", XboxController.BUTTON_RIGHTBUMPER);

        // Cargo arm manual lifting
        controlMap.put("cargoarm_up", XboxController.POV_UP);
        controlMap.put("cargoarm_down", XboxController.POV_DOWN);

        // Cargo arm toggling
        controlMap.put("cargotoggle_bay", XboxController.BUTTON_Y);
        controlMap.put("cargotoggle_rocket", XboxController.BUTTON_X);

        // Drive sticks
        controlMap.put("drivestick", XboxController.AXIS_LEFTSTICK_Y);
        controlMap.put("turnstick", XboxController.AXIS_RIGHTSTICK_X);

    }

    
    

    
}