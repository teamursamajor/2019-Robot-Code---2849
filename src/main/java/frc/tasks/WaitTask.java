package frc.tasks;

public class WaitTask extends Task {
	
	private long waitTime = 20;
	
	public WaitTask(long time) {
		super();
		waitTime = time;
	}

	@Override
	public void run() {
		// Logger.log("Running wait task", LogLevel.INFO);

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			// Logger.log("WaitTask run method Thread.sleep call, printStackTrace", LogLevel.ERROR);
		}
	}
	
	public String toString() {
		return "WaitTask: " + waitTime + "\n";
	}
}