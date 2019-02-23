package frc.robot;

import java.util.HashMap;

/**
 * This is a map of all of the controls for the robot.
 */
public class ControlMap {

    public HashMap<String, Integer> map = new HashMap<String,Integer>();

    public ControlMap() {

        // Climb
        map.put("climb_start", XboxController.BUTTON_START);
        map.put("climb_stop", XboxController.BUTTON_BACK);

        // Turntable
        map.put("turntable_left", XboxController.AXIS_LEFTTRIGGER);
        map.put("turntable_right", XboxController.AXIS_RIGHTTRIGGER);

        // Hatch
        map.put("hatch_run", XboxController.BUTTON_A);
        map.put("hatch_flip", XboxController.BUTTON_B);

        // Cargo arm toggle lifting
        map.put("cargo_bay", XboxController.BUTTON_Y);
        map.put("cargo_rocket", XboxController.BUTTON_X);

        // Cargo arm manual lifting
        map.put("cargo_up", XboxController.POV_UP);
        map.put("cargo_down", XboxController.POV_DOWN);

        // Cargo wheels intake/outtake
        map.put("cargo_intake", XboxController.BUTTON_LEFTBUMPER);
        map.put("cargo_outtake", XboxController.BUTTON_RIGHTBUMPER);

    }

    
    

    
}