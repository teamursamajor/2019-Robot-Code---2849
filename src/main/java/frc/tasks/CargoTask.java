package frc.tasks;
import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot{
	public enum CargoMode {
        DEPLOY, PICKUP, DROPOFF;

        public CargoOrder callLoop() {
            switch (this) {
                // TODO change these to their actual distance
            case DEPLOY:
                return moveToDistance(0.0);
            case PICKUP:
                return moveToDistance(0.0);
            case DROPOFF:
                return moveToDistance(0.0);
            }
            running = false;
            return new CargoOrder(0.0);  
        }

       private CargoOrder moveToDistance(double distance) {

            double distanceTolerance = 5;
            if(Math.abs(CargoState.position - distance) < distanceTolerance) {
                running = false;
                return new CargoOrder(0.0);
            }
            
            //TODO Add derivative term to PD loop
            double kpCargo = 1.0 / 40.0;
            double kdCargo = 0;

			double cargoMinimumPower = 0.2; //TODO Optimize
			// Proportional constant * (angle error) + derivative constant * velocity (aka pos / time)
			double cargoPower = kpCargo * (distance - CargoState.position) + kdCargo * CargoState.velocity;

            if(cargoPower == 0){
                running = false;
                return new CargoOrder(0.0);
            }

			if (Math.abs(cargoPower) < cargoMinimumPower) {
				cargoPower = Math.signum(cargoPower) * cargoMinimumPower;
			}
            
			return new CargoOrder(cargoPower);
       }
    }

    public static class CargoState {
        public static double position = 0.0;
        public static double velocity = 0.0;
        public static long stateTime = System.currentTimeMillis();

        public static void updateState(double velocity, double position) {
            CargoState.velocity = velocity;
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

    private static boolean running = true;

    public CargoTask(CargoMode mode, Cargo cargo){
        running = true;
        cargo.setMode(mode);
        Thread t = new Thread("CargoTask");
        t.start();
    }

	public void run(){
        while(running){
            try{
                Thread.sleep(20);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
	}
}