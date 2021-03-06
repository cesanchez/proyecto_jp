package finanzasjp.modelo;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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

import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

@Entity
@Table(name = "recibo")
public class Recibo {

	private int id_recibo;

	private double saldo;
	private double monto_prestamo;
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean activo;
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean mora;
	private double pago_total;
	private double interes;
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean diario;
	private Date fecha_prestamo;
	private Date fecha_fin;

	private Cliente id_cliente;
	private Set<Cuota> cuotas;
	private Set<Dia_Recibo> dias;

	public Recibo() {

	}

	public Recibo(int id_recibo, double saldo, double monto_prestamo, boolean activo, boolean mora, double pago_total,
			double interes, boolean diario, Date fecha_prestamo, Date fecha_fin, Cliente id_cliente, Set<Cuota> cuotas,
			Set<Dia_Recibo> dias) {
		super();
		this.id_recibo = id_recibo;
		this.saldo = saldo;
		this.monto_prestamo = monto_prestamo;
		this.activo = activo;
		this.mora = mora;
		this.pago_total = pago_total;
		this.interes = interes;
		this.diario = diario;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_fin = fecha_fin;
		this.id_cliente = id_cliente;
		this.cuotas = cuotas;
		this.dias = dias;
	}
	
	public Recibo(int id_recibo, double saldo, double monto_prestamo, boolean activo, boolean mora, double pago_total,
			double interes, boolean diario, Date fecha_prestamo, Date fecha_fin, Cliente id_cliente) {
		super();
		this.id_recibo = id_recibo;
		this.saldo = saldo;
		this.monto_prestamo = monto_prestamo;
		this.activo = activo;
		this.mora = mora;
		this.pago_total = pago_total;
		this.interes = interes;
		this.diario = diario;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_fin = fecha_fin;
		this.id_cliente = id_cliente;
		this.cuotas = new HashSet<Cuota>();
	}

	public boolean contieneDia(int dia) {

		boolean ret = false;
		for (Dia_Recibo d : this.getDias()) {

			if (d.getId_dia() == dia) {
				ret = true;
				break;
			}
		}

		return ret;

	}

	@Id
	@Column(name = "id_recibo")
	public int getId_recibo() {
		return id_recibo;
	}

	public void setId_recibo(int id_recibo) {
		this.id_recibo = id_recibo;
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

	@ManyToOne
	@JoinColumn(name = "id_cliente")
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

	@OneToMany(mappedBy = "id_recibo", targetEntity = Cuota.class, cascade = CascadeType.ALL)
	public Set<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(Set<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	/*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dia_recibo", joinColumns = {
			@JoinColumn(name = "id_recibo", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_dia", nullable = false) })*/
	@OneToMany(mappedBy = "id_recibo", targetEntity = Dia_Recibo.class, cascade = CascadeType.ALL)
	public Set<Dia_Recibo> getDias() {
		return dias;
	}

	public void setDias(Set<Dia_Recibo> dias) {
		this.dias = dias;
	}
	
	public void addDia(Dia_Recibo dia) {
		this.dias.add(dia);
	}

	public Date getFecha_prestamo() {
		return fecha_prestamo;
	}

	public void setFecha_prestamo(Date fecha_prestamo) {
		this.fecha_prestamo = fecha_prestamo;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	
	public void addCuota(Cuota cu) {
		this.cuotas.add(cu);
	}

	public Cuota darCuotaId(int id) {

		Cuota cRet = null;

		for (Cuota c : cuotas) {
			if (c.getId_cuota() == id) {
				cRet = c;
				break;
			}
		}
		return cRet;
	}

}
