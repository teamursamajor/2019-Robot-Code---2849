
package frc.robot;

// TODO unused import?
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Piston extends Subsystem<PistonTask.PistonMode> implements UrsaRobot {
    // TODO eventually set up pneumatics and incorporate into our architecture
    private Spark extend;

    public Piston() {
        extend = new Spark(EXTEND);

    }

    public void runSubsystem() {
        //updateStateInfo();
        //TurntableTask.TurntableOrder turntableOrder = subsystemMode.callLoop();
        
        if (xbox.getAxisGreaterThan(XboxController.AXIS_RIGHTTRIGGER, .1)){
           extend.set(0);
        }

        if (xbox.getAxisGreaterThan(XboxController.AXIS_LEFTTRIGGER, .1)){
      
        }

    }

}