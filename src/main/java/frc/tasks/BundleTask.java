package frc.tasks;

public class BundleTask extends GroupTask {

    public BundleTask() {
        super();
    }
	
	public void run() {
		// Logger.log("Running bundle task", LogLevel.INFO);

		for(Task t:tasks) {
			t.start();
		}
		for(Task t:tasks) {
			try {
				//Acts as a Thread.sleep
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				// Logger.log("BundleTask run method join thread printStackTrace", LogLevel.ERROR);
			}
		}
	}
	
	public String toString() {
		return "--BundleTask:\n" + super.toString();
	}
}