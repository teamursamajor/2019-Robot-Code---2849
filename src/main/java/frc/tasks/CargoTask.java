package frc.tasks;
import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot{
	public enum CargoMode {
        DEPLOY, PICKUP, DROPOFF;

        public CargoOrder callLoop() {
            // "this" refers to the enum that the method is in
            switch (this) {
                // TODO change these to their actual distance
            case DEPLOY:
                return moveToDistance(0.0);
            case PICKUP:
                return moveToDistance(0.0);
            case DROPOFF:
                return moveToDistance(0.0);
            }
            return new CargoOrder(0.0);  
        }

       private CargoOrder moveToDistance(double distance) {

            double distanceTolerance = 5;
            if(Math.abs(CargoState.position - distance) < distanceTolerance) {
                return new CargoOrder(0.0);
            }
            //TODO Add derivative term to PD loop
			double kpCargo = 1.0 / 40.0;
			double cargoMinimumPower = 0.2;
			// Proportional constant * (angle error) + derivative constant * velocity (aka pos / time)
			double cargoPower = kpCargo * (distance - CargoState.position);

			if (Math.abs(cargoPower) < cargoMinimumPower) {
				cargoPower = Math.signum(cargoPower) * cargoMinimumPower;
			}
			
			return new CargoOrder(cargoPower);
       }
    }

    public static class CargoState {
        static double position = 0.0;
        static long stateTime = System.currentTimeMillis();

        public static void updateState(double position) {
            CargoState.position = position;
            stateTime = System.currentTimeMillis();
        }
    }

    public static class CargoOrder {
        public double cargoPower = 0.0;

        public CargoOrder(double power) {
            cargoPower = power;
        }

    }

	public String toString() {
        // return "CargoTask: " + cargo.name() + "\n";
        return " ";
	}

	public void run(){
		
	}
}