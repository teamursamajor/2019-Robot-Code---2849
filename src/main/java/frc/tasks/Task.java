package frc.tasks;

public abstract class Task extends Thread {

    /**
     * Constructor for all tasks
     */
    public Task() {
    
    }

    public abstract void run();

}