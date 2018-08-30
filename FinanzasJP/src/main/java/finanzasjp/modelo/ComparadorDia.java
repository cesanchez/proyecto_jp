package finanzasjp.modelo;

import java.util.Comparator;

public class ComparadorDia implements Comparator<Dia_Recibo>{
	
	public ComparadorDia() {
		
	}
	public int compare(Dia_Recibo o1, Dia_Recibo o2) {
		// TODO Auto-generated method stub
	int ret = 0;
		
		if(o1.getId_dia() > o2.getId_dia()) {
			ret = 1;
		}else if(o1.getId_dia() < o2.getId_dia()){
			ret = -1;
		}
		
		return ret;
	}

}
