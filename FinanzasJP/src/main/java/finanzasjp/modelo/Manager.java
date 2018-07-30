package finanzasjp.modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.transform.Transformers;

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
				
				for(Cuota cu : rec.getCuotas()) {
					System.out.println(cu.getValor() +"pagado: " + cu.getValor_pagado());
				}

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
	
	public ArrayList<Cuota> generarListadoCobroDia(int dia) {
		
		ArrayList<Cuota> listaCuota = new ArrayList<Cuota>();
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        int mesActual = cal.get(Calendar.MONTH) + 1;
        
		for(Cliente_VIP clVi : clientesVip) {
			
			Set<Cliente> clientes = clVi.getClientes();
			
			for(Cliente cl : clientes) {
				
				Set<Recibo> recibos = cl.getRecibos();
			
				for(Recibo rec : recibos) {
					
					Set<Cuota> cuotas = rec.getCuotas();
					
					if(rec.contieneDia(dia)) {
						
						for(Cuota cu : cuotas) {
							
							Date fechaCob = cu.getFecha_cobro();
							int mesCob = fechaCob.getMonth()+1;
							
							if(mesActual == mesCob) {
								listaCuota.add(cu);
							}							
						}						
					}						
				}
			}			
		}
		return listaCuota;
	}
	
	public ArrayList<Cuota> generarListadoCobroFecha(String fecha) throws ParseException {
		
		ArrayList<Cuota> listaCuota = new ArrayList<Cuota>();

		String startDate= fecha;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime()); 
		
		//int mesFecha = sqlStartDate.getMonth() +1 ;
				
		for(Cliente_VIP clVi : clientesVip) {
			
			Set<Cliente> clientes = clVi.getClientes();
			
			for(Cliente cl : clientes) {
				
				Set<Recibo> recibos = cl.getRecibos();
			
				for(Recibo rec : recibos) {
					
					Set<Cuota> cuotas = rec.getCuotas();
					for(Cuota cu : cuotas) {
						
						if(cu.getFecha_cobro().equals(sqlStartDate)) {
							listaCuota.add(cu);
						}
					}
				}
			}			
		}
		return listaCuota;
	}
	
	public ArrayList<Cuota> generarListadoCobro(int dia, String fecha) {
		
		ArrayList<Cuota> listaCuota = new ArrayList<Cuota>();
		
		listaCuota.addAll(generarListadoCobroDia(dia));
		
		if(fecha != null) {
			try {
				ArrayList<Cuota> cuotasFecha = generarListadoCobroFecha(fecha);
				
				for(Cuota c:cuotasFecha) {
					if(!listaCuota.contains(c)) {
						listaCuota.add(c);
					}
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaCuota;
		
	}
	
	public void genListadoCsvCobro(ArrayList<Cuota> lista) {
			
		String userHomeFolder = System.getProperty("user.home") + "/Desktop";;		
		File file = new File (userHomeFolder, "Hola Mundo.txt");//Creación del archivo 
		try {
			FileWriter fw = new FileWriter(file); //Lo cargamos para su escritura
			BufferedWriter bw = new BufferedWriter(fw); //Lo pasamos por buffer para su manipulación
			bw.write("Nombre;Valor Cuota; Valor Pagado; Teléfono; Dirección; Id Cuota; Fecha \n");
			
			for(Cuota c :lista) {
				
				Recibo rec = c.getId_recibo();
				int numRec = rec.getId_recibo();
				
				Cliente cl = rec.getId_cliente();
				String nom = cl.getNombre() + " " + cl.getApellido();
				String tel = cl.getTelefono();
				String dir = cl.getDireccion();
				
				bw.write(nom+";"+c.getValor() + ";" + c.getValor_pagado() + ";" + tel + ";" + dir + ";" + c.getId_cuota() + ";" + c.getFecha_cobro() + "\n" );
			}
			
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void generarListadoCobro_hql(int dia, String fecha) {
		
//		String hql = "select c.valor, c.fecha_cobro, c.id_cuota, c.mora, c.valor_pagado, c.id_recibo from Cuota as c, Recibo as r " + 
//				"join r.dias rd "+
//				"where c.id_recibo = r.id_recibo " + 
//				"and rd.dia = :dia " +
//				"and c.mora = FALSE " + 
//				"and r.activo = true ";
		
		String hql = "select distinct c.valor, c.fecha_cobro, c.id_cuota, c.id_recibo from Cuota as c";
		
		Query q = session.createQuery(hql);
		//q.setParameter("dia", 5);
		//q.setParameter("fecha", "2018-02-01");
		//List<Cuota> list = (List<Cuota>) q.list();
		
		List<Cuota> list = q.setResultTransformer(Transformers.aliasToBean(Cuota.class) ).list();
	       
		
		System.out.println(list.size());
		
		for(Cuota c : list) {
			System.out.println(c.getId_cuota() + " " + c.getValor());
		}		
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
		manager.cargarInformacion();
		//manager.readRecibos();
		//manager.generarListadoCobro_hql(2, "");
		//manager.read();
		// manager.create(session);
		// manager.update(session);
		// manager.delete(session);
		
		//ArrayList<Cuota> cuotas = manager.generarListadoCobroDia(5);
		ArrayList<Cuota> cuotas;
		
			cuotas = manager.generarListadoCobro(5,"2018-01-01");
			
			manager.genListadoCsvCobro(cuotas);
		

		manager.exit();

	}*/

}
