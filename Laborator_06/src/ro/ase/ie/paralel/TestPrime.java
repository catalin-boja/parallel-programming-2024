package ro.ase.ie.paralel;

import java.util.ArrayList;

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
	
	//impartim intervalul in parti egale si folosim o variabila comuna
	public static int solutieParalela1(long min, long max) throws InterruptedException {
		
		int nrProcesoare = Runtime.getRuntime().availableProcessors();
		System.out.println("Nr procesoare: " + nrProcesoare);
		
		Contor contor = new Contor();
		
		ArrayList<ThreadPrimeContor> fire = new ArrayList<>(nrProcesoare);
		long delta = (max - min + 1)/nrProcesoare;
		for(int i = 0; i < nrProcesoare; i++) {
			long st = min + i * delta;
			long fin = min - 1 + (i + 1) * delta;
			if(i == nrProcesoare - 1) {
				fin = max;
			}
			fire.add(new ThreadPrimeContor(contor, st, fin));
		}
		double tStart = System.currentTimeMillis();
		for(ThreadPrimeContor t : fire) {
			t.start();
		}
		for(ThreadPrimeContor t : fire) {
			t.join();
		}
		double tFinal = System.currentTimeMillis();
		
		System.out.printf("Nr prime: %d in %f secunde",
				contor.getContor(), (tFinal - tStart)/1000);
		
		return contor.getContor();
	}
	
	//impartim intervalul in parti egale si folosim o variabila interna
	public static int solutieParalela2(long min, long max) throws InterruptedException {
		
		int nrProcesoare = Runtime.getRuntime().availableProcessors();
		System.out.println("Nr procesoare: " + nrProcesoare);
		
		ArrayList<ThreadPrimeVariabilaInterna> fire = new ArrayList<>(nrProcesoare);
		
		long delta = (max - min + 1)/nrProcesoare;
		for(int i = 0; i < nrProcesoare; i++) {
			long st = min + i * delta;
			long fin = min - 1 + (i + 1) * delta;
			if(i == nrProcesoare - 1) {
				fin = max;
			}
			fire.add(new ThreadPrimeVariabilaInterna(st, fin));
		}
		double tStart = System.currentTimeMillis();
		for(ThreadPrimeVariabilaInterna t : fire) {
			t.start();
		}
		for(ThreadPrimeVariabilaInterna t : fire) {
			t.join();
		}
		double tFinal = System.currentTimeMillis();
		
		int rezultat = 0;
		for(ThreadPrimeVariabilaInterna t : fire) {
			rezultat += t.getContor();
		}
		
		System.out.printf("Nr prime: %d in %f secunde",
				rezultat, (tFinal - tStart)/1000);
		
		return rezultat;
	}
	
	public static void main(String args[]) throws InterruptedException {
		
		long N = (long)3e5;
		System.out.println("Start solutie secventiala");
		double tStart = System.currentTimeMillis();
		int nrPrime = solutieSecventiala(1, N);
		double tFinal = System.currentTimeMillis();
		System.out.printf("Nr prime: %d in %f secunde",
				nrPrime, (tFinal - tStart)/1000);
		
		System.out.println("Start solutie paralela cu contor partajat");
		solutieParalela1(1, N);
		
		System.out.println("Start solutie paralela cu variabile interne");
		solutieParalela2(1, N);
	}
}
