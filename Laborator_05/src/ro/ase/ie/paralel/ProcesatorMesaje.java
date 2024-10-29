package ro.ase.ie.paralel;

import java.util.concurrent.LinkedBlockingDeque;

public class ProcesatorMesaje extends Thread{
	LinkedBlockingDeque<Mesaj> bufferMesaje;
	String id;
	
	public ProcesatorMesaje(LinkedBlockingDeque<Mesaj> bufferMesaje, String id) {
		super();
		this.bufferMesaje = bufferMesaje;
		this.id = id;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Mesaj mesaj = this.bufferMesaje.takeFirst();
				
				this.sleep(200);
				
				if(mesaj.getText().equals("end")){
					System.out.println("Procesatorul se inchide");
					return;
				} else {
					System.out.println(this.id + " <<<< " +
							mesaj.getText());
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	
	
	
}
