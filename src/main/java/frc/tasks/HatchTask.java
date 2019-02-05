package frc.tasks;

import frc.robot.Hatch;
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
			running = false;
			return new HatchOrder(0);
		}

		private HatchOrder moveToAngle(double desiredAngle) {
			double angleTolerance = 5;
			if(Math.abs(HatchState.hatchAngle - desiredAngle) <= angleTolerance){
				running = false;
				return new HatchOrder(0.0);
			}

			//TODO Add derivative term to PD loop
			double kpHatch = 1.0 / 40.0;
			double hatchMinimumPower = 0.3;
			// Proportional constant * (angle error) + derivative constant * velocity (aka pos / time)
			double hatchPower = kpHatch * (desiredAngle - HatchState.hatchAngle);

			if(hatchPower == 0) {
				running = false;
				return new HatchOrder(0.0);
			}

			if (Math.abs(hatchPower) < hatchMinimumPower) {
				hatchPower = Math.signum(hatchPower) * hatchMinimumPower;
			}
			
			return new HatchOrder(hatchPower);
		}
	}

	public static class HatchState {
		public static double hatchVelocity = 0.0, hatchAngle = 0.0;
		public static long stateTime = System.currentTimeMillis();

		public static void updateState(double hatchVelocity, double hatchAngle) {
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

	public HatchTask(HatchMode mode, Hatch hatch) {
		running = true;
		hatch.setMode(mode);
		Thread t = new Thread("HatchTask");
		t.start();
	}

	private static boolean running = true;

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