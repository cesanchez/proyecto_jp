package finanzasjp.modelo;

import java.util.Set;

public class Recibo {
	
	private int id_recibo;
	
	private double saldo; 
	private double monto_prestamo; 
	private boolean activo; 
	private boolean mora; 
	private double pago_total; 
	private double interes; 
	private boolean diario;
	
	private Cliente id_cliente; 
	private Set<Cuota> cuotas;	
		
	public Recibo() {
		
	}
	
	//autogenerado
	public int getId_recibo() {
		return id_recibo;
	}

	public void setId_recibo(int id_recibo) {
		this.id_recibo = id_recibo;
	}

	public Recibo(double saldo, double monto_prestamo, boolean activo, boolean mora, double pago_total, double interes,
			Cliente id_cliente, boolean diario) {
		super();
		this.saldo = saldo;
		this.monto_prestamo = monto_prestamo;
		this.activo = activo;
		this.mora = mora;
		this.pago_total = pago_total;
		this.interes = interes;
		this.id_cliente = id_cliente;
		this.diario = diario;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getMonto_prestamo() {
		return monto_prestamo;
	}

	public void setMonto_prestamo(double monto_prestamo) {
		this.monto_prestamo = monto_prestamo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isMora() {
		return mora;
	}

	public void setMora(boolean mora) {
		this.mora = mora;
	}

	public double getPago_total() {
		return pago_total;
	}

	public void setPago_total(double pago_total) {
		this.pago_total = pago_total;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public Cliente getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Cliente id_cliente) {
		this.id_cliente = id_cliente;
	}

	public boolean isDiario() {
		return diario;
	}

	public void setDiario(boolean diario) {
		this.diario = diario;
	}

	public Set<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(Set<Cuota> cuotas) {
		this.cuotas = cuotas;
	}
	
	
	
	

}
