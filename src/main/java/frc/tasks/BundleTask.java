package frc.tasks;

public class BundleTask extends GroupTask {

	public BundleTask() {
		super();
	}

	public void run() {

		for (Task t : tasks) {
			t.start();
		}
		for (Task t : tasks) {
			try {
				t.join(); // Acts as a Thread.sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		return "--BundleTask:\n" + super.toString();
	}
}