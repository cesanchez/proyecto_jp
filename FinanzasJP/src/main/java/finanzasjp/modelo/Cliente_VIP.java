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
@Table(name = "CLIENTE_VIP")
public class Cliente_VIP {
	
	private long id;
	private String nombre;
	private String apellido;
	private String telefono;
	
	private Set<Cliente> clientes;
	
	public Cliente_VIP() {
		
	}

	@Id
	@Column(name = "ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	//@OneToMany(mappedBy = "CLIENTE_VIP", cascade = CascadeType.ALL)
	public Set<Cliente> getClientes() {
		return clientes;
	}


}
