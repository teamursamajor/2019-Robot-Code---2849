package frc.tasks;

public class CargoTask extends Task {

    public CargoTask() {
        super();
    }

    public enum CargoMode {
        
    }

    //This stuff is for Intake from last year. Do what you will with it
    // private long timeout = 2000;
	// private long startTime;
	// public enum IntakeType {
	// 	IN, OUT, RUN, STOP, DEPLOY, HOLD, RUN_IN, RUN_OUT
	// }

	// private IntakeType intake;

	// public IntakeTask(ControlLayout cont, IntakeType intakeVal) {
	// 	super(cont);
	// 	intake = intakeVal;
	// }

	public void run() {
		// Logger.log("Running intake task", LogLevel.INFO);

		// cont.getIntake().setIntakeType(intake);
		// //Run just keeps running, In/Out use the sensor
		// switch (intake) {
		// case IN:
		//     startTime = System.currentTimeMillis();
		// 	while(System.currentTimeMillis() - startTime < timeout){
		// 		try {
		// 			Thread.sleep(20);
		// 		} catch (InterruptedException e) {
		// 			e.printStackTrace();
		// 			Logger.log("IntakeTask run method intake case IN Thread.sleep call, printStackTrace", LogLevel.ERROR);
		// 		}
		// 	}
		// 	cont.getIntake().setIntakeType(IntakeType.STOP);
		// 	break;
		// case OUT:
		// 	startTime = System.currentTimeMillis();
		// 	while(System.currentTimeMillis() - startTime < timeout){
		// 		try {
		// 			Thread.sleep(20);
		// 		} catch (InterruptedException e) {
		// 			e.printStackTrace();
		// 			Logger.log("IntakeTask run method intake case OUT Thread.sleep call, printStackTrace", LogLevel.ERROR);
		// 		}
		// 	}
		// 	cont.getIntake().setIntakeType(IntakeType.STOP);
		// 	break;
		// case DEPLOY:
		// 	startTime = System.currentTimeMillis();
		// 	// new LiftTask(cont,Integer.MAX_VALUE, 250).start();
		// 	while(System.currentTimeMillis() - startTime < timeout){
		// 		cont.getIntake().setIntakeType(IntakeType.STOP);
		// 		try {
		// 			Thread.sleep(20);
		// 		} catch (InterruptedException e) {
		// 			e.printStackTrace();
		// 			// Logger.log("IntakeTask run method intake case DEPLOY Thread.sleep call, printStackTrace", LogLevel.ERROR);
		// 		}
		// 	}
		// 	// cont.getIntake().setIntakeType(IntakeType.STOP);
		// 	break;
		// default:
		// 	// Logger.log("Intake in " + intake.name() + " case :^)", LogLevel.DEBUG);
		// 	break;
		// }

	}

	public String toString() {
        // return "CargoTask: " + cargo.name() + "\n";
        return " ";
	}
}