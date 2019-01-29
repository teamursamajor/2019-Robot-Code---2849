package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants{
    //String squirrelsAreAwesome = "This is a fact";
    int i = 50;
    //SmartDashboard.putData("Squirrel Test", new Squirrel());

    //SmartDashboard.putData()
    public void printTest(){
        System.out.println("SMART DASHBOARD: ");
    }
    public void dashTest(){
        String squirrel = "HIIIII";
        SmartDashboard.putString("Squirrel Test", squirrel);
        //SmartDashboard.putData("Function", );
    }
}