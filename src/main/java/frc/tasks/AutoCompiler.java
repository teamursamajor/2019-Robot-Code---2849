package frc.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tasks.CargoTask.CargoMode;
import frc.tasks.SusanTask.SusanMode;
import frc.tasks.DriveTask.DriveMode;
import frc.tasks.PistonTask.PistonMode;
import frc.robot.*;

//TODO import logger when ready
//TODO path (follow) code need to be added!

/**
 * // TODO actually make this Check the AutoModes folder for a file named Auto
 * Compiler Syntax.txt It contains all the syntax
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
	private LazySusan susan;
	private Piston piston;

	public AutoCompiler(Drive drive, Cargo cargo, Hatch hatch, LazySusan susan, Piston piston) {
		this.drive = drive;
		this.cargo = cargo;
		this.hatch = hatch;
		this.susan = susan;
		this.piston = piston;
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
			// Logger.log("[TASK] Print Task", LogLevel.INFO);
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
			// Logger.log("[TASK] Path Task", LogLevel.INFO);
			// return new PathTask(paths);
			return null;
		}
	}

	/**
	 * A token that sets the cargo arm to a given state
	 * 
	 * @param state State of the cargo arm (picking up, dropping off, or deploying)
	 */
	class CargoToken implements Token {
		private CargoMode cargoMode;

		public CargoToken(String state) {
			// state = state.replace(" ", "");
			// if (state.equalsIgnoreCase("DEPLOY")) {
			// cargoMode = CargoMode.DEPLOY;
			// } else if (state.equalsIgnoreCase("PICKUP")) {
			// cargoMode = CargoMode.PICKUP;
			// } else if (state.equalsIgnoreCase("DROPOFF")) {
			// cargoMode = CargoMode.DROPOFF;
			// } else {
			// cargoMode = CargoMode.DEPLOY;
			// }
		}

		public CargoTask makeTask() {
			// Logger.log("[TASK] Cargo Task", LogLevel.INFO);
			return new CargoTask(cargoMode, cargo);
		}
	}

	/**
	 * A token that moves the hatch arm to a preset position
	 * 
	 * @param position Position to move the hatch arm to
	 */
	class HatchToken implements Token {
		// TODO IMPLEMENT HATCH AUTO
		// private HatchMode hatchMode;

		// public HatchToken(String position) {
		// position = position.replace(" ", "");

		// if (position.equalsIgnoreCase("START")) {
		// hatchMode = HatchMode.START;
		// } else if (position.equalsIgnoreCase("BOTTOM")) {
		// hatchMode = HatchMode.BOTTOM;
		// } else if (position.equalsIgnoreCase("TOP")) {
		// hatchMode = HatchMode.TOP;
		// } else {
		// hatchMode = HatchMode.BOTTOM;
		// }
		// }

		// public HatchTask makeTask() {
		// // Logger.log("[TASK] Hatch Task", LogLevel.INFO);
		// return new HatchTask(hatchMode, hatch);
		// }
	}

	/**
	 * A token that moves the lazy susan to a given direction
	 * 
	 * @param direction Direction to turn the lazy susan to
	 */
	class SusanToken implements Token {
		private SusanMode susanMode;
		private double susanAngle;

		public SusanToken(String direction) {
			direction = direction.replace(" ", "");

			if (direction.equalsIgnoreCase("FORWARD")) {
				susanMode = SusanMode.FORWARD;
			} else if (direction.equalsIgnoreCase("LEFT")) {
				susanMode = SusanMode.LEFT;
			} else if (direction.equalsIgnoreCase("RIGHT")) {
				susanMode = SusanMode.RIGHT;
			} else {
				try {
					susanAngle = Double.parseDouble(direction);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				susanMode = SusanMode.FORWARD;
			}
		}

		public SusanTask makeTask() {
			// Logger.log("[TASK] Susan Task", LogLevel.INFO);
			if (susanAngle == 0)
				return new SusanTask(susanMode, susan);
			else
				return new SusanTask(susanAngle, susan);
		}
	}

	/**
	 * A token that makes the piston go in or out
	 * 
	 * @param state State of the piston (in or out)
	 */
	class PistonToken implements Token {
		private PistonMode pistonMode;

		public PistonToken(String state) {
			state = state.replace(" ", "");

			if (state.equalsIgnoreCase("IN")) {
				pistonMode = PistonMode.IN;
			} else if (state.equalsIgnoreCase("OUT")) {
				pistonMode = PistonMode.OUT;
			} else { // TODO is this what we want as a default case?
				pistonMode = PistonMode.IN;
			}
		}

		public PistonTask makeTask() {
			// Logger.log("[TASK] Piston Task", LogLevel.INFO);
			return new PistonTask(pistonMode, piston);
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
			// Logger.log("[TASK] Wait Task", LogLevel.INFO);
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
			// Logger.log("[TASK] Turn Task", LogLevel.INFO);
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

		public AlignToken(String times) {
			times = times.replace(" ", "");
			try {
				if (Integer.parseInt(times) > 0) {
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
			// Logger.log("[TASK] Align Task", LogLevel.INFO);
			return new DriveTask(matchPairs, drive, DriveMode.ALIGN);
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
			// Logger.log("[TASK] Drive Task", LogLevel.INFO);
			// return new DriveTask((int) dist, drive);
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
				String current = line.substring(line.indexOf("follow") + "follow".length()); // Path to follow (file
																								// name)
				tokenList.add(new PathToken(current));
			} else if (line.contains("execute")) {
				String current = line.substring(line.indexOf("execute") + "execute".length()); // Automode to execute
																								// (file name)
				tokenList.add(new ExecuteToken(current));
			} else if (line.contains("wait")) {
				String current = line.substring(line.indexOf("wait") + "wait".length()); // Wait length (seconds)
				tokenList.add(new WaitToken(current));
			} else if (line.contains("drive")) {
				String current = line.substring(line.indexOf("drive") + "drive".length()); // Drive length (feet)
				tokenList.add(new DriveToken(current));
			} else if (line.contains("turn")) {
				String current = line.substring(line.indexOf("align") + "turn".length()); // Turn angle
				tokenList.add(new TurnToken(current));
			} else if (line.contains("align")) {
				String current = line.substring(line.indexOf("turn") + "turn".length()); // Tape match times
				tokenList.add(new AlignToken(current));
			} else if (line.contains("hatch")) {
				String current = line.substring(line.indexOf("hatch") + "hatch".length()); // Hatch mode
				// TODO fix
				// tokenList.add(new HatchToken(current));
			} else if (line.contains("cargo")) {
				String current = line.substring(line.indexOf("cargo") + "cargo".length()); // Cargo mode
				tokenList.add(new CargoToken(current));
			} else if (line.contains("susan")) {
				String current = line.substring(line.indexOf("susan") + "susan".length()); // Susan mode
				tokenList.add(new SusanToken(current));
			} else if (line.contains("piston")) {
				String current = line.substring(line.indexOf("piston") + "piston".length()); // Piston mode
				tokenList.add(new PistonToken(current));
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
			// Logger.log("[TASK] Wait Task", LogLevel.INFO);
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
				// TODO fix
				// taskSet.addTask(((HatchToken) t).makeTask());
			} else if (t instanceof SusanToken) {
				taskSet.addTask(((SusanToken) t).makeTask());
			} else if (t instanceof PistonToken) {
				taskSet.addTask(((PistonToken) t).makeTask());
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
			// Logger.log("AutoBuilder buildAutoMode method parseAuto printStackTrace",
			// LogLevel.ERROR);
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