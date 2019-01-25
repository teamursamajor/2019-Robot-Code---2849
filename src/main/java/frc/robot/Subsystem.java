package frc.robot;

public abstract class Subsystem<E> implements Runnable {

    public static boolean running = false;
    private static Object lock = new Object();
    
    private Thread t;
    
    public Subsystem(String threadName) {
        synchronized (lock) {
			if (running)
				return;
			running = true;
        }
        //TODO Maybe we should declare/start the thread in the individual subsystems?
        //Unless the name has no purpose here, idk. Right now it's general
        t = new Thread(this, threadName);
        t.start();
    }

    public void run() {
        while (running) {
            try {
                runSubsystem();
            } catch (InterruptedException e) {

            }
        }
    }

    public static boolean getRunning() {
		return running;
    }
    
    /**
	 * Kill method for thread
	 */
	public void kill() {
		running = false;
	}

    private E subsystemMode;

    public void setMode(E mode) {
        t.interrupt();
        subsystemMode = mode;
    }

    public E getMode() {
        return subsystemMode;
    }

    public abstract void runSubsystem() throws InterruptedException;

}