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
import frc.tasks.DriveTask.DriveMode;
import frc.tasks.TurnTask.TurnMode;
import frc.tasks.SusanTask.SusanMode;


import frc.tasks.*;

//TODO import logger when ready
//TODO hatch/cargo, turn/drive, path (follow) code need to be added
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
	class PathToken implements Token {
		// private Path[] paths;

		public PathToken(String filename) {
			filename = filename.replace(" ", "");
			//TODO put all paths into /automodes/paths
			// paths = new PathReader("/home/lvuser/automodes/paths/" + filename + ".path", false).getPaths();
		}

		public PathTask makeTask() {
			// Logger.log("[TASK] Path Task", LogLevel.INFO);
			// return new PathTask(, drive);
			return null;
		}
	}

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
	
	class SusanToken implements Token {
		private SusanMode susan;

		public SusanToken(String susanType) {
		    susanType = susanType.replace(" ", "");
		}

		public SusanTask makeTask() {
			// Logger.log("[TASK] Susan Task", LogLevel.INFO);
            // return new SusanTask();
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
		ArrayList<Token> tokenList = new ArrayList<Token>();
		BufferedReader buff;
		buff = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = buff.readLine()) != null) {
			if (line.contains("#")) { //# means a comment
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
			} else if (line.contains("}")) { //Right brace ends a group task (bundle/serial)
				tokenList.add(new RightBraceToken());
			}
		}
		buff.close();
		return tokenList; // Returns ArrayList of all tokens
	}

	private Task parseAuto(ArrayList<Token> toks, GroupTask tokenList) {
		if (toks.size() == 0) {
			// Logger.log("[TASK] Wait Task", LogLevel.INFO);
			return new WaitTask(0);
		}

		while (toks.size() > 0) {
			Token t = toks.remove(0);
			if (t instanceof ExecuteToken) {
				Task otherMode = buildAutoMode(((ExecuteToken) t).scriptName);
				tokenList.addTask(otherMode);
			} else if (t instanceof WaitToken) {
				tokenList.addTask(((WaitToken) t).makeTask());
			} else if (t instanceof DriveToken) {
				// tokenList.addTask(((DriveToken) t).makeTask());
			// } else if (t instanceof TurnToken) {
			// 	tokenList.addTask(((TurnToken) t).makeTask());
			// } else if (t instanceof PathToken) {
			// 	tokenList.addTask(((PathToken) t).makeTask());
			} else if (t instanceof CargoToken) {
				tokenList.addTask(((CargoToken) t).makeTask());
			} else if (t instanceof HatchToken) {
				tokenList.addTask(((HatchToken) t).makeTask());
			} else if (t instanceof BundleToken) {
				BundleTask p = new BundleTask();
				parseAuto(toks, p);
				tokenList.addTask(p);
			} else if (t instanceof SerialToken) {
				SerialTask p = new SerialTask();
				parseAuto(toks, p);
				tokenList.addTask(p);
			} else if (t instanceof RightBraceToken) {
				return tokenList;
			} else if (t instanceof PrintToken) {
				tokenList.addTask(((PrintToken) t).makeTask());
			}
		}

		return tokenList;
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