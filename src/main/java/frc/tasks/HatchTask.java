package frc.tasks;

public class HatchTask extends Task {

    public HatchTask() {
        super();
    }

    public enum HatchMode {
		DEFAULT, INTAKE, CARRY, DEPLOY;
    }
    
    //This stuff was for lift. Figured it would maybe help to reference for Hatch. Do what you will with it.
    // private double height;

	// // max time the lift can run for in ms
	// private long timeout = 3000;

	// public enum LiftType {
	// 	BOTTOM, VAULT, SWITCH, SCALE
	// }

	// public LiftTask(ControlLayout cont, double height) {
	// 	super(cont);
	// 	this.height = height;
	// }
	
	// public LiftTask(ControlLayout cont, double height, long timeout) {
	// 	super(cont);
	// 	this.height = height;
	// 	this.timeout = timeout;
	// }

	public void run() {
		// Logger.log("Running lift task", LogLevel.INFO);
		// cont.getLift().setDesiredHeight(height);
		// long startTime = System.currentTimeMillis();
		// while (!cont.getLift().getReached() && System.currentTimeMillis() - startTime < timeout) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		// }
		// cont.getLift().setDesiredHeight(cont.getLift().getCurrentHeight());

	}

	public String toString() {
        // return "HatchTask: " + cont.getLift().getDesiredHeight() + "\n";
        return " ";
	}

	// public static long presetToTimeout(LiftType liftPreset) {
	// 	switch (liftPreset) {
	// 	case BOTTOM:
	// 		return 0;
	// 	case VAULT:
	// 		return 0;
	// 	case SWITCH:
	// 		return 2000;
	// 	case SCALE:
	// 		return 3400;
	// 	default:
	// 		return 0;
	// 	}
	// }
	
	// public static double presetToHeight(LiftType liftPreset) {
	// 	switch (liftPreset) {
	// 	case BOTTOM:
	// 		return 0;
	// 	case VAULT:
	// 		return 0;
	// 	case SWITCH:
	// 		return 20;
	// 	case SCALE:
	// 		return 75;
	// 	default:
	// 		return 0;
	// 	}
	// }
}