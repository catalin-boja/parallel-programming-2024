package ro.ase.ie.paralel;

import java.util.concurrent.atomic.AtomicInteger;

public class Contor {
	//int contor = 0;
	AtomicInteger contor = new AtomicInteger(0);

//varianta initiala
//	public synchronized void increment() {
//		this.contor += 1;
//	}
	
	public void increment() {
		this.contor.incrementAndGet();
	}
	
	public int getContor() {
		return this.contor.get();
	}
	
}
