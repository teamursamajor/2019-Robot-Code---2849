package frc.tasks;

public class WaitTask extends Task {
	
	private long waitTime = 20;
	
	public WaitTask(long time) {
		super();
		waitTime = time;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "WaitTask: " + waitTime + "\n";
	}
}