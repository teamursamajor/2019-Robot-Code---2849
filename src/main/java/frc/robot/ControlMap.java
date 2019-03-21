package frc.robot;

import java.util.HashMap;

/**
 * This is a map of all of the controls for the robot.
 */
public class ControlMap {

    public HashMap<String, Integer> map = new HashMap<String, Integer>();

    public static ControlLayout controlLayout = ControlLayout.CARGO_HATCH;

    public static enum ControlLayout {
        CARGO_HATCH_CLIMB, CARGO_HATCH, CARGO_CLIMB;
    }

    /*
     * PLEASE NOTE: If you change the type of an input (as in Button, Axis, or POV)
     * you have to change the method used to get that input whereever this control
     * is called. Ex: If you change the turntable from the triggers to a button, you
     * need to go to Turntable and change the xbox method to getButton() too.
     */
    public ControlMap() {
        // These are the same throughout all control layouts

        // Cargo arm toggle lifting
        map.put("cargo_bay", XboxController.BUTTON_Y);
        map.put("cargo_rocket", XboxController.BUTTON_X);

        // Cargo arm manual lifting
        map.put("cargo_up", XboxController.AXIS_RIGHTTRIGGER);
        map.put("cargo_down", XboxController.AXIS_LEFTTRIGGER);

        // Cargo wheels intake/outtake
        map.put("cargo_intake", XboxController.BUTTON_LEFTBUMPER);
        map.put("cargo_outtake", XboxController.BUTTON_RIGHTBUMPER);

        // Hatch *unused in cargo_climb, but nothing else uses A/B anyways*
        map.put("hatch", XboxController.BUTTON_A);

        // Changes control settings based on current layout
        switch (controlLayout) {
        case CARGO_HATCH:
            // Turntable
            map.put("turntable_left", XboxController.POV_LEFT);
            map.put("turntable_right", XboxController.POV_RIGHT);

            // Auto Align
            // map.put("auto_align", XboxController.BUTTON_START);
            map.put("reset_head", XboxController.BUTTON_START);
            map.put("cancel_auto_align", XboxController.BUTTON_BACK);
            break;
        case CARGO_CLIMB:
            // Climb
            map.put("climb_start", XboxController.BUTTON_START);
            map.put("climb_stop", XboxController.BUTTON_BACK);

            map.put("climb_arm_up", XboxController.POV_UP);
            map.put("climb_arm_down", XboxController.POV_DOWN);
            map.put("cam_up", XboxController.POV_RIGHT);
            map.put("cam_down", XboxController.POV_LEFT);

            break;
        case CARGO_HATCH_CLIMB:
            // Turntable
            map.put("turntable_left", XboxController.POV_LEFT);
            map.put("turntable_right", XboxController.POV_RIGHT);

            // Auto Align
            map.put("auto_align", XboxController.POV_UP);
            map.put("cancel_auto_align", XboxController.POV_DOWN);

            // Climb
            map.put("climb_start", XboxController.BUTTON_START);
            map.put("climb_stop", XboxController.BUTTON_BACK);

            break;
        default:
            // Climb
            map.put("climb_start", XboxController.BUTTON_START);
            map.put("climb_stop", XboxController.BUTTON_BACK);

            // Turntable
            map.put("turntable_left", XboxController.POV_LEFT);
            map.put("turntable_right", XboxController.POV_RIGHT);

            break;
        }
    }
}