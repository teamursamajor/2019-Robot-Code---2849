package frc.tasks;

import frc.robot.AutoAlign;

/**
 * This is a task class for aligning the robot for Hatch and Cargo placement
 * during autonomous.
 */
public class AutoAlignTask extends Task {

    public AutoAlignTask() {
        Thread t = new Thread(this, "AutoAlignTask");
        t.start();
    }

    public void run() {
        AutoAlign.autoAlign();
    }
}