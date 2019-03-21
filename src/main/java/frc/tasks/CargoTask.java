package frc.tasks;
import frc.robot.*;
import java.util.Map;
import java.util.HashMap;

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
            double voltageTolerance = 0.25;

            // System.out.println("desired volt: " + desiredVoltage);

            if(Math.abs(CargoState.cargoVoltage - desiredVoltage) <= voltageTolerance) {
                running = false;
                // System.out.println("running is false");
                return new CargoOrder(Cargo.getHoldPower());
            }

            // TODO determine critical point (where it just oscillates forever) and period (time between oscillations)
            double kcCargo = 0.0;
            double tcCargo = 0.0;

            double kpCargo = 0.6*kcCargo;

            // double kiCargo = 1.2*kcCargo/tcCargo;
            double kdCargo = 3.0/40*kcCargo*tcCargo;

            // TODO reconfigure these based on new findings
            double cargoDownMinimumPower = 0.15;
            double cargoDownMaxPower = .2;
            double cargoUpMinimumPower = 0.5;

            // Proportional constant * (angle error) + derivative constant * velocity (aka pos / time)
            // System.out.println("Cargo Velocity: " + CargoState.cargoVelocity);
			double cargoPower = kpCargo * (desiredVoltage - CargoState.cargoVoltage) + feedForward(getCargoAngle(CargoState.cargoVoltage)) + kdCargo * CargoState.cargoVelocity;
            
            // if(cargoPower <= 0.1){
            //     running = false;
            //     return new CargoOrder(Cargo.getHoldPower());
            // }

            if (cargoPower < 0 && Math.abs(cargoPower) < cargoUpMinimumPower) { // going up
                System.out.println("PID Power too weak, using cargoMinimumPower");
				cargoPower = Math.signum(cargoPower) * cargoUpMinimumPower;
            } else if (cargoPower > 0 && Math.abs(cargoPower) < cargoDownMinimumPower){
                cargoPower = Math.signum(cargoPower) * cargoDownMinimumPower;
            }
            if(cargoPower > 0 && Math.abs(cargoPower) > cargoDownMaxPower){
                cargoPower = cargoDownMaxPower;
            }
            
            System.out.println("Cargo Power: " + cargoPower);
			return new CargoOrder(cargoPower); // returns negative because negative goes up, pos goes down
       }

       private double getCargoAngle(double voltage) {
           // TODO determine linear regression
           return 0.0;
       }

       private final double cargoMass = 4.6947; //kilograms
       private final double cargoRadius = .365; //meters
       private final double torqueCoefficient = cargoMass * cargoRadius * 9.81;
       private final double voltToPowerRegression = 6.38982;
       private final double motorRange = 12.0;

       private double feedForward(double angle) {
           return torqueCoefficient*voltToPowerRegression*Math.cos(angle)/motorRange;
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