package ro.ase.ie.paralel;

public class ThreadPrimeVariabilaInterna extends Thread{

	int contor = 0;
	long min;
	long max;
	
	public ThreadPrimeVariabilaInterna(long min, long max) {
		this.max = max;
		this.min = min;
	}

	public int getContor() {
		return contor;
	}

	@Override
	public void run() {
		double tStart = System.currentTimeMillis();
		for(long x = min; x <= max; x++) {
			if(TestPrime.estePrim(x)) {
				this.contor+=1;
			}
		}
		double tFinal = System.currentTimeMillis();
		System.out.println("T: " + (tFinal-tStart)/1000);
	}
	
	
}
