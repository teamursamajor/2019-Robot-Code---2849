package frc.tasks;

import frc.robot.Piston;
import frc.robot.UrsaRobot;

public class PistonTask extends Task {

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
	public enum PistonMode {
		IN, OUT;

		public PistonOrder callLoop() {
			switch (this) {
			case IN:
				return moveToPosition();
			case OUT:
				return moveToPosition();
			}
			running = false;
			return new PistonOrder(0);
		}

		private PistonOrder moveToPosition() {
			//TODO Figure out what to do here
			
			return new PistonOrder(0);
		}
	}

	public static class PistonState {
		
		public static long stateTime = System.currentTimeMillis();

		public static void updateState() {
			stateTime = System.currentTimeMillis();
		}
	}

	public static class PistonOrder {
		public double pistonPower;

		public PistonOrder(double pistonPower) {
			this.pistonPower = pistonPower;
		}
	}

	public PistonTask(PistonMode mode, Piston piston) {
		running = true;
		piston.setMode(mode);
		Thread t = new Thread("HatchTask");
		t.start();
	}

	private static boolean running = true;

	public void run() {
		// Take an angle/position from the auto code or controller
		// Set Hatch's subsystemMode accordingly
		while (running) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		// return "HatchTask: " + cont.getLift().getDesiredHeight() + "\n";
		return " ";
	}
}