package frc.tasks;
import frc.robot.*;

public class CargoTask extends Task implements UrsaRobot{
	public enum CargoMode {
        LOWROCKET, MIDDLEROCKET, CARGOBAY, PICKUP, TOP, CUSTOM;

        public CargoOrder callLoop() {
            switch (this) {
                // TODO change these to their actual distance
            case TOP:
                return moveToAngle(UrsaRobot.cargoTopVoltage);
            case PICKUP:
                return moveToAngle(UrsaRobot.cargoBottomVoltage);
            case LOWROCKET:
                return moveToAngle(UrsaRobot.lowRocketVoltage);
            case MIDDLEROCKET:
                return moveToAngle(UrsaRobot.middleRocketVoltage);
            case CARGOBAY:
                return moveToAngle(UrsaRobot.cargoBayVoltage);
            case CUSTOM:
                return moveToAngle(0);
            }
            running = false;
            return new CargoOrder(0.0);  
        }

        //TODO change from distance to voltage
       private CargoOrder moveToAngle(double desiredVoltage) {

            double voltageTolerance = 0.0; //TODO FInd
            if(Math.abs(CargoState.cargoVoltage - desiredVoltage) <= voltageTolerance) {
                running = false;
                return new CargoOrder(CargoState.cargoPower);//potieometer value
            }
            
            //TODO Add derivative term to PD loop
            double kpCargo = 1.0 / 40.0;
            double kdCargo = 0;
			double cargoMinimumPower = 0.2; //TODO Optimize
			// Proportional constant * (angle error) + derivative constant * velocity (aka pos / time)
			double cargoPower = kpCargo * (desiredVoltage - CargoState.cargoVoltage) + kdCargo * CargoState.cargoVelocity;

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
        public static double cargoVelocity = 0.0, cargoVoltage = 0.0, cargoPower = 0.0;
        public static long stateTime = System.currentTimeMillis();
        
        public static void updateState(double cargoPower, double cargoVelocity, double cargoVoltage) {
            CargoState.cargoPower = cargoPower;
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
    

    /**
     * 
     * //get current voltage method
     * 
     * stayAtAngle(voltage){
     * 
     * 
     * 
     * }
     * 
     * 
     * stayAtAngle(currentAngle);
     * 
     * 
     */
//HOPEFULLY WORKING HATCH PSUEDO CO
    // public static void stayAtCurrentAngle(double theValueOfThePotWhenYouStopPressingTheButton){
    //     double desiredAngle = theValueOfThePotWhenYouStopPressingTheButton;
    //     double angleError = .01;
    //     //untile the select/start buttons are pressed adjust the
    //     while (!START_BUTTON_PRESSED_DOWN || !SELECT_BUTTON_PRESSED_DOWN){
    //         double currentAngle = GET_POT_VALUE;
    //         if (CURRENT_ANGLE - angleError > desiredAngle ){
    //             setMotorPower to currentMotorPower--;
    //         } else if (CURRENT_ANGLE + angleError < desiredAngle){
    //             setMotorPower to currentMotorPower++;
    //         }
    //     }

    // }



}