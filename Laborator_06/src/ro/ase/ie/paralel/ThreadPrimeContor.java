package ro.ase.ie.paralel;

public class ThreadPrimeContor extends Thread{
	Contor contor;
	long min;
	long max;
	
	public ThreadPrimeContor(Contor contor, long min, long max) {
		super();
		this.contor = contor;
		this.min = min;
		this.max = max;
	}

	@Override
	public void run() {
		for(long x = min; x <= max; x++) {
			if(TestPrime.estePrim(x)) {
				this.contor.increment();
			}
		}
	}
	
	
	

}
