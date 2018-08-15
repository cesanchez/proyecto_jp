package finanzasjp.modelo;

import java.util.Comparator;

public class ComparadorCuota implements Comparator<Cuota>{
	
	public ComparadorCuota() {
		
	}

	public int compare(Cuota o1, Cuota o2) {
		// TODO Auto-generated method stub
		int ret = 0;
		
		if(o1.getId_cuota() > o2.getId_cuota()) {
			ret = 1;
		}else if(o1.getId_cuota() < o2.getId_cuota()){
			ret = -1;
		}
		
		return ret;
	}

}
