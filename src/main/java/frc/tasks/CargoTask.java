package frc.tasks;
import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot{
	public enum CargoMode {
        GROUND, CARGOBAY, LOWROCKET, CLIMB;

        public CargoMode getNext(){
            return this.ordinal() < CargoMode.values().length - 1
                ? CargoMode.values()[this.ordinal() + 1]
                : GROUND;
        }

        public CargoMode getPrevious(){
            return this.ordinal() > 0
                ? CargoMode.values()[this.ordinal() - 1]
                : LOWROCKET;
        }

        public CargoOrder callLoop() {
            switch (this) {
            case GROUND:
                return moveToAngle(Cargo.cargoGroundVoltage);
            case LOWROCKET:
                return moveToAngle(Cargo.cargoLowRocketVoltage);
            case CARGOBAY:
                return moveToAngle(Cargo.cargoBayVoltage);
            case CLIMB:
                return moveToAngle(Cargo.cargoStartVoltage);
            }
            running = false;
            return new CargoOrder(0.0);  
        }

       private CargoOrder moveToAngle(double desiredVoltage) {
            double voltageTolerance = 5.0;

            if(Math.abs(CargoState.cargoVoltage - desiredVoltage) <= voltageTolerance) {
                running = false;
                return new CargoOrder(Cargo.getHoldPower());
            }
            //TODO Add derivative term to PD loop
            double kpCargo = 1.0 / 100.0;
            double kdCargo = 0;
			double cargoMinimumPower = 0.15;

            // Proportional constant * (angle error) + derivative constant * velocity (aka pos / time)
            System.out.println("Cargo Velocity: " + CargoState.cargoVelocity);
			double cargoPower = kpCargo * (desiredVoltage - CargoState.cargoVoltage); // + kdCargo * CargoState.cargoVelocity;
            
            // if(cargoPower <= 0.1){
            //     running = false;
            //     return new CargoOrder(Cargo.getHoldPower());
            // }

			if (Math.abs(cargoPower) < cargoMinimumPower) {
                System.out.println("PID Power too weak, using cargoMinimumPower");
				cargoPower = Math.signum(cargoPower) * cargoMinimumPower;
			}
            
			return new CargoOrder(-cargoPower); // returns negative because negative goes up, pos goes down
       }
    }

    public static class CargoState {
        public static double cargoVelocity = 0.0, cargoVoltage = 0.0;
        public static long stateTime = System.currentTimeMillis();
        
        public static void updateState( double cargoVelocity, double cargoVoltage) {
            CargoState.cargoVelocity = cargoVelocity;
            CargoState.cargoVoltage = cargoVoltage;
            stateTime = System.currentTimeMillis();
        }
        
    }

    public static class CargoOrder {
        public double cargoPower;

        public CargoOrder(double power) {
            this.cargoPower = power;
        }
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
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}