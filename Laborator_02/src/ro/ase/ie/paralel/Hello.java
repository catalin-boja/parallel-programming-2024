package ro.ase.ie.paralel;

import java.util.ArrayList;
import java.util.List;

public class Hello {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Hello 1");
		System.out.println("Hello 2");
		System.out.println("Hello 3");
		System.out.println("Hello 4");
		System.out.println("Hello 5");
		
		//creare fire executie
		HelloThread t1 = new HelloThread(1);
		List<HelloThread> fireExecutie = new ArrayList<>();
		fireExecutie.add(t1);
		for(int i = 1 ; i < 5; i++) {
			fireExecutie.add(new HelloThread(i+1));
		}
		
		//lansare fire executie
		for(int i = 0;i < 5; i++) {
			fireExecutie.get(i).start();
		}
		
		//fara join vom genera fire de executie orfane
		//sincronizare cu firele de executie lansate
		for(int i = 0; i < 5; i++) {
			fireExecutie.get(i).join();
		}
		
		Thread tRunnable = new Thread(new HelloRunnable());
		tRunnable.start();
		tRunnable.join();
		
		System.out.println("Sfarsit test");
		
	}

}
