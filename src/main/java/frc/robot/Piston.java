
package frc.robot;

// TODO unused import?
import frc.tasks.*;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Piston extends Subsystem<PistonTask.PistonMode> implements UrsaRobot {
    // TODO eventually set up pneumatics and incorporate into our architecture

    private Solenoid solenoid;
    private Compressor compressor;

    private boolean compressingAir = false;
    private boolean solenoidOpen = false;

    public Piston() {
        solenoid = new Solenoid(PISTON_PORT);
        compressor = new Compressor(0);
    }

    public void runSubsystem() {
        
        if (xbox.getAxisGreaterThan(XboxController.AXIS_RIGHTTRIGGER, .1)){
            compressingAir = !compressingAir;
            compressor.setClosedLoopControl(compressingAir);
        }

        if (xbox.getAxisGreaterThan(XboxController.AXIS_LEFTTRIGGER, .1)){
            solenoid.set(solenoidOpen);
            solenoidOpen = !solenoidOpen;
        }

    }

}