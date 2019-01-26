package frc.robot;

public abstract class Subsystem<E> implements Runnable {

    /* This class is meant to act as a skeleton for subsystems and reduce redundant code.
     * It creates a subsystem thread and methods to get/set different modes (according to enums).
     * Subsystem classes then extend it and run its constructor so they get the thread and methods.
     * It uses the generic E so that subsystem classes can substitute this with their mode enums.
     */

    public static boolean running = false;
    private static Object lock = new Object();
    
    private Thread t;
    
    public Subsystem(String threadName) {
        //Used to prevent ("lock") a thread from starting again if constructor is run again
        synchronized (lock) {
			if (running)
				return;
			running = true;
        }
        t = new Thread(this, threadName);
        t.start();
    }

    //Thread Methods

    public void run() {
        while (running) {
            //When the thread is interrupted to set a mode (see below), it will just restart itself.
            try {
                runSubsystem();
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * Checks if subsystem thread is running.
     */
    public static boolean getRunning() {
		return running;
    }
    
    /**
	 * Kills subsystem thread.
	 */
	public void kill() {
		running = false;
	}

    //Mode Setter/Getter Methods

    protected E subsystemMode;

    /**
     * Sets the current mode for the subsystem
     */
    public void setMode(E mode) {
        subsystemMode = mode;
    }

    /**
     * @return The current mode for the subsystem.
     */
    public E getMode() {
        return subsystemMode;
    }

    /**
     * Abstract method for subsystems to do stuff in their individual threads.
     * @throws InterruptedException in case the thread is interrupted to change modes.
     */
    public abstract void runSubsystem() throws InterruptedException;

}