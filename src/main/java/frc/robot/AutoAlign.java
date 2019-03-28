package frc.robot;

public class AutoAlign implements UrsaRobot, Runnable {

    public static void autoAlign() {
        Thread t = new Thread("Auto Align Thread");
        t.start();
    }

    public void run() {
        // double tolerance = 3;

        //TODO calibrate all of this
        double maxTurnPower = 0.20;
        double maxTapeAreaPercent = 60;
        double passiveSpeed = .25;
        double turnKp = 1.0 / 40.0;

        // potentionally use ts to make the robot face the tapes
        Drive.setPower(passiveSpeed); // passive drive forward

        while (true) {
            // use tx (horizontal offset) to align the robot
            double tx = limelightTable.getEntry("tx").getDouble(0);
            double ta = limelightTable.getEntry("ta").getDouble(0);

            // turn PID using tx
            double outputPower = turnKp * tx;

            if (outputPower > maxTurnPower)
                outputPower = maxTurnPower;

            // set drive powers to passive speed + PID speed
            if (tx < 0) // need to turn right
                Drive.setLeftPower(passiveSpeed + outputPower);
            else if (tx > 0) // need to turn left
                Drive.setRightPower(passiveSpeed + outputPower);

            if (tx == 0)
                outputPower = 0;

            if (ta > maxTapeAreaPercent) {
                Drive.setPower(0);
                break;
            }
        }
        xbox.rumbleFor(500);
    }
}