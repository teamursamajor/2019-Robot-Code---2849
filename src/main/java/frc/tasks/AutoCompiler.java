package frc.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tasks.HatchTask.HatchMode;
import frc.tasks.CargoTask.CargoMode;
import frc.tasks.TurnTask.TurnMode;
import frc.tasks.SusanTask.SusanMode;
import frc.robot.*;

//TODO import logger when ready
//TODO hatch/cargo, turn/drive, path (follow) code need to be added!

public class AutoCompiler {
	interface Token {
	}

	private Drive drive;
	private Cargo cargo;
	private Hatch hatch;
	private LazySusan susan;
	public AutoCompiler(Drive drive, Cargo cargo, Hatch hatch, LazySusan susan) {
		this.drive = drive;
		this.cargo = cargo;
		this.hatch = hatch;
		this.susan = susan;
	}

	/**
	 * A token that executes a given auto file
	 * 
	 * @param scriptName Name of the file to execute
	 */
	class ExecuteToken implements Token {
		private String scriptName;

		public ExecuteToken(String scriptName) {
			// TODO update path directory
			this.scriptName = "/home/lvuser/automodes/" + scriptName.trim();
		}
	}

	/**
	 * A token that prints all arguments passed to it to the console
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
		public PrintTask makeTask( ) {
			// Logger.log("[TASK] Print Task", LogLevel.INFO);
			return new PrintTask(str);
		}
	}

	/**
	 * A token that runs a given path file
	 * 
	 * @param filename Path file to run
	 */
    //TODO Implement with changes to path
	class PathToken implements Token {
		// private Path[] paths;

		public PathToken(String filename) {
			filename = filename.replace(" ", "");
			//TODO put all paths into /automodes/paths
			// paths = new PathReader("/home/lvuser/automodes/paths/" + filename + ".path", false).getPaths();
		}

		public PathTask makeTask() {
			// Logger.log("[TASK] Path Task", LogLevel.INFO);
			// return new PathTask(paths);
			return null;
		}
	}

	/**
	 * A token that sets the cargo to a given mode
	 * 
	 * @param cargoType Cargo mode to run by
	 */
	class CargoToken implements Token {
		private CargoMode cargoMode;

		public CargoToken(String cargoType) {
			cargoType = cargoType.replace(" ", "");
			if (cargoType.equalsIgnoreCase("DEPLOY")) {
				cargoMode = CargoMode.DEPLOY;
			} else if (cargoType.equalsIgnoreCase("PICKUP")) {
				cargoMode = CargoMode.PICKUP;
			} else if (cargoType.equalsIgnoreCase("DROPOFF")) {
				cargoMode = CargoMode.DROPOFF;
			} else {
				// cargo = default case
			}
		}

		public CargoTask makeTask() {
			// Logger.log("[TASK] Cargo Task", LogLevel.INFO);
            // return new CargoTask(cargo);
            return null;
		}
	}

	/**
	 * A token that sets the hatch position to a given mode
	 * 
	 * @param hatchType The hatch position to get to
	 */
	class HatchToken implements Token {
		private HatchMode hatch;

		public HatchToken(String hatchType) {
		    hatchType = hatchType.replace(" ", "");

			if (hatchType.equalsIgnoreCase("START")) {
				hatch = HatchMode.START;
			} else if (hatchType.equalsIgnoreCase("BOTTOM")) {
				hatch = HatchMode.BOTTOM;
			} else if (hatchType.equalsIgnoreCase("TOP")) {
				hatch = HatchMode.TOP;
			} else {
				hatch = HatchMode.BOTTOM;
			}
		}

		public HatchTask makeTask() {
			// Logger.log("[TASK] Hatch Task", LogLevel.INFO);
            // return new HatchTask(hatch);
            return null;
		}
	}
	
	/**
	 * A token that moves the lazy susan to a given direction
	 * 
	 * @param susanType The direction to turn the lazy susan to
	 */
	class SusanToken implements Token {
		private SusanMode susan;

		public SusanToken(String susanType) {
			susanType = susanType.replace(" ", "");
			
			if (susanType.equalsIgnoreCase("FORWARD")) {
				susan = SusanMode.FORWARD;
			} else if (susanType.equalsIgnoreCase("LEFT")) {
				susan = SusanMode.LEFT;
			} else if (susanType.equalsIgnoreCase("RIGHT")) {
				susan = SusanMode.RIGHT;
			} else {
			// 	susan = default case
			}
		}

		public SusanTask makeTask() {
			// Logger.log("[TASK] Susan Task", LogLevel.INFO);
            // return new SusanTask(susan);
            return null;
		}
    }

	/**
	 * A token that delays the auto mode for a duration passed to it
	 * 
	 * @param waitTime The time to wait
	 */
	class WaitToken implements Token {
		private double wait;

		public WaitToken(String waitTime) {
			waitTime = waitTime.replace(" ", "");
			if (Double.parseDouble(waitTime) >= 0) {
				wait = Double.parseDouble(waitTime);
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
	 * A token that turns the robot to face an absolute or relative direction
	 * 
	 * @param turn The type and amount to turn ("to" or "by" and degrees)
	 */
	class TurnToken implements Token {
		private double turnAmount;
		private TurnMode turnType;

		public TurnToken(String turn) {
			turn = turn.toLowerCase();
			if (turn.contains("to")) {
				turnType = TurnMode.TURN_TO;
				turnAmount = Double.valueOf(turn.substring(turn.indexOf("to") + "TO".length()));
			} else {
				turnType = TurnMode.TURN_BY;
				turnAmount = Double.valueOf(turn.substring(turn.indexOf("by") + "BY".length()));
			}
		}

		public TurnTask makeTask() {
			// Logger.log("[TASK] Turn Task", LogLevel.INFO);
            // return new TurnTask(turnType, turnAmount);
            return null;
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
			if (Math.abs(Double.parseDouble(distance)) >= 0) {
				dist = Double.parseDouble(distance);
			}
		}

		public DriveTask makeTask() {
			// Logger.log("[TASK] Drive Task", LogLevel.INFO);
            // return new DriveTask((int) dist, drive);
            return null;
		}
	}
	
	/**
	 * Interprets specified file to identify keywords as tokens to add to a collective ArrayList
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
			if (line.contains("#")) { //# means a comment, so the tokenizer ignores lines beginning with it
				continue;
			} else if (line.contains("follow")) {
				String current = line.substring(line.indexOf("follow") + "follow".length()); //Path to follow (file name)
				tokenList.add(new PathToken(current));
			} else if (line.contains("execute")) {
				String current = line.substring(line.indexOf("execute") + "execute".length()); //Automode to execute (file name)
				tokenList.add(new ExecuteToken(current));
			} else if (line.contains("wait")) {
				String current = line.substring(line.indexOf("wait") + "wait".length()); //Wait length (seconds)
				tokenList.add(new WaitToken(current));
			} else if (line.contains("drive")) {
				String current = line.substring(line.indexOf("drive") + "drive".length()); //Drive length (feet)
				tokenList.add(new DriveToken(current));
			} else if (line.contains("turn")) {
				String current = line.substring(line.indexOf("turn") + "turn".length()); //Turn type/angle
				tokenList.add(new TurnToken(current));
			} else if (line.contains("hatch")) {
				String current = line.substring(line.indexOf("hatch") + "hatch".length()); //Hatch mode
				tokenList.add(new HatchToken(current));
			} else if (line.contains("cargo")) {
				String current = line.substring(line.indexOf("cargo") + "cargo".length()); //Cargo mode
				tokenList.add(new CargoToken(current));
			} else if (line.contains("susan")) {
				String current = line.substring(line.indexOf("susan") + "susan".length()); //Susan mode
				tokenList.add(new SusanToken(current));
			} else if (line.contains("print")) {
				String current = line.substring(line.indexOf("print") + "print".length()); //Text to print
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
	 * @param taskSet A set of tasks to add tasks to
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
			} else if (t instanceof PathToken) {
				taskSet.addTask(((PathToken) t).makeTask());
			} else if (t instanceof CargoToken) {
				taskSet.addTask(((CargoToken) t).makeTask());
			} else if (t instanceof HatchToken) {
				taskSet.addTask(((HatchToken) t).makeTask());
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
			// Logger.log("AutoBuilder buildAutoMode method parseAuto printStackTrace", LogLevel.ERROR);
			return null;
		}
    }
    
    //TODO Adapt this when we need it
    // 	/**
	//  * Gets the switch/scale side from the FMS, and finds an Auto Mode file
	//  * which finds robot's position and switch/scale ownership and performs the
	//  * highest task in the ranked list of tasks from the SmartDashboard that
	//  * matches the current setup.
	//  * 
	//  * @param robotPosition
	//  *            The side our robot starts on. L, M, or R.
	//  * @param autoPrefs
	//  *            String array of ranked Auto Modes
	//  * @param autoFiles
	//  *            File array of all files in the AutoModes folder
	//  * @return String name of the auto file to run
	//  */
	// public String pickAutoMode(char robotPosition, String[] autoPrefs, File[] autoFiles) {
	// 	// Gets the ownership information from the FMS
	// 	String switchPos = DriverStation.getInstance().getGameSpecificMessage().substring(0,1);
	// 	String scalePos = DriverStation.getInstance().getGameSpecificMessage().substring(1,2);
	// 	String mode;
	// 	System.out.println(switchPos + scalePos);
		
	// 	SmartDashboard.putString("Switch side: ", switchPos);
	// 	SmartDashboard.putString("Scale side: ", scalePos);
		
	// 	switch (switchPos + scalePos) {
	// 	case "LL":
	// 		mode = autoPrefs[0];
	// 		break;
	// 	case "LR":
	// 		mode = autoPrefs[1];
	// 		break;
	// 	case "RL":
	// 		mode = autoPrefs[2];
	// 		break;
	// 	case "RR":
	// 		mode = autoPrefs[3];
	// 		break;
	// 	default:
	// 		mode = "path_drive";
	// 		break;
	// 	}
		
	// 	//for potential future use
	// 	String oppSide = DriverStation.getInstance().getGameSpecificMessage().substring(2);
		
	// 	String regex = "^[" + robotPosition + "0]_[" + switchPos + "0][" + scalePos + "0]_" + mode + "\\.auto$";
		
	// 	for (File f: autoFiles) {
	// 		if (f.getName().matches(regex)) {
	// 			return "/home/lvuser/automodes/" + f.getName();
	// 		}
	// 	}
	// 	return "/home/lvuser/automodes/0_00_path_drive.auto";
	// }
}