package frc.tasks;

import frc.robot.AutoAlign;

public class AutoAlignTask extends Task{

    public AutoAlignTask(){
        Thread t = new Thread(this, "AutoAlignTask");
        t.start();
    }
    
    public void run(){
        AutoAlign.autoAlign();
    }
}