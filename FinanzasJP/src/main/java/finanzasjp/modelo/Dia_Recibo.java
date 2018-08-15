package finanzasjp.modelo;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dia_recibo")
public class Dia_Recibo implements Serializable{
	
	private int id_dia;
	private Recibo id_recibo;
	
	public Dia_Recibo() {
		
	}
	
	public Dia_Recibo(int id_dia, Recibo id_recibo) {
		super();
		this.id_dia = id_dia;
		this.id_recibo = id_recibo;
	}

	@Id
    @Column(name = "id_dia")
	public int getId_dia() {
		return id_dia;
	}

	public void setId_dia(int id_dia) {
		this.id_dia = id_dia;
	}
	
	@Id
	@ManyToOne
    @JoinColumn(name = "id_recibo")
	public Recibo getId_recibo() {
		return id_recibo;
	}

	public void setId_recibo(Recibo id_recibo) {
		this.id_recibo = id_recibo;
	}

}

