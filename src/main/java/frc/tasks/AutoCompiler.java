package frc.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// TODO @20XX why the unused imports?
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.Relay.Direction;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tasks.CargoTask.CargoMode;
import frc.tasks.DriveTask.DriveMode;
import frc.tasks.HatchTask.HatchMode;
import frc.tasks.TurntableTask.TurntableMode;
import frc.robot.*;

//TODO path (follow) code need to be added!

// TODO actually make this file vvvv

/**
 * Check the AutoModes folder for Auto Compiler Syntax.txt
 * It contains all the syntax
 * 
 * @author Evan + Sheldon originally wrote this on 1/16/18. Evan updated it for
 *         the 2019 season.
 */
public class AutoCompiler {
	interface Token {
	}

	private Drive drive;
	private Cargo cargo;
	private Hatch hatch;
	private Turntable turntable;

	public AutoCompiler(Drive drive, Cargo cargo, Hatch hatch, Turntable turntable) {
		this.drive = drive;
		this.cargo = cargo;
		this.hatch = hatch;
		this.turntable = turntable;
	}

	/**
	 * A token that executes a given auto file
	 * 
	 * @param scriptName Name of the file to execute
	 */
	class ExecuteToken implements Token {
		private String scriptName;

		public ExecuteToken(String scriptName) {
			this.scriptName = "/home/lvuser/automodes/" + scriptName.trim();
		}
	}

	/**
	 * A token that prints any string passed to it to the console
	 * 
	 * @param str String to print
	 */
	class PrintToken implements Token {
		private String str; // String to be printed

		// Instantiate PrintToken class
		public PrintToken(String str) {
			this.str = str; // Set variable str to argument string
		}

		// Creates a new instance of PrintTask class
		public PrintTask makeTask() {
			return new PrintTask(str);
		}
	}

	/**
	 * A token that runs a given path file
	 * 
	 * @param filename Path file to run
	 */
	// TODO Implement with changes to path
	class PathToken implements Token {
		// private Path[] paths;

		public PathToken(String filename) {
			filename = filename.replace(" ", "");
			// TODO put all paths into /paths
			// paths = new PathReader("/home/lvuser/paths/" + filename + ".path",
			// false).getPaths();
		}

		public PathTask makeTask() {
			// return new PathTask(paths);
			return null;
		}
	}

	/**
	 * A token that sets the cargo arm to a given state
	 * 
	 * @param state State of the cargo arm (picking up, dropping off, etc)
	 */
	class CargoToken implements Token {
		private CargoMode cargoMode;

		public CargoToken(String state) {
			state = state.replace(" ", "");
			if (state.equalsIgnoreCase("GROUND")) {
				cargoMode = CargoMode.GROUND;
			} else if (state.equalsIgnoreCase("LOWROCKET")) {
				cargoMode = CargoMode.LOWROCKET;
			} else if (state.equalsIgnoreCase("CARGOBAY")) {
				cargoMode = CargoMode.CARGOBAY;
			} else {
				cargoMode = CargoMode.GROUND;
			}
		}

		public CargoTask makeTask() {
			return new CargoTask(cargoMode, cargo);
		}
	}

	/**
	 * A token that moves the hatch arm to a preset position
	 * 
	 * @param position Position to move the hatch arm to
	 */
	class HatchToken implements Token {
		private HatchMode hatchMode;

		public HatchToken(String position) {
			position = position.replace(" ", "");

			if (position.equalsIgnoreCase("RUN")) {
				hatchMode = HatchMode.RUN; 
			} else if (position.equalsIgnoreCase("FLIP")) {
				hatchMode = HatchMode.FLIP;
			} else {
				hatchMode = HatchMode.WAIT;
			}
		}

		public HatchTask makeTask() {
			return new HatchTask(hatchMode, hatch);
		}
	}

	/**
	 * A token that moves the turntable to a given direction
	 * 
	 * @param direction Direction to turn the turntable to
	 */
	class TurntableToken implements Token {
		private TurntableMode turntableMode = TurntableMode.CUSTOM;
		private double turntableAngle;

		public TurntableToken(String direction) {
			direction = direction.replace(" ", "");

			if (direction.equalsIgnoreCase("FORWARD")) {
				turntableMode = TurntableMode.FORWARD;
			} else if (direction.equalsIgnoreCase("LEFT")) {
				turntableMode = TurntableMode.LEFT;
			} else if (direction.equalsIgnoreCase("RIGHT")) {
				turntableMode = TurntableMode.RIGHT;
			} else if (direction.equalsIgnoreCase("ALIGN")) {
				turntableMode = TurntableMode.AUTO_ALIGN;
			} else {
				try {
					turntableAngle = Double.parseDouble(direction);
				} catch (NumberFormatException e) {
					turntableAngle = 0;
					e.printStackTrace();
				}
			}
		}

		public TurntableTask makeTask() {
			// If there is no custom angle, return a mode (defaults to forwards)
			if (turntableAngle == 0)
				return new TurntableTask(turntableMode, turntable);
			// If there is a custom angle, return that angle
			else
				return new TurntableTask(TurntableMode.CUSTOM, turntable);
		}
	}

	/**
	 * A token that delays the auto mode for a duration passed to it
	 * 
	 * @param time Time to wait
	 */
	class WaitToken implements Token {
		private double wait;

		public WaitToken(String time) {
			time = time.replace(" ", "");
			try {
				if (Double.parseDouble(time) >= 0) {
					wait = Double.parseDouble(time);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		public WaitTask makeTask() {
			return new WaitTask((long) (wait * 1000.0d));
		}
	}

	/**
	 * A token that runs a set of tasks within it at once
	 */
	class BundleToken implements Token {
		public BundleToken() {
		}
	}

	/**
	 * A token that runs a set of tasks within it in order
	 */
	class SerialToken implements Token {
		public SerialToken() {
		}
	}

	/**
	 * A token that ends the most recent group task (bundle/serial)
	 */
	class RightBraceToken implements Token {
		public RightBraceToken() {
		}
	}

	/**
	 * A token that turns the robot to face a given angle
	 * 
	 * @param angle Angle to turn to
	 */
	class TurnToken implements Token {
		private double turnAmount;

		public TurnToken(String angle) {
			angle = angle.replace(" ", "");
			try {
				if (Math.abs(Double.parseDouble(angle)) >= 0) {
					turnAmount = Double.parseDouble(angle);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		public DriveTask makeTask() {
			return new DriveTask(turnAmount, drive, DriveMode.TURN);
		}
	}

	/**
	 * A token that aligns the robot to the nearest pair of reflective tape
	 * 
	 * @param times Number of pairs of reflective tape to check for
	 */
	class AlignToken implements Token {
		private int matchPairs = 1;
		private String mode = "";

		public AlignToken(String times) {
			times = times.replace(" ", "").toUpperCase();
			if (times.contains("BAY")) mode = "BAY";
			else mode = "FLOOR";
			try {
				if (Integer.parseInt(times.substring(times.indexOf(mode))) > 0) {
					matchPairs = Integer.parseInt(times);
				}
				if (matchPairs > 3) {
					matchPairs = 3;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		public DriveTask makeTask() {
			if (mode.equals("BAY")) return new DriveTask(matchPairs, drive, DriveMode.ALIGN_BAY);
			else return new DriveTask(matchPairs, drive, DriveMode.ALIGN_FLOOR);
		}
	}

	/**
	 * A token that drives the robot a given distance
	 * 
	 * @param dist The distance to drive
	 */
	class DriveToken implements Token {
		private double dist;

		public DriveToken(String distance) {
			distance = distance.replace(" ", "");
			try {
				if (Math.abs(Double.parseDouble(distance)) >= 0) {
					dist = Double.parseDouble(distance);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		public DriveTask makeTask() {
			return new DriveTask(dist, drive);
		}
	}

	/**
	 * Interprets specified file to identify keywords as tokens to add to a
	 * collective ArrayList
	 * 
	 * @param filename Name of file to tokenize
	 * @return ArrayList of all tokens in ranking order
	 * @throws IOException
	 */
	private ArrayList<Token> tokenize(String filename) throws IOException {
		ArrayList<Token> tokenList = new ArrayList<Token>();
		BufferedReader buff;
		buff = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = buff.readLine()) != null) {
			if (line.contains("#")) { // # means a comment, so the tokenizer ignores lines beginning with it
				continue;
			} else if (line.contains("follow")) {
				// Path to follow (file name)
				String current = line.substring(line.indexOf("follow") + "follow".length()); 
				tokenList.add(new PathToken(current));
			} else if (line.contains("execute")) {
				// Automode to execute (file name)
				String current = line.substring(line.indexOf("execute") + "execute".length()); 
				tokenList.add(new ExecuteToken(current));
			} else if (line.contains("wait")) {
				String current = line.substring(line.indexOf("wait") + "wait".length()); // Wait length (seconds)
				tokenList.add(new WaitToken(current));
			} else if (line.contains("drive")) {
				String current = line.substring(line.indexOf("drive") + "drive".length()); // Drive length (feet)
				tokenList.add(new DriveToken(current));
			} else if (line.contains("turntable")) { // This must go first because "turntable" contains "turn"
				String current = line.substring(line.indexOf("turntable") + "turntable".length()); // Turntable mode
				tokenList.add(new TurntableToken(current));
			} else if (line.contains("turn")) {
				String current = line.substring(line.indexOf("align") + "turn".length()); // TurntableTask angle
				tokenList.add(new TurnToken(current));
			} else if (line.contains("hatch")) {
				String current = line.substring(line.indexOf("hatch") + "hatch".length()); // Hatch mode
				tokenList.add(new HatchToken(current));
			} else if (line.contains("cargo")) {
				String current = line.substring(line.indexOf("cargo") + "cargo".length()); // Cargo mode
				tokenList.add(new CargoToken(current));
			} else if (line.contains("align")) { // This must go later because some tasks have align modes
				String current = line.substring(line.indexOf("turn") + "turn".length()); // Tape match times
				tokenList.add(new AlignToken(current));
			} else if (line.contains("print")) {
				String current = line.substring(line.indexOf("print") + "print".length()); // Text to print
				tokenList.add(new PrintToken(current));
			} else if (line.contains("bundle")) {
				tokenList.add(new BundleToken());
			} else if (line.contains("serial")) {
				tokenList.add(new SerialToken());
			} else if (line.contains("}")) {
				tokenList.add(new RightBraceToken());
			}
		}
		buff.close();
		return tokenList; // Returns ArrayList of all tokens
	}

	/**
	 * Interprets an ArrayList of tokens as an ordered set of tasks
	 * 
	 * @param tokenList An ArrayList of tokens (returned from tokenize())
	 * @param taskSet   A set of tasks to add tasks to
	 * @return A complete set of tasks
	 */
	private Task parseAuto(ArrayList<Token> tokenList, GroupTask taskSet) {
		if (tokenList.size() == 0) {
			return new WaitTask(0);
		}

		while (tokenList.size() > 0) {
			Token t = tokenList.remove(0);
			if (t instanceof ExecuteToken) {
				Task otherMode = buildAutoMode(((ExecuteToken) t).scriptName);
				taskSet.addTask(otherMode);
			} else if (t instanceof WaitToken) {
				taskSet.addTask(((WaitToken) t).makeTask());
			} else if (t instanceof DriveToken) {
				taskSet.addTask(((DriveToken) t).makeTask());
			} else if (t instanceof TurnToken) {
				taskSet.addTask(((TurnToken) t).makeTask());
			} else if (t instanceof AlignToken) {
				taskSet.addTask(((AlignToken) t).makeTask());
			} else if (t instanceof PathToken) {
				taskSet.addTask(((PathToken) t).makeTask());
			} else if (t instanceof CargoToken) {
				taskSet.addTask(((CargoToken) t).makeTask());
			} else if (t instanceof HatchToken) {
				taskSet.addTask(((HatchToken) t).makeTask());
			} else if (t instanceof TurntableToken) {
				taskSet.addTask(((TurntableToken) t).makeTask());
			} else if (t instanceof BundleToken) {
				BundleTask bundleSet = new BundleTask();
				parseAuto(tokenList, bundleSet);
				taskSet.addTask(bundleSet);
			} else if (t instanceof SerialToken) {
				SerialTask serialSet = new SerialTask();
				parseAuto(tokenList, serialSet);
				taskSet.addTask(serialSet);
			} else if (t instanceof RightBraceToken) {
				return taskSet;
			} else if (t instanceof PrintToken) {
				taskSet.addTask(((PrintToken) t).makeTask());
			}
		}
		return taskSet;
	}

	/**
	 * Builds a set of tasks based on the contents of an auto script
	 * 
	 * @param filename The name of the auto script to reference
	 * @return A set of tasks
	 */
	public Task buildAutoMode(String filename) {
		try {
			return parseAuto(tokenize(filename), new SerialTask());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO Adapt this when we need it
	/**
	 * Gets the switch/scale side from the FMS, and finds an Auto Mode file which
	 * finds robot's position and switch/scale ownership and performs the highest
	 * task in the ranked list of tasks from the SmartDashboard that matches the
	 * current setup.
	 * 
	 * @param robotPosition The side our robot starts on. L, M, or R.
	 * @param autoPrefs     String array of ranked Auto Modes
	 * @param autoFiles     File array of all files in the AutoModes folder
	 * @return String name of the auto file to run
	 */
	public String pickAutoMode(String robotPosition, String gamePiece, String piecePosition, String[] autoPrefs,
			File[] autoFiles) {
		// Gets the ownership information from the FMS

		// TODO make a new selector for this based on what bay we want to go to
		String mode = " ";

		// TODO update
		// switch (switchPos + scalePos) {
		// case "LL":
		// mode = autoPrefs[0];
		// break;
		// case "LR":
		// mode = autoPrefs[1];
		// break;
		// case "RL":
		// mode = autoPrefs[2];
		// break;
		// case "RR":
		// mode = autoPrefs[3];
		// break;
		// default:
		// mode = "path_drive";
		// break;
		// }

		// TODO for potential future use
		// String oppSide =
		// DriverStation.getInstance().getGameSpecificMessage().substring(2);

		String regex = "/(" + robotPosition + ")(" + gamePiece + ")(" + piecePosition + ")(" + mode + "(.auto)/gi";

		for (File f : autoFiles) {
			if (f.getName().matches(regex)) {
				return "/home/lvuser/automodes/" + f.getName();
			}
		}
		// TODO make a default auto mode
		return "/home/lvuser/automodes/ .auto";
	}
}