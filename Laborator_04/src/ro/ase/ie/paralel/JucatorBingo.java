package ro.ase.ie.paralel;

public class JucatorBingo extends Thread{
	
	String nume;
	int numarNorocos;
	Bingo bingo;
	
	public JucatorBingo(String nume, int numarNorocos, Bingo bingo) {
		this.nume = nume;
		this.numarNorocos = numarNorocos;
		this.bingo = bingo;
	}

	@Override
	public void run() {
		System.out.println(
				this.nume + " are numar norocos " + this.numarNorocos);
		while(true) {
			if(this.bingo.getNumarExtras() == this.numarNorocos) {
				System.out.println(this.nume + " a castigat **********");
				return;
			}
		}
	}
	
	
	

}
