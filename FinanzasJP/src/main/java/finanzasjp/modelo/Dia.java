package finanzasjp.modelo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "dia")
public class Dia {
	
	private int dia;
	private Set<Recibo> recibos;
	
	public Dia() {
		
	}

	public Dia(int dia) {
		super();
		this.dia = dia;
	}

	@Id
    @Column(name = "dia")
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "dias")
	public Set<Recibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(Set<Recibo> recibos) {
		this.recibos = recibos;
	}
	
	
	

}
