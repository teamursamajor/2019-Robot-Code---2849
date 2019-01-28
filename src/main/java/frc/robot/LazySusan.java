package frc.robot;

import frc.tasks.*;
import edu.wpi.first.wpilibj.Spark;

public class LazySusan extends Subsystem<SusanTask.SusanMode> implements UrsaRobot{

    private Spark susanMotor;

    public LazySusan(){
        super("lazySusanThread");
        susanMotor = new Spark(LAZY_SUSAN);
    }

    public void runSubsystem(){
        
    }
}