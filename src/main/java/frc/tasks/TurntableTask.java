package frc.tasks;

import frc.robot.Turntable;
import frc.robot.UrsaRobot;
import frc.robot.Constants;
import frc.robot.ControlMap;

public class TurntableTask extends Task implements UrsaRobot {

    public double power;
    public long time;

    public enum TurntableMode {
        MANUAL, CUSTOM;

        public TurntableOrder callLoop() {
            // "this" refers to subsystemMode
            switch (this) {
            case MANUAL:
                return manualBox();
            case CUSTOM:
                return autoCalculator();
            }
            return new TurntableOrder(0.0);
        }

        private TurntableOrder manualBox() {
            if (ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_HATCH)
                    || ControlMap.controlLayout.equals(ControlMap.ControlLayout.CARGO_HATCH_CLIMB)) {
                if (xbox.getDPad(controls.map.get("turntable_left"))) {
                    return new TurntableOrder(Constants.turntablePower);
                } else if (xbox.getDPad(controls.map.get("turntable_right"))) {
                    return new TurntableOrder(-Constants.turntablePower);
                }
            }

            return new TurntableOrder(0.0);

        }

        /**
         * Would move to some custom location set by autonomous code. Unwritten.
         * 
         * @return
         */
        private TurntableOrder autoCalculator() {
            // TODO code this
            return new TurntableOrder(0.0);
        }
    }

    // TODO update this to use a timer
    public TurntableTask(TurntableMode mode, Turntable turntable) {
        running = true;
        turntable.setMode(mode);
        Thread t = new Thread("Turntable Task");
        t.start();
    }

    public TurntableTask(double power, long time, Turntable turntable) {
        running = true;
        this.power = power;
        this.time = time;
        turntable.setMode(TurntableMode.CUSTOM);
        Thread t = new Thread("Turntable Task");
        t.start();
    }

    public static boolean running = true;

    public void run() {
        while (running) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This is returned by the TurntableTask and holds values for the new left and
     * right powers to be set by Turntable
     */
    public static class TurntableOrder {
        public double power;

        public TurntableOrder(double power) {
            this.power = power;
        }
    }
}