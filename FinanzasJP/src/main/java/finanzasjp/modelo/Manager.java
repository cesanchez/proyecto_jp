package finanzasjp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Manager {
	
	private Session session;
	private ArrayList recibos = new ArrayList();
	
	private List<Cliente_VIP> clientesVip;

	public void exit() {
		// code to close Hibernate Session factory
		session.close();
	}

	protected void create(Session session) {

		Cliente cliente = new Cliente();

		cliente.setId("1106");
		cliente.setNombre("Neil Grass");
		cliente.setApellido("Tayson");

		session.beginTransaction();

		session.save(cliente);

		session.getTransaction().commit();
	}

	protected void read() {

		String id = "1130";
		Cliente_VIP clientevip = (Cliente_VIP) session.get(Cliente_VIP.class, id);

		System.out.println("Nombre: " + clientevip.getNombre());
		System.out.println("Apellido: " + clientevip.getApellido());
		System.out.println(clientevip.getClientes().size());

		for (Cliente cl : clientevip.getClientes()) {
			System.out.println(cl.getNombre() + "  " + cl.getRecibos().size());

			for (Recibo rec : cl.getRecibos()) {
				System.out.println("Cuotas" + rec.getCuotas().size() + "dias: " + rec.getDias().size());

			}
		}

	}

	protected void update(Session session) {

		Cliente cliente = new Cliente();

		cliente.setId("1106");
		cliente.setNombre("Neil Grass");
		cliente.setApellido("Tayson");
		cliente.setTelefono("4416575");

		session.beginTransaction();

		session.update(cliente);

		session.getTransaction().commit();
	}

	protected void delete(Session session) {

		Cliente cliente = new Cliente();
		cliente.setId("1106");

		session.beginTransaction();

		session.delete(cliente);

		session.getTransaction().commit();
	}
	
	public ArrayList<Cliente> darClientes() {
		
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		
		for(Cliente_VIP cl : clientesVip) {
			clientes.addAll(cl.getClientes());
		}
		
		return clientes;		
	}
	
	public Recibo darReciboCliente(Cliente miCliente){
		
		Recibo reciboCliente = null;
		
		ArrayList<Cliente> clientes = darClientes();
		
		for(Cliente elCliente : clientes) {
			if(elCliente.getId().equals(miCliente.getId())) {
				
				for(Recibo rec : elCliente.getRecibos()) {
					if(rec.isActivo()) {
						reciboCliente = rec;
						break;
					}
				}
			}
		}		
		return reciboCliente;		
	}
	
	public ArrayList<Dia> darDiasRecibo(Recibo recibo) {
		
		ArrayList<Dia> diasRecibo = new ArrayList<Dia>();
		Recibo rec = (Recibo) session.get(Recibo.class, recibo.getId_recibo());
		
		for(Dia d: rec.getDias()) {
			diasRecibo.add(d);
		}
		
		return diasRecibo;
	}
	public void cargarInformacion() {
		
		clientesVip = session.createCriteria(Cliente_VIP.class).list();	
		System.out.println("Info:"+clientesVip.size());
	}

	protected void readRecibos() {

		List<Recibo> recibos = session.createCriteria(Recibo.class).list();
		System.out.println(recibos.size());
		
		
		Query q = session.createQuery("SELECT id, nombre FROM Cliente");
		//q.setParameter("id", id);
		List<Cliente> list = q.list();
		
		System.out.println(list.size());
	}
	
	public void setup() {
		// code to run the program
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
		registry.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = registry.buildServiceRegistry();

		// builds a session factory from the service registry
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// obtains the session
		session = sessionFactory.openSession();
	}

	/*public static void main(String[] args) {
		
		Manager manager = new Manager();
		
		manager.setup();
		manager.readRecibos();
		
		manager.read();
		// manager.create(session);
		// manager.update(session);
		// manager.delete(session);

		manager.exit();

	}*/

}
