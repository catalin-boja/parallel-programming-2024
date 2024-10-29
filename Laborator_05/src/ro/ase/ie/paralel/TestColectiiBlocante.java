package ro.ase.ie.paralel;

import java.util.concurrent.LinkedBlockingDeque;

public class TestColectiiBlocante {

	public static void main(String[] args) throws InterruptedException {
		
		LinkedBlockingDeque<Mesaj> mesaje = 
				new LinkedBlockingDeque<>(50);
		
		GeneratorMesaje generator = 
				new GeneratorMesaje(mesaje, "G1");
		ProcesatorMesaje procesator1 = 
				new ProcesatorMesaje(mesaje, "P1");
		ProcesatorMesaje procesator2 = 
				new ProcesatorMesaje(mesaje, "P2");
		
		generator.start();
		procesator1.start();
		procesator2.start();
		
		Thread.sleep(10000);
		
		generator.stopGenerator();
		generator.join();
		procesator1.join();
		procesator2.join();
		
		System.out.println("Sfarsit exemplu");
		
	}

}
