package frc.robot;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.minimap.MapPanel;
import frc.minimap.TestBot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException

/**
 * This class displays and reads information off of the SmartDashboard.
 */
public class DashboardInfo implements Runnable, UrsaRobot {
    public static boolean running = false;

    public static double hatchPower;
    public static double cargoPowerUp, cargoPowerDown;
    public static double cargoIntakePower, cargoOuttakePower;
    public static double climbPower;
    public static double lEncoder, rEncoder;
    private static MapPanel map;
    private static TestBot bot;

    public DashboardInfo() throws IOException{
        hatchPower = 0.30;
        SmartDashboard.putNumber("Hatch Power", hatchPower);
        cargoPowerUp = -.75;
        SmartDashboard.putNumber("Cargo Power Up", cargoPowerUp);
        cargoPowerDown = 0.15;
        SmartDashboard.putNumber("Cargo Power Down", cargoPowerDown);
        cargoIntakePower = 0.5;
        SmartDashboard.putNumber("Cargo Intake Power", cargoIntakePower);
        cargoOuttakePower = 1.0;
        SmartDashboard.putNumber("Cargo Outtake Power", cargoOuttakePower);
        climbPower = 0.90;
        SmartDashboard.putNumber("Climb Power", climbPower);

        bot = new TestBot();
        //TODO - Change this when not in GitHub
        BufferedImage fieldImage = ImageIO.read(
            new File("C:/Users/teamursamajor/git/2019-Robot-Code---2849/src/main/java/frc/minimap/2019 Field.jpg"));
        map = new MapPanel(fieldImage, bot);
        SmartDashboard.frame.add(map);
        
        startDashboardInfo();
    }

    public void startDashboardInfo() {
        running = true;
        new Thread(this, "DashboardInfo").start();
    }

    public void run() {
        while (running) {
            hatchPower = SmartDashboard.getNumber("Hatch Power", 0.3);
            cargoPowerUp = SmartDashboard.getNumber("Cargo Power Up", -0.75);
            cargoPowerDown = SmartDashboard.getNumber("Cargo Power Down", 0.15);
            cargoIntakePower = SmartDashboard.getNumber("Cargo Intake Power", 0.5);
            cargoIntakePower = SmartDashboard.getNumber("Cargo Outtake Power", 1.0);
            climbPower = SmartDashboard.getNumber("Climb Power", 0.9);

            lEncoder = leftEncoder.getDistance();
            rEncoder = rightEncoder.getDistance();
            double angle = ahrs.getAngle();
	    	angle = fixHeading(angle);            
            
            double[] encoderVals = {lEncoder, rEncoder};
            
            //TODO - Get rid of TestBot when not in GitHub
            //TODO - Add compass when not in GitHub
            //TODO - Set start points (after getting field measurements)
            bot.update(encoderVals, angle);
            map.update();
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // printTest();
        }
    }

    public void printTest() {
        System.out.println("SMART DASHBOARD: ");
        System.out.println("Hatch Power" + hatchPower);
        System.out.println("Cargo Power Up" + cargoPowerUp);
        System.out.println("Cargo Power Down" + cargoPowerUp);
        System.out.println("Cargo Intake Power" + cargoIntakePower);
        System.out.println("Cargo Outtake Power" + cargoOuttakePower);
        System.out.println("Climb Power" + climbPower);
    }
    
    

/**
 * The image widget simply displays a static image loaded from a file. If you
 * want to display the final product from image processing and you’re
 * extending a WPICameraWidgetExtension, either draw on top of the WPIImage
 * provided as a parameter or return a new WPIImage (if you return, make sure
 * you store a handle to it somewhere else, or SmartDashboard will crash without
 * any indication – this caused me a lot of grief).
 * https://github.com/team2485/sdwidgets/tree/master/src/team2485/smartdashboard/extension
 */
}
