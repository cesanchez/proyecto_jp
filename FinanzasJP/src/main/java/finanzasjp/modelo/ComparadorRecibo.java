package finanzasjp.modelo;

import java.util.Comparator;

public class ComparadorRecibo implements Comparator<Recibo>{
	
	public ComparadorRecibo() {
		
	}

	public int compare(Recibo r1, Recibo r2) {
		
		int ret = 0;
		
		if(r1.getId_recibo() > r2.getId_recibo()) {
			ret = 1;
		}else if(r1.getId_recibo() < r2.getId_recibo()){
			ret = -1;
		}
		
		return ret;
	}

}
