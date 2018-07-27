package finanzasjp.modelo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "CLIENTE")
public class Cliente {
	
	private long id; 
	private String nombre; 
	private String apellido;
	private String direccion; 
	private String telefono; 
	
	private Cliente_VIP id_cliente_vip;
	
	public Cliente() {
		
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
//	
//    @ManyToOne
//    @JoinColumn(name = "id_cliente_vip")
	public Cliente_VIP getId_cliente_vip() {
		return id_cliente_vip;
	}

	
	

}
