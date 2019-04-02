package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;
// import frc.tasks.CargoTask.CargoMode;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {
    public static Spark cargoIntake;

    public Cargo() {
        cargoIntake = new Spark(CARGO_INTAKE); 
    }

    public void runSubsystem() {
        updateStateInfo();

        // Cargo Intake
        if (xbox.getButton(controls.map.get("cargo_intake"))) {
            cargoIntake.set(0.55);
        } else if (xbox.getButton(controls.map.get("cargo_outtake"))) {
            cargoIntake.set(-1.0);
        } else {
            cargoIntake.set(0.0);
        }
    }

    public void updateStateInfo() {
        double deltaTime = System.currentTimeMillis() - CargoTask.CargoState.stateTime;

        if (deltaTime <= 5)
            return;
    }
}

