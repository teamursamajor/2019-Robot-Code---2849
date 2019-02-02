package frc.tasks;

import frc.robot.UrsaRobot;

public class HatchTask extends Task {

	/**
	 * Start: The position at the start of the match - Holding a hatch and within
	 * perimeter
	 * 
	 * Bottom: The lowest possible position - This is where you start before picking
	 * up a hatch
	 * 
	 * Top: The highest possible positon - This is where you start before delivering
	 * a hatch and where you carry the hatch
	 */
	public enum HatchMode {
		START, BOTTOM, TOP;

		public HatchOrder callLoop() {
			switch (this) {
			case START:
				return moveToAngle(UrsaRobot.startAngle);
			case BOTTOM:
				return moveToAngle(UrsaRobot.bottomAngle);
			case TOP:
				return moveToAngle(UrsaRobot.topAngle);
			}
			return new HatchOrder(0);
		}

		private HatchOrder moveToAngle(double desiredAngle) {
			// Loop: While we are not at the specified angle, move to ti
			double angleTolerance = 5;
			if(Math.abs(HatchState.hatchAngle - desiredAngle) <= angleTolerance){
				return new HatchOrder(0.0);
			}

			//TODO Add derivative term to PD loop
			double kpHatch = 1.0 / 40.0;
			double hatchMinimumPower = 0.3;
			// Proportional constant * (angle error)
			double hatchPower = kpHatch * (desiredAngle - HatchState.hatchAngle);

			if (Math.abs(hatchPower) < hatchMinimumPower) {
				hatchPower = Math.signum(hatchPower) * hatchMinimumPower;
			}
			
			return new HatchOrder(hatchPower);
		}
	}

	public static class HatchState {
		public static double hatchPower = 0.0, hatchVelocity = 0.0, hatchAngle = 0.0;
		public static long stateTime = System.currentTimeMillis();

		public static void updateState(double hatchPower, double hatchVelocity, double hatchAngle) {
			HatchState.hatchPower = hatchPower;
			HatchState.hatchVelocity = hatchVelocity;
			HatchState.hatchAngle = hatchAngle;
			stateTime = System.currentTimeMillis();
		}
	}

	public static class HatchOrder {
		public double hatchPower;

		public HatchOrder(double hatchPower) {
			this.hatchPower = hatchPower;
		}
	}

	public HatchTask() {
		super();
	}

	public void run() {
		// Take an angle/position from the auto code or controller
		// Set Hatch's subsystemMode accordingly
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		// return "HatchTask: " + cont.getLift().getDesiredHeight() + "\n";
		return " ";
	}
}