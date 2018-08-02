package finanzasjp.modelo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "cliente_vip")
public class Cliente_VIP {
	
	private String id_cl;
	private String nombre;
	private String apellido;
	private String telefono;
	private double monto_inicial;
	
	private Set<Cliente> clientes;	
	
	public Cliente_VIP() {
		
	}
	
	public Cliente_VIP(String id_cl, String nombre, String apellido, String telefono, double capital, Set<Cliente> clientes) {
		super();
		this.id_cl = id_cl;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.clientes = clientes;
		this.monto_inicial = capital;
	}

	@Id
	@Column(name = "id")
	public String getId() {
		return id_cl;
	}

	public void setId(String id) {
		this.id_cl = id;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@OneToMany(mappedBy = "cliente_vip", targetEntity=Cliente.class, cascade = CascadeType.ALL)
	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public void addCliente(Cliente cl) {
		this.clientes.add(cl);
	}
	
	public boolean containsCliente(Cliente myCl) {
		boolean ret = false;
		for(Cliente cl : clientes) {
			
			if(cl.getId().equals(myCl.getId())) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public double getMonto_inicial() {
		return monto_inicial;
	}

	public void setMonto_inicial(double monto_inicial) {
		this.monto_inicial = monto_inicial;
	}
}
