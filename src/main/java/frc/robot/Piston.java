
package frc.robot;

import frc.tasks.*;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Piston implements UrsaRobot, Runnable {
    
    private Solenoid exampleSolenoid = new Solenoid(3);
    Compressor c = new Compressor(0);

    private Object lock = new Object();
    private boolean running = false;

    public Piston() {
        startTest();
    }

    public void startTest() {
        synchronized (lock) {
            if (running)
                return;
            running = true;
        }
        new Thread(this, "Piston").start();
    }

    public void run(){
        c.setClosedLoopControl(true);
        while (running) {
            exampleSolenoid.set(true);
            
            try{
               Thread.sleep(5000);
            } catch(InterruptedException exception){
                exception.printStackTrace();
            }
            
            exampleSolenoid.set(false);

            try{
                Thread.sleep(5000);
             } catch(InterruptedException exception){
                 exception.printStackTrace();

             }
        }    
    }
    
}