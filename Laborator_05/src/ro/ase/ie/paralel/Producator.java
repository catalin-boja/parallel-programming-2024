package ro.ase.ie.paralel;

import java.util.Random;

public class Producator extends Thread{
	Piata piata;
	String nume;
	
	public Producator(Piata piata, String nume) {
		this.piata = piata;
		this.nume = nume;
	}

	@Override
	public void run() {
		while(this.piata.getEsteDeschisa()) {
			Random random = new Random();
			int valoare = random.nextInt(1000);
			try {
				this.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(
					this.nume + " >>>>>>>>>>> " + valoare);
			this.piata.adaugaStoc(valoare);
		}
	}
	
}
