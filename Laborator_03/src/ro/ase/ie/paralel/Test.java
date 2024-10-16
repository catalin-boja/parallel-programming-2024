package ro.ase.ie.paralel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
	
	
	public static boolean estePar(int valoare) {
		//calcule suplimentare pentru efort computational
		float temp = valoare / 3;
		return valoare % ((Math.sqrt(temp) / 3 * 0) + 2) == 0;
	}
	
	//determinam cate numere sunt pare intr-o colectie
	public static long determinaSecventialNumarPare(int[] valori, int N) {
		long contor = 0;
		for(int i = 0; i < N; i++) {
			if(estePar(valori[i])) {
				contor+=1;
			}
		}
		return contor;
	}
	

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Initializare date...");
		Random random = new Random(1);	//pentru a avea aceeasi secventa de fiecare data
		
		int N = (int)3e8;
		int[] valori = new int[N];
		for(int i = 0; i < N; i++) {
			valori[i] = random.nextInt(N);
		}
		
		System.out.println("Start test...");
		double tStart = System.currentTimeMillis();
		
		long rezultat = determinaSecventialNumarPare(valori, N);
		
		double tFinal = System.currentTimeMillis();
		
		System.out.println(
				String.format("Numar elemente pare: %d, durata: %f s",
						rezultat, (tFinal-tStart)/1000));

		//test secvential cu race condition
		List<Thread> fireExecutie = new ArrayList<>();
		int nrFire = 4;
		
		Contor contor = new Contor();
		
		int interval = N / nrFire;
		
		for(int i = 0; i < nrFire; i++) {
			int indexStart = i * interval;
			int indexFinal = (i == nrFire - 1) ? N : (i + 1) * interval;
			fireExecutie.add(new ThreadCalculNrPare(
					contor, valori, indexStart, indexFinal));
		}
		
		tStart = System.currentTimeMillis();
		for(int i = 0; i < nrFire; i++) {
			fireExecutie.get(i).start();
		}
		for(int i = 0; i < nrFire; i++) {
			fireExecutie.get(i).join();
		}
		tFinal = System.currentTimeMillis();
		System.out.println(
				String.format("Numar elemente pare: %d, durata: %f s",
						contor.get(), (tFinal-tStart)/1000));

		
		
		
	}

}
