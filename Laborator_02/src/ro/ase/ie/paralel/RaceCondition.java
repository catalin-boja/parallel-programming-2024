package ro.ase.ie.paralel;

public class RaceCondition {

	public static void main(String[] args) throws InterruptedException {

		ContBancar contBancar = new ContBancar(2000);
		
		ThreadUtilizator tGigel = new ThreadUtilizator("Gigel", contBancar);
		ThreadUtilizator tIonel = new ThreadUtilizator("Ionel", contBancar);
		
		tGigel.start();
		tIonel.start();
		
		tGigel.join();
		tIonel.join();
		
		System.out.println("Sfarsit simulare");
		
	}

}
