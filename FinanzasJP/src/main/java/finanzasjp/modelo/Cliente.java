package finanzasjp.modelo;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "cliente")
public class Cliente {
	
	private String id; 
	private String nombre; 
	private String apellido;
	private String direccion; 
	private String telefono; 
	
	private Cliente_VIP cliente_vip;
	private Set<Recibo> recibos;	
	
	public Cliente() {
		
	}
		
    public Cliente(String id, String nombre, String apellido, String direccion, String telefono,
			Cliente_VIP cliente_vip, Set<Recibo> recibos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.cliente_vip = cliente_vip;
		this.recibos = recibos;
	}
    
    public Cliente(String id, String nombre, String apellido, String direccion, String telefono,
			Cliente_VIP cliente_vip) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.cliente_vip = cliente_vip;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@ManyToOne
    @JoinColumn(name = "id_cliente_vip")
	public Cliente_VIP getCliente_vip() {
		return cliente_vip;
	}

	public void setCliente_vip(Cliente_VIP cliente_vip) {
		this.cliente_vip = cliente_vip;
	}
	
	@OneToMany(mappedBy = "id_cliente", targetEntity=Recibo.class, cascade = CascadeType.ALL)
	public Set<Recibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(Set<Recibo> recibos) {
		this.recibos = recibos;
	}
		
	public Recibo darRecibo(int id_recibo) {
		Recibo ret = null;
		
		for(Recibo re : recibos) {
			if(re.getId_recibo() == id_recibo) {
				ret = re;
				break;
			}
		}
		
		return ret;
	}
	
		
}
