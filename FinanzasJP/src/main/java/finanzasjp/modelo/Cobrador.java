package finanzasjp.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cobrador")
public class Cobrador {
	
	private String id_cobrador;
	private String nombre;
	private String apellido;
	private String tel_celular;
	
	private Set<Cliente> clientes;	
	
	public Cobrador() {
		
	}
	
	public Cobrador(String id_cobrador, String nombre, String apellido, String tel_celular) {
		super();
		this.id_cobrador = id_cobrador;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tel_celular = tel_celular;
		
		clientes = new HashSet<Cliente>();
	}
	
	@Id
	@Column(name = "id_cobrador")
	public String getId_cobrador() {
		return id_cobrador;
	}
	public void setId_cobrador(String id_cobrador) {
		this.id_cobrador = id_cobrador;
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
	public String getTel_celular() {
		return tel_celular;
	}
	public void setTel_celular(String tel_celular) {
		this.tel_celular = tel_celular;
	}
	@OneToMany(mappedBy = "id_cobrador", targetEntity=Cliente.class, cascade = CascadeType.ALL)
	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	

}
