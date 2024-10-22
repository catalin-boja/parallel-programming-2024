package ro.ase.ie.paralel;

public class ThreadPersoana extends Thread{
	String nume;
	ThreadPersoana prieten;
	
	public ThreadPersoana(String nume) {
		this.nume = nume;
	}

	public synchronized void saluta() {
		System.out.println(this.nume + " spune Salut !");
		this.prieten.raspundeSalut();
	}
	
	
	//cu synchronized avem deadlock pentru ca ambele metode
	//sunt blocate de mutex - sunt in acelasi 
	public synchronized void raspundeSalut() {
		System.out.println(this.nume + " raspunde Salut !");
	}
	
	public void setPrieten(ThreadPersoana prieten) {
		this.prieten = prieten;
	}

	@Override
	public void run() {
		this.saluta();
	}
	
	
}
