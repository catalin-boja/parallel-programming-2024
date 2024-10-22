package ro.ase.ie.paralel;

import java.util.Random;

//clasa care gestioneaza jocul
public class Bingo extends Thread{
	volatile int numarExtras;
	int contor;
	
	public Bingo(int contor) {
		super();
		this.contor = contor;
	}

	public int getNumarExtras() {
		return numarExtras;
	}

	@Override
	public void run() {
		//simulam cu un numar maxim de numere exrase
		while(this.contor > 0) {
			this.contor--;
			Random random = new Random();
			this.numarExtras = random.nextInt(50);
			System.out.println("Numar extras: " + this.numarExtras);
		}
	}
	
	
}
