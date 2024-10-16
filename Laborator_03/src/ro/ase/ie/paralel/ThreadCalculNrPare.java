package ro.ase.ie.paralel;

public class ThreadCalculNrPare extends Thread{
	Contor contor;
	int[] valori;
	int indexStart;
	int indexFinal;
	
	public ThreadCalculNrPare(Contor contor, int[] valori, int indexStart, int indexFinal) {
		super();
		this.contor = contor;
		this.valori = valori;
		this.indexStart = indexStart;
		this.indexFinal = indexFinal;
	}

	@Override
	public void run() {
		for(int i = indexStart; i < indexFinal; i++) {
			if(Test.estePar(valori[i])) {
				this.contor.incrementeaza();
			}
		}
	}
	
	
	
	
}
