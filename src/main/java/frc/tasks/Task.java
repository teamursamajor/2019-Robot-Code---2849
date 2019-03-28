package frc.tasks;

public abstract class Task extends Thread {

    //TODO do we need this class for anything?
    /**
     * Constructor for all tasks
     */
    public Task() {
    }

    public abstract void run();

}