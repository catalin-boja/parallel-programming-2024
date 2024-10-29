package ro.ase.ie.paralel;

public class Piata {
	private int stoc;
	volatile boolean esteDeschisa = true;
	
	public static final int MAXIM = 10000;
	
	public Piata(int stoc) {
		this.stoc = stoc;
	}
	
	public synchronized void inchidePiata() {
		this.notifyAll();
		this.esteDeschisa = false;
	}
	
	public boolean getEsteDeschisa() {
		return this.esteDeschisa;
	}
	
	public synchronized void adaugaStoc(int valoare) {
		
		if(!this.esteDeschisa) {
			this.notifyAll();
			return;
		}
		
		if(this.stoc + valoare > MAXIM) {
			
			this.notifyAll();
			
			try {
				System.out.println("******** Blocare producator");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			//pierdem valoare care se putea adauga inainte de suspendare
			System.out.println("Se adauga " + valoare);
			this.stoc += valoare;
			System.out.println("Valoare stoc: " + this.stoc);
		}
		

	}
	
	public synchronized void consumaStoc(int valoare) {
		
		if(!this.esteDeschisa) {
			this.notifyAll();
			return;
		}
		
		if(this.stoc < valoare) {
			
			this.notifyAll();
			
			try {
				System.out.println("******** Blocare consumator");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Se consuma " + valoare);
			this.stoc -= valoare;
			System.out.println("Valoare stoc: " + this.stoc);
		}
	}
	
	
	
}
