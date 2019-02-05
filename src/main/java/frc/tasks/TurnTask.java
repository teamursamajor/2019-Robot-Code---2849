package frc.tasks;

public class TurnTask extends Task {

    public enum TurnMode {
        TURN_TO, TURN_BY
    }

    public TurnOrder callLoop() {
        //"this" refers to the enum that the method is in
        switch (this) {
        case TURN_TO:
            return autoGoToAngle(90);
        case TURN_BY:
            return autoGoToAngle(/*value here*/)
        }
        return new TurnOrder(0.0);
    }

    private TurnOrder autoGoToAngle(double angle) {
        return new TurnOrder(0.0);
    }

    public TurnTask() {
        super();
    }

    public static boolean running = true;

    public void run() {
        while(running) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO this entire thing
}