package finanzasjp.modelo;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cuota")
public class Cuota {
	
	private int id_cuota; 
	private Date fecha_cobro; 
	private double valor; 
	private double valor_pagado; 
	private boolean mora; 
	
	private Recibo id_recibo;
	
	public Cuota() {
		
	}

	public Cuota(int id_cuota, Date fecha_cobro, double valor, double valor_pagado, boolean mora, Recibo id_recibo) {
		super();
		this.id_cuota = id_cuota;
		this.fecha_cobro = fecha_cobro;
		this.valor = valor;
		this.mora = mora;
		this.id_recibo = id_recibo;
		this.valor_pagado = valor_pagado;
	}

	@Id
    @Column(name = "id_cuota")
	public int getId_cuota() {
		return id_cuota;
	}

	public void setId_cuota(int id_cuota) {
		this.id_cuota = id_cuota;
	}

	public Date getFecha_cobro() {
		return fecha_cobro;
	}

	public void setFecha_cobro(Date fecha_cobro) {
		this.fecha_cobro = fecha_cobro;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean isMora() {
		return mora;
	}

	public void setMora(boolean mora) {
		this.mora = mora;
	}

	@ManyToOne
    @JoinColumn(name = "id_recibo")
	public Recibo getId_recibo() {
		return id_recibo;
	}

	public void setId_recibo(Recibo id_recibo) {
		this.id_recibo = id_recibo;
	}

	public double getValor_pagado() {
		return valor_pagado;
	}

	public void setValor_pagado(double valor_pagado) {
		this.valor_pagado = valor_pagado;
	}
	
	

}
