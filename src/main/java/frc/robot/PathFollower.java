package frc.robot;

import java.io.IOException;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

/**
 * This will use Jaci to follow the paths drawn in PathWeaver. This still needs
 * to be optimized and is not ready for competitions yet.
 */
public class PathFollower extends TimedRobot implements UrsaRobot {
    private static final int k_ticks_per_rev = 1024;

    private static final double k_wheel_diameter = 0.2032; // meters
    private static final double k_max_velocity = 160;

    private SpeedController m_left_motor, m_right_motor;

    private Encoder m_left_encoder, m_right_encoder;

    private Drive drive;

    private EncoderFollower leftFollower, rightFollower;

    private Notifier followNotifier;
    private Trajectory lTrajectory, rTrajectory;

    /**
     * 
     * @param leftEncoder  encoder on the left side
     * @param rightEncoder encoder on the right side
     * @param leftMotor    motor on the left side
     * @param rightMotor   motor on the right side
     */
    public PathFollower(Drive drive) {
        this.drive = drive;
        m_left_encoder = UrsaRobot.leftEncoder;
        m_right_encoder = UrsaRobot.rightEncoder;
        m_left_motor = Drive.mFrontLeft;
        m_right_motor = Drive.mFrontRight;
    }

    /**
     * Call this to follow a path. It configures the encoder followers and the PID
     * loop
     * 
     * @param path Name of the path
     * @throws IOException
     */
    public void pathInit(String path) throws IOException {
        lTrajectory = PathfinderFRC.getTrajectory(path + ".right");
        rTrajectory = PathfinderFRC.getTrajectory(path + ".left");

        leftFollower = new EncoderFollower(lTrajectory);
        rightFollower = new EncoderFollower(rTrajectory);

        //TODO - find these values imperiacally
        double kp = 0.0; //should be between .8 and 1.0
        double ki = 0.0; //currently unused
        double kd = 0.0; //adjust accuracy of path
        double kv = 1/k_max_velocity;
        double ka = 0.0; //default is 0
        setFollowers(kp,ki,kd,kv,ka);

    }

    private void setFollowers(double kp, double ki, double kd, double kv, double ka){
        leftFollower.configureEncoder(m_left_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
        leftFollower.configurePIDVA(kp, ki, kd, kv, ka);

        rightFollower.configureEncoder(m_right_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
        rightFollower.configurePIDVA(kp, ki, kd, kv, ka);

        followNotifier = new Notifier(this::followPath);
        followNotifier.startPeriodic(lTrajectory.get(0).dt);
    }

    private void followPath() {
        if (leftFollower.isFinished() || rightFollower.isFinished()) {
            followNotifier.stop();
        } else {
            double left_speed = leftFollower.calculate(leftEncoder.get());
            double right_speed = rightFollower.calculate(rightEncoder.get());
            double heading = drive.getHeading();

            // this may need to be negative, try it if the robot is turning the wrong way or something
            double desired_heading = Pathfinder.r2d(leftFollower.getHeading());

            double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
            
            // these numbers are from WPILIB, dont know why/where they came from
            double turn = 0.8 * (-1.0 / 80.0) * heading_difference;
            
            m_left_motor.set(left_speed + turn);
            m_right_motor.set(right_speed - turn);
        }
    }

    public void killPath() {
        followNotifier.stop();
        m_left_motor.set(0);
        m_right_motor.set(0);
        Drive.stop();
    }
}