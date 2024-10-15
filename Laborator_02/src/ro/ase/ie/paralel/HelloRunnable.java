package ro.ase.ie.paralel;

public class HelloRunnable implements Runnable{

	@Override
	public void run() {
		System.out.println("Hello Thread Runnable");
	}

}
