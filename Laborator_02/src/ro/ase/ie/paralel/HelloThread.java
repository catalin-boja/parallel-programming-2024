package ro.ase.ie.paralel;

public class HelloThread extends Thread{
	
	int id;
	
	public HelloThread(int id) {
		super();
		this.id = id;
	}

	@Override
	public void run() {
		
		System.out.println("Hello " + this.id);
		
	}

}
