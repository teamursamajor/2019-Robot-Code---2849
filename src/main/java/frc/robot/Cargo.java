package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.tasks.*;
import frc.tasks.CargoTask.CargoMode;

public class Cargo extends Subsystem<CargoTask.CargoMode> implements UrsaRobot {
    public static Spark cargoIntake;

    public Cargo() {
        cargoIntake = new Spark(CARGO_INTAKE);
    }

    public void runSubsystem() {
        // Cargo Intake
        if (xbox.getButton(controls.map.get("cargo_intake"))) {
            subsystemMode = CargoMode.IN;
        } else if (xbox.getButton(controls.map.get("cargo_outtake"))) {
            subsystemMode = CargoMode.OUT;
        } else {
            subsystemMode = CargoMode.WAIT;
        }

        switch (subsystemMode) {
        case IN:
            cargoIntake.set(0.55);
            break;
        case OUT:
            cargoIntake.set(-1.0);
            break;
        case WAIT:
            cargoIntake.set(0.0);
            break;
        }
    }
}
