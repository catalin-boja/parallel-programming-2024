package ro.ase.ie.paralel;

public class TestProducatorConsumator {

	public static void main(String[] args) throws InterruptedException {
		
		Piata piata = new Piata(0);
		Producator producator = new Producator(piata, "P1");
		Consumator consumator = new Consumator(piata, "C1");
		
		Producator producator2 = new Producator(piata, "P2");
		Consumator consumator2 = new Consumator(piata, "C2");
		
		producator.start();
		producator2.start();
		consumator.start();
		consumator2.start();
		
		Thread.sleep(10000);
		piata.inchidePiata();
		
		producator.join();
		consumator.join();
		producator2.join();
		consumator2.join();
		
		System.out.println("Sfarsit exemplu");
		
	}

}
