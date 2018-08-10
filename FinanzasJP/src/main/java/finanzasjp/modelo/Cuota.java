package finanzasjp.modelo;

import java.io.Serializable;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "cuota")
public class Cuota implements Serializable{
	
	private int id_cuota; 
	private Date fecha_cobro; 
	private double valor; 
	private double valor_pagado; 
	private double valor_interes;
	private double valor_capital;
	
	@Column(columnDefinition = "TINYINT(1)")
	@Type(type = "org.hibernate.type.NumericBooleanType")
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
	
	public Cuota(int id_cuota, double valor, double valor_pagado, boolean mora, double valorInteres, double valorCapital, Recibo id_recibo) {
		super();
		this.id_cuota = id_cuota;
		this.valor = valor;
		this.mora = mora;
		this.id_recibo = id_recibo;
		this.valor_pagado = valor_pagado;
		this.valor_interes = valorInteres;
		this.valor_capital = valorCapital;
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

	@Id
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

	public double getValor_interes() {
		return valor_interes;
	}

	public void setValor_interes(double valor_interes) {
		this.valor_interes = valor_interes;
	}

	public double getValor_capital() {
		return valor_capital;
	}

	public void setValor_capital(double valor_capital) {
		this.valor_capital = valor_capital;
	}

	

}
