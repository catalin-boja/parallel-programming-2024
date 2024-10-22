package ro.ase.ie.paralel;

public class TestDeadlock {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		//test deadlock intre mai multe fire de executie
		ThreadPersoana ionut = new ThreadPersoana("Ionut");
		ThreadPersoana gigel = new ThreadPersoana("Gigel");
		ionut.setPrieten(gigel);
		gigel.setPrieten(ionut);
		
		ionut.start();
		gigel.start();
		
		ionut.join();
		gigel.join();
		
		System.out.println("Sfarsit exemplu");
	}

}
