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

		String id = "11304564";
		Cliente_VIP clientevip = (Cliente_VIP) session.get(Cliente_VIP.class, id);

		if (clientevip == null) {
			System.out.println("null papá");
		} else {

			System.out.println("Nombre: " + clientevip.getNombre());
			System.out.println("Apellido: " + clientevip.getApellido());
			System.out.println(clientevip.getClientes().size());

			for (Cliente cl : clientevip.getClientes()) {
				System.out.println(cl.getNombre() + "  " + cl.getRecibos().size());

				for (Recibo rec : cl.getRecibos()) {
					System.out.println("Cuotas" + rec.getCuotas().size() + "dias: " + rec.getDias().size());

					for (Cuota cu : rec.getCuotas()) {
						System.out.println(cu.getValor() + "pagado: " + cu.getValor_pagado());
					}

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

	public void guardarPago(double valor, int id_cuota, int id_recibo) {
		// TODO Auto-generated method stub
		Recibo recibo = (Recibo) session.get(Recibo.class, id_recibo);
		Cuota cu = recibo.darCuotaId(id_cuota);

		// Actualizar cuota
		cu.setValor_pagado(valor);
		double valorCuota = cu.getValor();
		if (valor >= valorCuota) {
			cu.setMora(false);
		}

		// Actualizar saldo recibo
		double saldo = recibo.getSaldo();
		saldo = saldo - valor;
		recibo.setSaldo(saldo);

		session.beginTransaction();
		session.update(cu);
		session.update(recibo);
		session.getTransaction().commit();
	}

	public void desactivarRecibo(int id_recibo) {

		Recibo recibo = (Recibo) session.get(Recibo.class, id_recibo);

		recibo.setActivo(false);
		session.beginTransaction();
		session.update(recibo);
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

		for (Cliente_VIP cl : clientesVip) {
			clientes.addAll(cl.getClientes());
		}

		return clientes;
	}

	public ArrayList<Cliente_VIP> darClientesVip() {

		return (ArrayList<Cliente_VIP>) clientesVip;
	}

	public Recibo darReciboCliente(Cliente miCliente) {

		Recibo reciboCliente = null;

		ArrayList<Cliente> clientes = darClientes();

		for (Cliente elCliente : clientes) {
			if (elCliente.getId().equals(miCliente.getId())) {

				for (Recibo rec : elCliente.getRecibos()) {
					if (rec.isActivo()) {
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

		for (Dia d : rec.getDias()) {
			diasRecibo.add(d);
		}

		return diasRecibo;
	}

	public void cargarInformacion() {

		clientesVip = session.createCriteria(Cliente_VIP.class).list();
	}

	public ArrayList<Cuota> generarListadoCobroDia(int dia) {

		ArrayList<Cuota> listaCuota = new ArrayList<Cuota>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		int mesActual = cal.get(Calendar.MONTH) + 1;

		for (Cliente_VIP clVi : clientesVip) {

			Set<Cliente> clientes = clVi.getClientes();

			for (Cliente cl : clientes) {

				Set<Recibo> recibos = cl.getRecibos();

				for (Recibo rec : recibos) {

					if (rec.isActivo()) {
						Set<Cuota> cuotas = rec.getCuotas();
						if (rec.contieneDia(dia)) {

							for (Cuota cu : cuotas) {

								Date fechaCob = cu.getFecha_cobro();
								int mesCob = fechaCob.getMonth() + 1;

								if (mesActual == mesCob) {
									listaCuota.add(cu);
								}
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

		String startDate = fecha;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

		for (Cliente_VIP clVi : clientesVip) {

			Set<Cliente> clientes = clVi.getClientes();

			for (Cliente cl : clientes) {

				Set<Recibo> recibos = cl.getRecibos();

				for (Recibo rec : recibos) {

					if (rec.isActivo()) {
						Set<Cuota> cuotas = rec.getCuotas();
						for (Cuota cu : cuotas) {

							if (cu.getFecha_cobro().equals(sqlStartDate)) {
								listaCuota.add(cu);
							}
						}
					}
				}
			}
		}
		return listaCuota;
	}

	public ArrayList<Cuota> generarListadoCobro(int dia, String fecha) throws ParseException {

		// null, fec
		// dia, null
		// dia, fec
		ArrayList<Cuota> listaCuota = new ArrayList<Cuota>();

		if (dia == 0 && fecha != null) {
			listaCuota.addAll(generarListadoCobroFecha(fecha));
		} else if (dia != 0 && fecha == null) {
			listaCuota.addAll(generarListadoCobroDia(dia));
		} else if (dia != 0 && fecha != null) {
			listaCuota.addAll(generarListadoCobroDia(dia));
			ArrayList<Cuota> cuotasFecha = generarListadoCobroFecha(fecha);

			for (Cuota c : cuotasFecha) {
				if (!listaCuota.contains(c)) {
					listaCuota.add(c);
				}
			}
		}
		return listaCuota;
	}

	public ArrayList<Cliente> darListaClientesCobro(int dia, String fecha) throws ParseException {

		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		ArrayList<Cuota> listaCuota = generarListadoCobro(dia, fecha);

		for (Cuota c : listaCuota) {

			Recibo rec = c.getId_recibo();
			Cliente cl = rec.getId_cliente();
			listaClientes.add(cl);
		}

		return listaClientes;
	}

	public void genListadoCsvCobro(ArrayList<Cuota> lista) {

		String userHomeFolder = System.getProperty("user.home") + "/Desktop";
		;
		File file = new File(userHomeFolder, "Hola Mundo.txt");// Creación del archivo
		try {
			FileWriter fw = new FileWriter(file); // Lo cargamos para su escritura
			BufferedWriter bw = new BufferedWriter(fw); // Lo pasamos por buffer para su manipulación
			bw.write("Nombre;Valor Cuota; Valor Pagado; Teléfono; Dirección; Id Cuota; Fecha \n");

			for (Cuota c : lista) {

				Recibo rec = c.getId_recibo();
				int numRec = rec.getId_recibo();

				Cliente cl = rec.getId_cliente();
				String nom = cl.getNombre() + " " + cl.getApellido();
				String tel = cl.getTelefono();
				String dir = cl.getDireccion();

				bw.write(nom + ";" + c.getValor() + ";" + c.getValor_pagado() + ";" + tel + ";" + dir + ";"
						+ c.getId_cuota() + ";" + c.getFecha_cobro() + "\n");
			}

			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void generarListadoCobro_hql(int dia, String fecha) {

		// String hql = "select c.valor, c.fecha_cobro, c.id_cuota, c.mora,
		// c.valor_pagado, c.id_recibo from Cuota as c, Recibo as r " +
		// "join r.dias rd "+
		// "where c.id_recibo = r.id_recibo " +
		// "and rd.dia = :dia " +
		// "and c.mora = FALSE " +
		// "and r.activo = true ";

		String hql = "select distinct c.valor, c.fecha_cobro, c.id_cuota, c.id_recibo from Cuota as c";

		Query q = session.createQuery(hql);
		// q.setParameter("dia", 5);
		// q.setParameter("fecha", "2018-02-01");
		// List<Cuota> list = (List<Cuota>) q.list();

		List<Cuota> list = q.setResultTransformer(Transformers.aliasToBean(Cuota.class)).list();

		System.out.println(list.size());

		for (Cuota c : list) {
			System.out.println(c.getId_cuota() + " " + c.getValor());
		}
	}

	protected void readRecibos() {

		List<Recibo> recibos = session.createCriteria(Recibo.class).list();
		System.out.println(recibos.size());

		Query q = session.createQuery("SELECT id, nombre FROM Cliente");
		// q.setParameter("id", id);
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

	public ArrayList<Cuota> darCuotasCliente(Cliente cliente) {

		ArrayList<Cuota> cuotasCliente = new ArrayList<Cuota>();
		Recibo rec = darReciboCliente(cliente);
		cuotasCliente = darCuotasRecibo(rec);

		return cuotasCliente;
	}

	public ArrayList<Cuota> darCuotasRecibo(Recibo rec) {

		ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
		for (Cuota c : rec.getCuotas()) {
			cuotas.add(c);
		}

		return cuotas;
	}

	public boolean guardarCliente(String cedCliVip, String ced, String nombre, String apellido, String tel,
			String dir) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Cliente_VIP clVip = (Cliente_VIP) session.get(Cliente_VIP.class, cedCliVip);
		Cliente elCliente = (Cliente) session.get(Cliente.class, ced);

		session.beginTransaction();
		// Si el cliente no existe en la db, entonces se crea uno nuevo y guarda.
		if (elCliente == null) {
			elCliente = new Cliente(ced, nombre, apellido, dir, tel, clVip);
			session.save(elCliente);
			ret = true;
		} else {
			ret = false;
		}
		session.getTransaction().commit();
		return ret;

	}

	public boolean guardarRecibo(int id_rec, String ced, double prestamo, double interes, String fechaI, String fechaF,
			double pagoTotal, ArrayList<Integer> dias) throws ParseException {

		boolean ret = false;
		Recibo elRecibo = (Recibo) session.get(Recibo.class, id_rec);

		String startDate = fechaI;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateI = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(dateI.getTime());

		String endtDate = fechaF;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateF = sdf2.parse(endtDate);
		java.sql.Date sqlEndDate = new java.sql.Date(dateF.getTime());

		session.beginTransaction();
		// Si el recibo no existe en la bd, se crea uno nuevo y se guarda.
		if (elRecibo == null) {
			// create
			Cliente cl = (Cliente) session.get(Cliente.class, ced);
			elRecibo = new Recibo(id_rec, pagoTotal, prestamo, true, false, pagoTotal, interes, false, sqlStartDate,
					sqlEndDate, cl);
			
			//Verificar si el arraylist dias no está vacío
			if(!dias.isEmpty()) {
				Set<Dia> losDias = new HashSet<Dia>();
				for (Integer d : dias) {
					Dia elDia = new Dia(d.intValue());
					losDias.add(elDia);
				}
				elRecibo.setDias(losDias);
			}						
			session.save(elRecibo);
			ret = true;
		} else {
			ret = false;
		}
		session.getTransaction().commit();
		return ret;
	}

	public int darNumUltimoRecibo() {
		int num = 0;

		List<Recibo> losRecibos = session.createCriteria(Recibo.class).list();
		ComparadorRecibo compa = new ComparadorRecibo();
		losRecibos.sort(compa);
		int index = losRecibos.size();
		System.out.println(index);
		Recibo elRecibo = losRecibos.get(index - 1);
		num = elRecibo.getId_recibo();
		return num;
	}

	public ArrayList<Cliente> darClientes(String id_clVip) {

		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Cliente_VIP clVip = (Cliente_VIP) session.get(Cliente_VIP.class, id_clVip);
		for(Cliente c : clVip.getClientes()) {
			clientes.add(c);
		}
		
		return clientes;
	}

	public boolean guardarClienteVip(String id, String nombre, String tel, double capital) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Cliente_VIP elCliente = (Cliente_VIP) session.get(Cliente_VIP.class, id);

		session.beginTransaction();
		// Si el cliente no existe en la db, entonces se crea uno nuevo y guarda.
		if (elCliente == null) {
			String [] dat = nombre.split(" ");
			elCliente = new Cliente_VIP(id, dat[0], dat[1], tel, capital);
			session.save(elCliente);
			ret = true;
		} else {
			ret = false;
		}
		session.getTransaction().commit();
		return ret;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * Manager manager = new Manager();
	 * 
	 * manager.setup(); manager.cargarInformacion(); //manager.readRecibos();
	 * //manager.generarListadoCobro_hql(2, ""); //manager.read(); //
	 * manager.create(session); // manager.update(session); //
	 * manager.delete(session);
	 * 
	 * //ArrayList<Cuota> cuotas = manager.generarListadoCobroDia(5); //
	 * ArrayList<Cuota> cuotas; // // try { // cuotas =
	 * manager.generarListadoCobro(5,"2018-01-01"); //
	 * manager.genListadoCsvCobro(cuotas); // } catch (ParseException e) { // //
	 * TODO Auto-generated catch block // e.printStackTrace(); // }
	 * 
	 * 
	 * 
	 * 
	 * manager.exit();
	 * 
	 * }
	 */

}
