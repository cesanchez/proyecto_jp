package finanzasjp.modelo;

import java.sql.Date;

public class Cuota {
	
	private int id_cuota; 
	private Date fecha_cobro; 
	private double valor; 
	private boolean mora; 
	
	private Recibo id_recibo;
	
	public Cuota() {
		
	}

	public Cuota(int id_cuota, Date fecha_cobro, double valor, boolean mora, Recibo id_recibo) {
		super();
		this.id_cuota = id_cuota;
		this.fecha_cobro = fecha_cobro;
		this.valor = valor;
		this.mora = mora;
		this.id_recibo = id_recibo;
	}

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

	public Recibo getId_recibo() {
		return id_recibo;
	}

	public void setId_recibo(Recibo id_recibo) {
		this.id_recibo = id_recibo;
	}
	
	

}
