package finanzasjp.modelo;

import java.util.Comparator;

public class ComparadorClienteRecibo  implements Comparator<Cliente_Recibo> {
	
	public ComparadorClienteRecibo() {
		
	}

	public int compare(Cliente_Recibo o1, Cliente_Recibo o2) {
		// TODO Auto-generated method stub
		return o1.getCliente().getNombre().compareTo(o2.getCliente().getNombre());
	}

}
