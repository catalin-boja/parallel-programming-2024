package ro.ase.ie.paralel;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class GeneratorMesaje extends Thread{
	LinkedBlockingDeque<Mesaj> bufferMesaje;
	String id;
	boolean esteOprit = false;
	
	public GeneratorMesaje(LinkedBlockingDeque<Mesaj> bufferMesaje, String id) {
		super();
		this.bufferMesaje = bufferMesaje;
		this.id = id;
	}
	
	public void stopGenerator() {
		this.esteOprit = true;
	}

	@Override
	public void run() {
		while(!this.esteOprit) {
			Random random = new Random();
			int numarMesaj = random.nextInt(500);
			Mesaj mesaj = new Mesaj("Mesaj " + numarMesaj);
			System.out.println(">>>> Generare mesaj " + numarMesaj);
			
			try {
				this.bufferMesaje.put(mesaj);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//inserare mesaje cu "poisen pill" pentru a notifica consumatorii
		//daca avem 10 thread-uri consumator in sistem
		for(int i = 0; i < 10; i++) {
			try {
				System.out.println("******Generare mesaj end");
				this.bufferMesaje.put(new Mesaj("end"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

	
	
	
	
	
}
