package finanzasjp.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "codeudor")
public class Codeudor {
	
	private String id_codeudor; 
	private String nombre; 
	private String apellido;
	private String direccion; 
	private String telefono_celular; 
	private String telefono_fijo;
	private String barrio;
	private String trabajo;
	private String telefono_trabajo;
	
	private Cliente id_cliente;
	
	public Codeudor(String id, String nombre, String apellido, String direccion, String telefono_celular,
			String telefono_fijo, String barrio, String trabajo, String telefono_trabajo) {
		super();
		this.id_codeudor = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono_celular = telefono_celular;
		this.telefono_fijo = telefono_fijo;
		this.barrio = barrio;
		this.trabajo = trabajo;
		this.telefono_trabajo = telefono_trabajo;
	}	
	
	@Id
	@Column(name = "id_codeudor")
	public String getId_codeudor() {
		return id_codeudor;
	}

	public void setId_codeudor(String id_codeudor) {
		this.id_codeudor = id_codeudor;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	public Cliente getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Cliente id_cliente) {
		this.id_cliente = id_cliente;
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
