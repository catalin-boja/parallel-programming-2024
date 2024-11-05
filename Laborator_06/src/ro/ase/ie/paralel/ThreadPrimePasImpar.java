package ro.ase.ie.paralel;

public class ThreadPrimePasImpar extends Thread{
	int contor = 0;
	long min;
	long max;
	int pas;
	
	public ThreadPrimePasImpar(long min, long max, int pas) {
		this.max = max;
		this.min = min;
		this.pas = pas;
	}

	public int getContor() {
		return contor;
	}

	@Override
	public void run() {
		double tStart = System.currentTimeMillis();
		for(long x = min; x <= max; x += this.pas * 2) {
			if(TestPrime.estePrim(x)) {
				this.contor+=1;
			}
		}
		double tFinal = System.currentTimeMillis();
		System.out.println("T: " + (tFinal-tStart)/1000);
	}
	
}
