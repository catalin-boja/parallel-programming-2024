package ro.ase.ie.paralel;

public class Contor {
	int valoare = 0;
	
	public synchronized void incrementeaza() {
		this.valoare++;
	}
	
	public int get() {
		return this.valoare;
	}
}
