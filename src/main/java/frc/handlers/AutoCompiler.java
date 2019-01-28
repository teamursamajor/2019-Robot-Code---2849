package frc.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.tasks.HatchTask.HatchMode;
import frc.tasks.CargoTask.CargoMode;
import frc.tasks.ClimbTask.ClimbMode;
import frc.tasks.DriveTask.DriveMode;
import frc.tasks.TurnTask.TurnMode;


import frc.tasks.*;

//TODO import logger when ready
//TODO hatch/cargo, turn/drive, climb, path (follow) code need to be added
//TODO Comment this out!

public class AutoCompiler {
	interface Token {
	}

	public AutoCompiler() {
		
	}

	class ExecuteToken implements Token {
		private String scriptName;

		public ExecuteToken(String scriptName) {
			this.scriptName = "/home/lvuser/automodes/" + scriptName.trim();
		}
	}

	/**
	 * PrintToken: A token that prints all arguments passed to it
	 * 
	 * @param str String that you want to print
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

    //TODO Implement with changes to path
	// class PathToken implements Token {
	// 	private Path[] paths;

	// 	public PathToken(String filename) {
	// 		filename = filename.replace(" ", "");
	// 		//put all paths into /automodes/paths
	// 		paths = new PathReader("/home/lvuser/automodes/paths/" + filename + ".path", false).getPaths();
	// 	}

	// 	public PathTask makeTask( ) {
	// 		Logger.log("[TASK] Path Task", LogLevel.INFO);
	// 		return new PathTask(, paths, drive);
	// 	}
	// }

	class CargoToken implements Token {
		private CargoMode cargo;

		public CargoToken(String cargoType) {
			cargoType = cargoType.replace(" ", "");
			// if (cargoType.equalsIgnoreCase("IN")) {
			// 	cargo = CargoMode.IN;
			// } else if (cargoType.equalsIgnoreCase("OUT")) {
			// 	cargo = CargoMode.OUT;
			// } else if (cargoType.equalsIgnoreCase("RUN")) {
			// 	cargo = CargoMode.RUN;
			// } else if (cargoType.equalsIgnoreCase("STOP")) {
			// 	cargo = CargoMode.STOP;
			// } else if (cargoType.equalsIgnoreCase("DEPLOY")) {
			// 	cargo = CargoMode.DEPLOY;
			// } else if (cargoType.equalsIgnoreCase("HOLD")) {
			// 	cargo = CargoMode.HOLD;
			// } else {
			// 	cargo = CargoMode.STOP;
			// }
		}

		public CargoTask makeTask() {
			// Logger.log("[TASK] Cargo Task", LogLevel.INFO);
            // return new CargoTask(cargo);
            return null;
		}
	}

	class HatchToken implements Token {
		private HatchMode hatch;

		public HatchToken(String hatchType) {
		    hatchType = hatchType.replace(" ", "");

			// if (hatchType.equalsIgnoreCase("BOTTOM")) {
			// 	hatch = HatchMode.BOTTOM;
			// } else if (hatchType.equalsIgnoreCase("VAULT")) {
			// 	hatch = HatchMode.VAULT;
			// } else if (hatchType.equalsIgnoreCase("SWITCH")) {
			// 	hatch = HatchMode.SWITCH;
			// } else if (hatchType.equalsIgnoreCase("SCALE")) {
			// 	hatch = HatchMode.SCALE;
			// } else {
			// 	hatch = HatchMode.BOTTOM;
			// }
		}

		public HatchTask makeTask() {
			// Logger.log("[TASK] Hatch Task", LogLevel.INFO);
            // return new HatchTask(HatchTask.presetToHeight(hatch), HatchTask.presetToTimeout(hatch));
            return null;
		}
    }
    
    class ClimbToken implements Token {
		private ClimbMode climb;

		public ClimbToken(String climbType) {
		    climbType = climbType.replace(" ", "");

			// if (climbType.equalsIgnoreCase("BOTTOM")) {
			// 	climb = ClimbMode.BOTTOM;
			// } else if (climbType.equalsIgnoreCase("VAULT")) {
			// 	climb = ClimbMode.VAULT;
			// } else if (climbType.equalsIgnoreCase("SWITCH")) {
			// 	climb = ClimbMode.SWITCH;
			// } else if (climbType.equalsIgnoreCase("SCALE")) {
			// 	climb = ClimbMode.SCALE;
			// } else {
			// 	climb = ClimbMode.BOTTOM;
			// }
		}

		public ClimbTask makeTask() {
			// Logger.log("[TASK] Climb Task", LogLevel.INFO);
            // return new ClimbTask(ClimbTask.presetToHeight(climb), ClimbTask.presetToTimeout(climb));
            return null;
		}
	}

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

	class BundleToken implements Token {
		public BundleToken() {
		}
	}

	class SerialToken implements Token {
		public SerialToken() {
		}
	}

	class RightBraceToken implements Token {
		public RightBraceToken() {
		}
	}

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

    //TODO Reformat this with all the DriveOrder DriveState stuff
		public TurnTask makeTask() {
			// Logger.log("[TASK] Turn Task", LogLevel.INFO);
            // return new TurnTask(turnType, turnAmount, turn);
            return null;
		}
	}

	class DriveToken implements Token {
		private double dist;

		public DriveToken(String distance) {
			distance = distance.replace(" ", "");
			if (Math.abs(Double.parseDouble(distance)) >= 0) {
				dist = Double.parseDouble(distance);
			}
		}

        // TODO Reformat with Drive stuff (see above)
		public DriveTask makeTask() {
			// Logger.log("[TASK] Drive Task", LogLevel.INFO);
            // return new DriveTask((int) dist, drive);
            return null;
		}
	}
	
	/**
	 * Interprets specified file to identify keywords as tokens to add to a collective ArrayList
	 * @param filename
	 * 		Name of file to tokenize
	 * @return ArrayList of all tokens in ranking order
	 * @throws IOException
	 */
	private ArrayList<Token> tokenize(String filename) throws IOException {
		ArrayList<Token> ret = new ArrayList<Token>();
		BufferedReader buff;
		buff = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = buff.readLine()) != null) {
            //TODO This is coming from the guy who wrote these comments last year. Please remove them. For my sake.
            //TODO Also uncomment turn, drive, path, whatever when those are all fixed
			if (line.contains("#")) { // If the line is a comment
				continue;
			// } else if (line.contains("follow")) { // If the line is a path token
			// 	String current = line.substring(line.indexOf("follow") + "follow".length());
			// 	// The path to follow (name of path file) is what comes after the token "follow"
			// 	ret.add(new PathToken(current)); // Adds new path token to the ArrayList of all tokens
			} else if (line.contains("execute")) { // If the line is an execute token
				String current = line.substring(line.indexOf("execute") + "execute".length());
				// The automode to execute (name of auto file) is what comes after the token "execute"
				ret.add(new ExecuteToken(current)); // Adds new execute token to the ArrayList of all tokens
			} else if (line.contains("wait")) { // If the line is a wait token
				String current = line.substring(line.indexOf("wait") + "wait".length());
				// The length (in seconds) to wait is what comes after the token "wait"
				ret.add(new WaitToken(current)); // Adds new wait token to the ArrayList of all tokens
			} else if (line.contains("drive")) { // If the line is a drive token
				String current = line.substring(line.indexOf("drive") + "drive".length());
				// The length (in feet) to drive is what comes after the token "drive"
				ret.add(new DriveToken(current)); // Adds new drive token to the ArrayList of all tokens
			} else if (line.contains("turn")) { // If the line is a turn token
				String current = line.substring(line.indexOf("turn") + "turn".length());
				// The type and amount in degrees to turn is what comes after the token "turn"
				ret.add(new TurnToken(current)); // Adds new turn token to the ArrayList of all tokens
			} else if (line.contains("hatch")) { // If the line is a hatch token
				String current = line.substring(line.indexOf("hatch") + "hatch".length());
				// The preset height to lift to is what comes after the token "lift"
				ret.add(new HatchToken(current)); // Adds new lift token to the ArrayList of all tokens
			} else if (line.contains("cargo")) { // If the line is a cargo token
				String current = line.substring(line.indexOf("cargo") + "cargo".length());
				// The cargo mode is what comes after the token "cargo"
				ret.add(new CargoToken(current)); // Adds new intake token to the ArrayList of all tokens
			} else if (line.contains("print")) { // If the line is a print token
				String current = line.substring(line.indexOf("print") + "print".length());
				// The data to print is everything that comes after the token "print"
				ret.add(new PrintToken(current)); // Adds new print token to the ArrayList of all tokens
			} else if (line.contains("bundle")) { // If the line is a bundle token
				ret.add(new BundleToken()); // Adds new bundle token to the ArrayList of all tokens
			} else if (line.contains("serial")) { // If the line is a serial token
				ret.add(new SerialToken()); // Adds new serial token to the ArrayList of all tokens
			} else if (line.contains("}")) { // If the line is a right brace (ends a group task)
				ret.add(new RightBraceToken()); // Adds new right brace token to the ArrayList of all tokens
			}
		}
		buff.close();
		return ret; // Returns ArrayList of all tokens
	}

	private Task parseAuto(ArrayList<Token> toks, GroupTask ret) {
		if (toks.size() == 0) {
			// Logger.log("[TASK] Wait Task", LogLevel.INFO);
			return new WaitTask(0);
		}

		while (toks.size() > 0) {
			Token t = toks.remove(0);
			if (t instanceof ExecuteToken) {
				Task otherMode = buildAutoMode(((ExecuteToken) t).scriptName);
				ret.addTask(otherMode);
			} else if (t instanceof WaitToken) {
				ret.addTask(((WaitToken) t).makeTask());
			} else if (t instanceof DriveToken) {
				// ret.addTask(((DriveToken) t).makeTask());
			// } else if (t instanceof TurnToken) {
			// 	ret.addTask(((TurnToken) t).makeTask());
			// } else if (t instanceof PathToken) {
			// 	ret.addTask(((PathToken) t).makeTask());
			} else if (t instanceof CargoToken) {
				ret.addTask(((CargoToken) t).makeTask());
			} else if (t instanceof HatchToken) {
				ret.addTask(((HatchToken) t).makeTask());
			} else if (t instanceof BundleToken) {
				BundleTask p = new BundleTask();
				parseAuto(toks, p);
				ret.addTask(p);
			} else if (t instanceof SerialToken) {
				SerialTask p = new SerialTask();
				parseAuto(toks, p);
				ret.addTask(p);
			} else if (t instanceof RightBraceToken) {
				return ret;
			} else if (t instanceof PrintToken) {
				ret.addTask(((PrintToken) t).makeTask());
			}
		}

		return ret;
	}

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