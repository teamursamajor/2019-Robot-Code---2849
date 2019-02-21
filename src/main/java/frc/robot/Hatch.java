package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import frc.tasks.HatchTask;
import frc.tasks.HatchTask.HatchMode;

public class Hatch extends Subsystem<HatchTask.HatchMode> implements UrsaRobot {

    private Spark hatchMotor;
    private Servo hatchServo;
    private boolean servoUp = true;
    private long maxRunTime = 1050; // how long the wheel spins

    public Hatch() {
        hatchMotor = new Spark(HATCH);

        hatchServo = new Servo(HATCH_SERVO);
        System.out.println(hatchServo.getAngle());
        System.out.println(hatchServo.get());
        subsystemMode = HatchMode.WAIT;
        
    //     hatchServo.setAngle(1);
    //     System.out.println("1");
    //     try{
    //         Thread.sleep(1000);
    //     } catch(Exception e){
    //         e.printStackTrace();
    //     }
    //     hatchServo.setAngle(2);
    //     System.out.println("2");
    //     try{
    //         Thread.sleep(100);
    //     } catch(Exception e){
    //         e.printStackTrace();
    //     }
    //     hatchServo.setAngle(3);
    //     System.out.println("3");
    }

    public void runSubsystem() {
        hatchServo.setAngle(hatchServo.getAngle() + 1);
        if(xbox.getButton(XboxController.BUTTON_A)){
            hatchServo.setPosition(1);
            System.out.println(1);
        } else if(xbox.getButton(XboxController.BUTTON_B)){
            hatchServo.setPosition(2);
            System.out.println(2);
        } else if(xbox.getButton(XboxController.BUTTON_X)){
            hatchServo.setPosition(3);
            System.out.println(3);
        } else if(xbox.getButton(XboxController.BUTTON_Y)){
            hatchServo.setPosition(4);
            System.out.println(4);
        }
        // System.out.println(hatchServo.get());
        // System.out.println(hatchServo.getAngle());
        // System.out.println("Get " + hatchServo.get());
        // System.out.println("Angle " + hatchServo.getAngle());
        // System.out.println("Pos " + hatchServo.getPosition());
        // System.out.println(hatchServo.getRaw());
        
        // if (xbox.getSingleButtonPress(XboxController.BUTTON_A)) { // runs hatch and flips servo
        //     subsystemMode = HatchMode.RUN;
        // } else if (xbox.getSingleButtonPress(XboxController.BUTTON_B)) { // flips servo, does not run hatch
        //     subsystemMode = HatchMode.WAIT;
        // }

        // HatchTask.HatchOrder hatchOrder = subsystemMode.callLoop();

        // if(subsystemMode.equals(HatchMode.RUN)){
        //     long startTime = System.currentTimeMillis();
        //     if(servoUp) { // servo was originally up and we are dropping off
        //         System.out.println(hatchServo.getAngle());
        //         System.out.println(hatchServo.get());
        //         try {
        //             hatchMotor.set(hatchOrder.hatchPower); // goes out
        //             Thread.sleep(maxRunTime);

        //             hatchMotor.set(0.0);
        //             servoUp = false;
        //             hatchServo.setAngle(180); // servo down
        //             System.out.println(hatchServo.getAngle());
        //             hatchMotor.set(-hatchOrder.hatchPower); // comes in
        //             Thread.sleep(maxRunTime);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     } else { // servo was originally down and we are picking up
        //         hatchMotor.set(hatchOrder.hatchPower);
        //         // TODO test number
        //         while((hatchServo.getAngle() <= 180 && hatchServo.getAngle() > 90) && (System.currentTimeMillis() - startTime) < maxRunTime){
        //             try {
        //                 Thread.sleep(10);
        //             } catch(InterruptedException e){
        //                 e.printStackTrace();
        //             }
        //         }
        //         long currentRunTime = System.currentTimeMillis() - startTime;
        //         // TODO test number
        //         hatchServo.setAngle(90);
        //         servoUp = true;
        //         hatchMotor.set(-hatchOrder.hatchPower);
        //         try {
        //             Thread.sleep(currentRunTime);
        //         } catch(InterruptedException e){
        //             e.printStackTrace();
        //         }
        //     }
        //     hatchMotor.set(0.0);
        // } else{
        //     if(servoUp){
        //         hatchServo.setAngle(90);
        //         System.out.println(hatchServo.getPosition() + " " + hatchServo.get());
        //     } else {
        //         // hatchServo.setAngle(90);
        //     }
        //     hatchMotor.set(0.0);
        // }
        // subsystemMode = HatchMode.WAIT;
    }

}