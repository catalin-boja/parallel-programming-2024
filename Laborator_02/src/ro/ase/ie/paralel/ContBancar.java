package ro.ase.ie.paralel;

public class ContBancar {
	int balanta;

	public ContBancar(int balanta) {
		super();
		this.balanta = balanta;
	}
	
	synchronized void plata(int suma) {
		System.out.println("Balanta " + this.balanta + " >> " + suma);
		if(this.balanta >= suma) {
			System.out.println("Balanta curenta inainte de plata: " + this.balanta);
			this.balanta -= suma;
			System.out.println("Balanta curenta dupa plata: " + this.balanta);
		}
	}
	
	public int getBalanta() {
		return this.balanta;
	}
}
