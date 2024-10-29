package ro.ase.ie.paralel;

import java.util.Random;

public class Consumator extends Thread{
	Piata piata;
	String nume;
	
	public Consumator(Piata piata, String nume) {
		super();
		this.piata = piata;
		this.nume = nume;
	}

	@Override
	public void run() {
		while(this.piata.getEsteDeschisa()) {
			Random random = new Random();
			int valoare = random.nextInt(500);
			try {
				this.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(
					this.nume + " <<<<<<<<<<<< " + valoare);
			this.piata.consumaStoc(valoare);
		}
	}
	
	
	
	
}
