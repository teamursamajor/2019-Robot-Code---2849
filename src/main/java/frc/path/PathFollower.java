// package frc.path;

// // import org.usfirst.frc.team2849.diagnostics.Logger;
// // import org.usfirst.frc.team2849.diagnostics.Logger.LogLevel;
// // import org.usfirst.frc.team2849.util.Path;

// public class PathFollower {

// 	private double kp;
// 	private double ki;
// 	private double kd;
// 	private double kv;
// 	private double ka;
// 	private double kturn;

// 	private double error;
// 	private double prevError;

// 	public PathFollower(double kp, double ki, double kd, double kv, double ka, double kturn) {
// 		this.kp = kp;
// 		this.ki = ki;
// 		this.kd = kd;
// 		this.kv = kv;
// 		this.ka = ka;
// 		this.kturn = kturn;
// 		prevError = 0;
// 	}

// 	public double getCorrection(Path path, double distance, double time) {
// 		PointonPath point = path.findNextPointDist(distance);
// 		error = point.getPosition() - distance;
// 		// Logger.log("   Point Position: " + point.getPosition(), LogLevel.DEBUG);
// 		// Logger.log("   Point time: " + point.getTime(), LogLevel.DEBUG);
// 		// Logger.log("   Distance: " + distance, LogLevel.DEBUG);
// 		// Logger.log("   Error: " + error, LogLevel.DEBUG);
// 		// Logger.log("   Velocity: " + point.getVelocity(), LogLevel.DEBUG);
// 		// Logger.log("   Acceleration: " + point.getAccel(), LogLevel.DEBUG);
// 		double out = kp * error + kd * (point.getVelocity() - (error - prevError) / path.getDt())
// 				+ kv * point.getVelocity() + ka * point.getAccel();
// 		prevError = error;
// 		out = constrain(out);
// 		out *= path.isReversed() ? -1: 1;
// 		return out;
// 	}
	
// 	public double getSteering(PointonPath point, double heading) {
// //		System.out.println("   Heading: " + heading);
// //		System.out.println("   Point Heading: " + point.getDirection());
// 		return kturn * Path.getSmallestAngleBetween(heading, point.getDirection());
// 	}
	
// <<<<<<< HEAD
// // 	public double getDoubleSteering(RobotStats point, RobotStats secondPoint, double heading) {
// =======
// // 	public double getDoubleSteering(PointonPath point, PointonPath secondPoint, double heading) {
// // 		// Logger.log("   Second Point Heading: " + secondPoint.getDirection(), LogLevel.DEBUG);
// // 		if (Math.abs(smallestAngle) >= 0) 
// // 			return kturn * smallestAngle + .25 * kturn * Path.getSmallestAngleBetween(heading, secondPoint.getDirection());
// // 		else return 0;
// // 	}
	
// // 	public double constrain(double power) {
// // 		if (power > 1) {
// // 			return 1;
// // 		} else if (power < 0) {
// // 			return 0;
// // 		} else return power;
// <<<<<<< HEAD
// //     }
// //     //NEED Vel, Direction(DEFINED), Time(), Accel, xyfeetdefined
// =======
// // 	}
// >>>>>>> 2e57ee82008925163b977d1be63767af0edd55d4

// }