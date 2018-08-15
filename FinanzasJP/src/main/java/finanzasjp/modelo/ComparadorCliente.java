package finanzasjp.modelo;

import java.util.Comparator;

public class ComparadorCliente implements Comparator<Cliente> {
	
	public ComparadorCliente() {
		
	}

	public int compare(Cliente o1, Cliente o2) {
		// TODO Auto-generated method stub
		return o1.getNombre().compareTo(o2.getNombre());
	}

}
