package finanzasjp.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

	private String id;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono_celular;
	private String telefono_fijo;
	private String barrio;
	private String trabajo;
	private String telefono_trabajo;

	private Cliente_VIP cliente_vip;
	private Set<Recibo> recibos;
	private Set<Codeudor> id_codeudor;

	public Cliente() {

	}

	public Cliente(String id, String nombre, String apellido, String direccion, String telefono_celular,
			String telefono_fijo, String barrio, String trabajo, String telefono_trabajo, Cliente_VIP cliente_vip,
			Set<Recibo> recibos, Set<Codeudor> id_codeudor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono_celular = telefono_celular;
		this.telefono_fijo = telefono_fijo;
		this.barrio = barrio;
		this.trabajo = trabajo;
		this.telefono_trabajo = telefono_trabajo;
		this.cliente_vip = cliente_vip;
		this.recibos = recibos;
		this.id_codeudor = id_codeudor;
	}

	public Cliente(String id, String nombre, String apellido, String direccion, String telefono_celular,
			String telefono_fijo, String barrio, String trabajo, String telefono_trabajo, Cliente_VIP cliente_vip) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono_celular = telefono_celular;
		this.telefono_fijo = telefono_fijo;
		this.barrio = barrio;
		this.trabajo = trabajo;
		this.telefono_trabajo = telefono_trabajo;
		this.cliente_vip = cliente_vip;

		this.recibos = new HashSet<Recibo>();
	}
	
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@ManyToOne
	@JoinColumn(name = "id_cliente_vip")
	public Cliente_VIP getCliente_vip() {
		return cliente_vip;
	}

	public void setCliente_vip(Cliente_VIP cliente_vip) {
		this.cliente_vip = cliente_vip;
	}
	
	@OneToMany(mappedBy = "id_cliente", targetEntity = Codeudor.class, cascade = CascadeType.ALL)
	public Set<Codeudor> getId_codeudor() {
		return id_codeudor;
	}

	public void setId_codeudor(Set<Codeudor> id_codeudor) {
		this.id_codeudor = id_codeudor;
	}

	@OneToMany(mappedBy = "id_cliente", targetEntity = Recibo.class, cascade = CascadeType.ALL)
	public Set<Recibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(Set<Recibo> recibos) {
		this.recibos = recibos;
	}

	public void addRecibo(Recibo rec) {
		this.recibos.add(rec);
	}

	public Recibo darRecibo(int id_recibo) {
		Recibo ret = null;

		if (recibos != null) {
			for (Recibo re : recibos) {
				if (re.getId_recibo() == id_recibo) {
					ret = re;
					break;
				}
			}
		}
		return ret;
	}

	public String reciboActivo() {

		String res = "";
		for (Recibo r : recibos) {
			if (r.isActivo()) {
				res += r.getId_recibo() + ", ";
			}
		}
		return res;
	}

	public String getTelefono_celular() {
		return telefono_celular;
	}

	public void setTelefono_celular(String telefono_celular) {
		this.telefono_celular = telefono_celular;
	}

	public String getTelefono_fijo() {
		return telefono_fijo;
	}

	public void setTelefono_fijo(String telefono_fijo) {
		this.telefono_fijo = telefono_fijo;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public String getTelefono_trabajo() {
		return telefono_trabajo;
	}

	public void setTelefono_trabajo(String telefono_trabajo) {
		this.telefono_trabajo = telefono_trabajo;
	}


}
