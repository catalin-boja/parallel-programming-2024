package ro.ase.ie.paralel;

public class TestPrime {
	
	public static boolean estePrim(long valoare) {
		for(long i = 2; i <= valoare/2; i++) {
			if(valoare % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static int solutieSecventiala(long min, long max) {
		int contor = 0;
		for(long x = min; x <= max; x++) {
			if(estePrim(x))
				contor += 1;
		}
		return contor;
	}
	
	
	public static void main(String args[]) {
		
		long N = (long)3e5;
		System.out.println("Start solutie secventiala");
		double tStart = System.currentTimeMillis();
		int nrPrime = solutieSecventiala(1, N);
		double tFinal = System.currentTimeMillis();
		System.out.printf("Nr prime: %d in %f secunde",
				nrPrime, (tFinal - tStart)/1000);
		
	}
}
