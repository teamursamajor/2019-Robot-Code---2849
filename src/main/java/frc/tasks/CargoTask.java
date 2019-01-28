package frc.tasks;
import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot{
	public enum CargoMode {
        Auto, DriveSticks;

        public CargoOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
            case Auto:
                return autoCalculator();
            case DriveSticks:
                return sticksBox();
            }
            return new CargoOrder(0.0);  
        }

        private CargoOrder autoCalculator() {
            return new CargoOrder(0.0);
        }
        private CargoOrder sticksBox() {
            if (xbox.getButton(XboxController.BUTTON_A)){
                return new CargoOrder(.5);
            }
            else{
                return new CargoOrder(0);
            }
        }
    }

    static class CargoState {
        static double power = 0.0, position = 0.0;
        static long stateTime = System.currentTimeMillis();

        public static void updateState(double power, double position) {
            CargoTask.CargoState.power = power;
            CargoTask.CargoState.position = position;
            stateTime = System.currentTimeMillis();
        }
    }

    static class CargoOrder {
        double power = 0.0;

        public CargoOrder(double power) {
            this.power = power;
        }

    }

	public String toString() {
        // return "CargoTask: " + cargo.name() + "\n";
        return " ";
	}

	public void run(){
		
	}
}