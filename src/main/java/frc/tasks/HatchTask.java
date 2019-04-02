package frc.tasks;

import frc.robot.*;

public class HatchTask extends Task implements UrsaRobot {
    public enum HatchMode {
        WAIT, RUN;
    }

    private static boolean running = true;

    public HatchTask(HatchMode mode, Hatch hatch) {
        running = true;
        hatch.setMode(mode);
        Thread t = new Thread(this, "HatchTask");
        t.start();
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}