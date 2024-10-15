package ro.ase.ie.paralel;

import java.util.Random;

public class ThreadUtilizator extends Thread{

	String nume;
	ContBancar cont;
	
	public ThreadUtilizator(String nume, ContBancar cont) {
		super();
		this.nume = nume;
		this.cont = cont;
	}

	@Override
	public void run() {
		while(true) {
			if(this.cont.getBalanta() > 0) {
				Random random = new Random();
				int suma = random.nextInt(20);
				System.out.println(this.nume + " incearca o plata de " + suma);
				
				this.cont.plata(suma);
			}
			else {
				return;
			}
		}
	}
	
}
