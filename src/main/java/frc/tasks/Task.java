package frc.tasks;

public abstract class Task extends Thread {

    /**
     * Constructor for all tasks. Can be used if something needs to be ran for every
     * Task
     */
    public Task() {
        
    }

    public abstract void run();

}