package ro.ase.ie.paralel;

public class TestBingo {

	public static void main(String[] args) throws InterruptedException {

		Bingo bingo = new Bingo(1000);
		JucatorBingo gigel = new JucatorBingo("Gigel", 13, bingo);
		JucatorBingo ana = new JucatorBingo("Ana", 32, bingo);
		JucatorBingo ionut = new JucatorBingo("Ionut", 7, bingo);
		
		gigel.start();
		ana.start();
		ionut.start();
		bingo.start();
		
		gigel.join();
		ana.join();
		ionut.join();
		bingo.join();
		
		System.out.println("Joc terminat");
	}

}
