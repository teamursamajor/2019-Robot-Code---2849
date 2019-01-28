package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class CargoLoops implements UrsaRobot {

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
            return new CargoOrder(0.0, 0.0);
        }

        private CargoOrder autoCalculator() {
            return new CargoOrder(0.0);
        }

        static class CargoState {
            static double power = 0.0, position = 0.0;
            static long stateTime = System.currentTimeMillis();

        public static void updateState(double power, double position){
            DriveLoops.CargoState.power = power;
            DriveLoops.CargoState.position = position;
            stateTime = System.currentTimeMillis();
        }
    }

}