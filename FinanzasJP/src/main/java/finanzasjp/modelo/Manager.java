package finanzasjp.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jmx.SessionFactoryStub;
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

		session.beginTransaction();

		session.update(cliente);

		session.getTransaction().commit();
	}

	public void guardarPago(double valor, double newValorCuota, int id_cuota, int id_recibo) {
		// TODO Auto-generated method stub
		Recibo recibo = (Recibo) session.get(Recibo.class, id_recibo);
		Cuota cu = recibo.darCuotaId(id_cuota);
		double valorCuota = cu.getValor();
		double saldo = recibo.getSaldo();
		double dif = 0;
		// Actualizar valor cuota, si es necesario
		if (newValorCuota != valorCuota) {
			cu.setValor(newValorCuota);
			dif = newValorCuota - valorCuota;

			// if (dif > 0) {
			// saldo = saldo + newValorCuota;
			// } else {
			// saldo = saldo - newValorCuota;
			// }
		}

		// Actualizar cuota
		cu.setValor_pagado(valor);
		if (valor >= valorCuota) {
			cu.setMora(false);
		}

		// Actualizar saldo recibo
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

		ArrayList<Cliente> clientes = (ArrayList<Cliente>) session.createCriteria(Cliente.class).list();
		ComparadorCliente c = new ComparadorCliente();
		clientes.sort(c);

		ArrayList<Codeudor> code = (ArrayList<Codeudor>) session.createCriteria(Codeudor.class).list();
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

	public ArrayList<Dia_Recibo> darDiasRecibo(Recibo recibo) {

		ArrayList<Dia_Recibo> diasRecibo = new ArrayList<Dia_Recibo>();
		Recibo rec = (Recibo) session.get(Recibo.class, recibo.getId_recibo());

		for (Dia_Recibo d : rec.getDias()) {
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
								if (cu.isMora()) {
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

							if (cu.getFecha_cobro() != null) {
								if (cu.getFecha_cobro().equals(sqlStartDate)) {
									listaCuota.add(cu);
								}

								if (cu.isMora()) {
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

			if (!listaClientes.contains(cl)) {
				listaClientes.add(cl);
			}
		}
		ComparadorCliente com = new ComparadorCliente();
		listaClientes.sort(com);
		return listaClientes;
	}

	public ArrayList<Cliente> darListaClientesMora() {

		ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) session.createCriteria(Cliente.class).list();
		ArrayList<Cliente> clientesMora = new ArrayList<Cliente>();

		for (Cliente c : listaClientes) {

			for (Recibo r : c.getRecibos()) {

				if (r.isActivo() && r.isMora()) {

					clientesMora.add(c);
				}
			}
		}
		return clientesMora;
	}

	public void genListadoCsvCobro(ArrayList<Cuota> lista) throws IOException {

		Workbook workbook = new XSSFWorkbook();
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet();

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		String[] headerValues = { "Nombre", "Id Cuota", "Valor Total", "Teléfono" };

		for (int i = 0; i < headerValues.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headerValues[i]);
			cell.setCellStyle(headerCellStyle);
		}

		String idCuotas = "";
		double valorTotal = 0;
		String nom = "";
		int idReCuota = 0;

		int numRow = 1;
		int numCuota = 1;
		int idReCuotaAux = 0;
		for (Cuota c : lista) {

			Recibo rec = c.getId_recibo();
			Cliente cl = rec.getId_cliente();

			idReCuota = rec.getId_recibo();
			if (numCuota == 1) {
				idReCuotaAux = idReCuota;
			}

			String tel = cl.getTelefono_celular();
			nom = cl.getNombre() + " " + cl.getApellido();

			if (idReCuota == idReCuotaAux) {
				idCuotas += c.getId_cuota() + ";";
				valorTotal += c.getValor();
			} else {
				Row row = sheet.createRow(numRow);
				row.createCell(0).setCellValue(nom);
				row.createCell(1).setCellValue(idCuotas);
				row.createCell(2).setCellValue(valorTotal);
				row.createCell(3).setCellValue(tel);

				nom = cl.getNombre() + " " + cl.getApellido();
				idCuotas = c.getId_cuota() + ";";
				valorTotal = c.getValor();

				numRow++;
			}

			idReCuotaAux = idReCuota;
			numCuota++;
		}

		String userHomeFolder = System.getProperty("user.home") + "/Desktop";
		File file = new File(userHomeFolder, "ListaDeCobro.xlsx");
		FileOutputStream fileout = new FileOutputStream(file);

		workbook.write(fileout);
		fileout.close();
		workbook.close();

	}

	public void generarListadoCsvMora() throws IOException {

		Workbook workbook = new XSSFWorkbook();
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet();

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		String[] headerValues = { "Nombre Cliente", "Teléfono", "Dirección", "Id_cuota", "Fecha Cobro", "Valor Cuota",
				"Valor Pagado", "Días Mora", "Valor Mora" };

		for (int i = 0; i < headerValues.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headerValues[i]);
			cell.setCellStyle(headerCellStyle);
		}

		ArrayList<Cliente> listaClientes = darListaClientesMora();

		int numRow = 1;
		for (Cliente cl : listaClientes) {

			String nom = cl.getNombre() + " " + cl.getApellido();
			ArrayList<Mora> moras = darInfMoraCliente(cl.getId());

			for (Mora mora : moras) {
				Row row = sheet.createRow(numRow);
				row.createCell(0).setCellValue(nom);
				row.createCell(1).setCellValue(cl.getTelefono_celular());
				row.createCell(2).setCellValue(cl.getDireccion());
				row.createCell(3).setCellValue(mora.getCuota().getId_cuota());
				row.createCell(4).setCellValue(mora.getCuota().getFecha_cobro().toString());
				row.createCell(5).setCellValue(mora.getCuota().getValor());
				row.createCell(6).setCellValue(mora.getCuota().getValor_pagado());
				row.createCell(7).setCellValue(mora.getDiasMora());
				row.createCell(8).setCellValue(mora.getValorMora());

				numRow++;
			}
		}

		String userHomeFolder = System.getProperty("user.home") + "/Desktop";
		File file = new File(userHomeFolder, "ListaDeCuotasEnMora.xlsx");
		FileOutputStream fileout = new FileOutputStream(file);

		workbook.write(fileout);
		fileout.close();
		workbook.close();
	}

	/*
	 * public void genListadoCsvCobro(ArrayList<Cuota> lista) throws IOException {
	 * 
	 * Workbook workbook = new XSSFWorkbook(); org.apache.poi.ss.usermodel.Sheet
	 * sheet = workbook.createSheet();
	 * 
	 * Font headerFont = workbook.createFont(); headerFont.setBold(true);
	 * headerFont.setFontHeightInPoints((short)17);
	 * headerFont.setColor(IndexedColors.RED.getIndex());
	 * 
	 * CellStyle headerCellStyle = workbook.createCellStyle();
	 * headerCellStyle.setFont(headerFont);
	 * 
	 * Row headerRow = sheet.createRow(0);
	 * 
	 * Cell cell = headerRow.createCell(4); cell.setCellValue("HellomyFriend");
	 * cell.setCellStyle(headerCellStyle);
	 * 
	 * Row row = sheet.createRow(1);
	 * 
	 * row.createCell(0).setCellValue("toobntos");
	 * 
	 * FileOutputStream fileout = null; try { fileout = new
	 * FileOutputStream("myFisrt.xlsx"); } catch (FileNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } workbook.write(fileout);
	 * fileout.close(); workbook.close();
	 * 
	 * 
	 * }
	 */

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

	public ArrayList<Cuota> darCuotasCliente(String cliente) {

		ArrayList<Cuota> cuotasCliente = new ArrayList<Cuota>();
		// Cliente miCliente = (Cliente) session.get(Cliente.class, cliente);

		// Buscar el cliente, dentro de todos los clientes

		Cliente miCliente = null;
		for (Cliente_VIP clVip : clientesVip) {
			miCliente = clVip.buscarCliente(cliente);
			if (miCliente != null) {
				break;
			}
		}

		Recibo rec = darReciboCliente(miCliente);
		cuotasCliente = darCuotasRecibo(rec);
		ComparadorCuota c = new ComparadorCuota();
		cuotasCliente.sort(c);

		return cuotasCliente;
	}

	public ArrayList<Cuota> darCuotasRecibo(Recibo rec) {

		ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
		for (Cuota c : rec.getCuotas()) {
			cuotas.add(c);
		}

		return cuotas;
	}

	public boolean guardarCliente(String cedCliVip, String ced, String nombre, String apellido, String tel, String dir,
			String telFijo, String barrio, String trabajo, String telTrabajo) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Cliente_VIP clVip = (Cliente_VIP) session.get(Cliente_VIP.class, cedCliVip);
		Cliente elCliente = (Cliente) session.get(Cliente.class, ced);

		session.beginTransaction();
		// Si el cliente no existe en la db, entonces se crea uno nuevo y guarda.
		if (elCliente == null) {
			elCliente = new Cliente(ced, nombre, apellido, dir, tel, telFijo, barrio, trabajo, telTrabajo, clVip);
			clVip.addCliente(elCliente);
			session.save(elCliente);
			ret = true;
		} else {
			ret = false;
		}
		session.getTransaction().commit();
		return ret;

	}

	public boolean guardarRecibo(int id_rec, String ced, double prestamo, double miInteres, String fechaI,
			String fechaF, double pagoTotal, ArrayList<Integer> dias) throws ParseException {

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

		double interes = miInteres / 100;

		session.beginTransaction();
		// Si el recibo no existe en la bd, se crea uno nuevo y se guarda.
		if (elRecibo == null) {
			// create
			Cliente cl = (Cliente) session.get(Cliente.class, ced);
			elRecibo = new Recibo(id_rec, pagoTotal, prestamo, true, false, pagoTotal, interes, false, sqlStartDate,
					sqlEndDate, cl);

			// Verificar si el arraylist dias no está vacío
			if (!dias.isEmpty()) {
				Set<Dia_Recibo> losDias = new HashSet<Dia_Recibo>();
				for (Integer d : dias) {
					Dia_Recibo elDia = new Dia_Recibo(d.intValue(), elRecibo);
					losDias.add(elDia);
				}
				elRecibo.setDias(losDias);
			}
			cl.addRecibo(elRecibo);
			// session.update(cl);
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
		for (Cliente c : clVip.getClientes()) {
			clientes.add(c);
		}

		return clientes;
	}

	public boolean guardarClienteVip(String id, String nombre, String apellido, String tel, double capital) {
		// TODO Auto-generated method stub
		boolean ret = false;
		Cliente_VIP elCliente = (Cliente_VIP) session.get(Cliente_VIP.class, id);

		session.beginTransaction();
		// Si el cliente no existe en la db, entonces se crea uno nuevo y guarda.
		if (elCliente == null) {
			elCliente = new Cliente_VIP(id, nombre, apellido, tel, capital);
			session.save(elCliente);
			ret = true;
		} else {
			ret = false;
		}
		session.getTransaction().commit();
		return ret;
	}

	public double calcularInteresTotal(int miCuotas, double interes, int modo) {
		double interesTotal = 0;
		int numMeses = 0;

		double cuotas = (double) miCuotas;

		switch (modo) {
		// 1. Modo de pago Mensual
		case 1:
			numMeses = (int) cuotas;
			break;
		// 2. Modo de pago Quincenal
		case 2:
			numMeses = (int) Math.round(cuotas / 2);
			break;
		// 3. Modo de pago Semanal
		case 3:
			numMeses = (int) cuotas / 4;
			break;
		// 4. Modo de pago Diario
		default:
			break;
		}

		interesTotal = numMeses * interes;
		return interesTotal;
	}

	public double calcularPagoTotal(double valorPrestamo, double miInteres, int modo, int numCuotas) {
		double pagoTotal = 0;
		double valorInteres = 0;
		double interes = miInteres;
		double interesTotal = calcularInteresTotal(numCuotas, interes, modo);
		valorInteres = interesTotal * valorPrestamo;
		pagoTotal = valorInteres + valorPrestamo;
		return pagoTotal;
	}

	public double calcularPagoTotalLabel(double valorPrestamo, double miInteres, int modo, int numCuotas) {
		double pagoTotal = 0;
		double valorInteres = 0;
		double interes = miInteres / 100;
		double interesTotal = calcularInteresTotal(numCuotas, interes, modo);
		valorInteres = interesTotal * valorPrestamo;
		pagoTotal = valorInteres + valorPrestamo;
		return pagoTotal;
	}

	public double calcularValorCuota(double valorPrestamo, double interes, int modo, int numCuotas) {

		double pagoTotal = calcularPagoTotal(valorPrestamo, interes, modo, numCuotas);
		double valorPagoCuota = pagoTotal / numCuotas;

		return valorPagoCuota;
	}

	public void cargarPagos() {

		File file = new File("infoPagos.txt");// Carga el archivo

		try {
			FileReader reader = new FileReader(file); // Se prepara para la lectura del archivo
			BufferedReader br = new BufferedReader(reader); // Se carga en el buffer para su manipulación
			String line = "";

			while ((line = br.readLine()) != null) { // Se leen las lineas hasta el final del documento
				String[] data = line.split(";");
				
				if (data[0].equals("2419")) {
					System.out.println("entro");
				}

				if (!data[0].equals("NH")) {
					int idrec = Integer.parseInt(data[0]);
					double[] pagos = new double[3];
					pagos[0] = Double.parseDouble(data[1]);
					pagos[1] = Double.parseDouble(data[2]);
					pagos[2] = Double.parseDouble(data[3]);
//					pagos[3] = Double.parseDouble(data[4]);
//					pagos[4] = Double.parseDouble(data[5]);
//					pagos[5] = Double.parseDouble(data[6]);
//					pagos[6] = Double.parseDouble(data[7]);
//					pagos[7] = Double.parseDouble(data[8]);
//					pagos[8] = Double.parseDouble(data[9]);
//					pagos[9] = Double.parseDouble(data[10]);
//					pagos[10] = Double.parseDouble(data[11]);
//					pagos[11] = Double.parseDouble(data[12]);
//					pagos[12] = Double.parseDouble(data[13]);
//					pagos[13] = Double.parseDouble(data[14]);

					Recibo miRec = (Recibo) session.get(Recibo.class, idrec);

					if (miRec != null) {
						Set<Cuota> ctas = miRec.getCuotas();
						if (!ctas.isEmpty() || ctas != null) {
							for (Cuota c : ctas) {
								int id = c.getId_cuota();
								for (int i = 0; i < pagos.length; i++) {
									if (id == (i+1)) {
										c.setValor_pagado(pagos[i]);
									}
								}
							}
						}
					}
				}

			}
			br.close(); // Se cierra el buffer

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

		}

	}

	public void cargarCuotas() {

		File file = new File("infoRecibos.txt");// Carga el archivo

		try {
			FileReader reader = new FileReader(file); // Se prepara para la lectura del archivo
			BufferedReader br = new BufferedReader(reader); // Se carga en el buffer para su manipulación
			String line = "";

			while ((line = br.readLine()) != null) { // Se leen las lineas hasta el final del documento
				String[] data = line.split(";");
				String idrec = data[0];
				String interes = data[1];
				String prestamo = data[2];
				String diaCobro = data[3];
				String cuotas = data[7];
				String modo = data[9];

				int idRecibo = Integer.parseInt(idrec);
				double valorPrestamo = Double.parseDouble(prestamo);
				double miInteres = Double.parseDouble(interes);
				int numCuotas = Integer.parseInt(cuotas);
				int miModo = Integer.parseInt(modo);

				String[] dias = diaCobro.split("-");
				Recibo miRec = (Recibo) session.get(Recibo.class, idRecibo);
				for (int i = 0; i < dias.length; i++) {
					Dia_Recibo diaRec = new Dia_Recibo(Integer.parseInt(dias[i].trim()), miRec);
					miRec.addDia(diaRec);
					session.save(diaRec);
				}

				generarCuotas(valorPrestamo, miInteres, miModo, numCuotas, idRecibo);

			}

			br.close(); // Se cierra el buffer

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

		}

	}

	public boolean generarCuotas(double valorPrestamo, double miInteres, int modo, int numCuotas, int idRecibo) {

		double interes = miInteres / 100;
		boolean res = false;
		Recibo recibo = (Recibo) session.get(Recibo.class, idRecibo);
		double valor = calcularValorCuota(valorPrestamo, interes, modo, numCuotas);
		double valorInteresTotal = calcularInteresTotal(numCuotas, interes, modo) * valorPrestamo;
		double valorInteres = valorInteresTotal / numCuotas;
		double valorCapital = valor - valorInteres;

		valor = Math.round(valor);
		valorInteres = Math.round(valorInteres);
		valorCapital = Math.round(valorCapital);

		session.beginTransaction();

		try {

			Date fecha = recibo.getFecha_prestamo();
			Set<Dia_Recibo> dias = recibo.getDias();
			ArrayList<Dia_Recibo> arrayDias = new ArrayList<Dia_Recibo>();
			int[] misDias = new int[dias.size()];

			for (Dia_Recibo d : dias) {
				arrayDias.add(d);
			}

			arrayDias.sort(new ComparadorDia());

			int j = 0;
			for (Dia_Recibo d : arrayDias) {
				misDias[j] = d.getId_dia();
				j++;
			}

			int elMes = 0;
			boolean cambio = false;
			for (int i = 1; i <= numCuotas; i++) {

				// tomar la fecha inicial del recibo, y apartir de esa fecha almacenar las
				// cuotas de acuerdo al modo
				Date fechaProx = null;

				switch (modo) {
				// mensual
				case 1:
					int day = fecha.getDate();
					int month = fecha.getMonth() + i;
					int year = fecha.getYear();
					fechaProx = new Date(year, month, day);

					if (!dias.isEmpty() && dias.size() == 1) {
						for (Dia_Recibo d : dias) {
							fechaProx.setDate(d.getId_dia());
						}
					}
					break;
				// quincenal
				case 2:

					int dayQ = fecha.getDate();
					int monthQ = fecha.getMonth();
					int yearQ = fecha.getYear();
					fechaProx = new Date(yearQ, monthQ, dayQ);

					// Calendar proxiQ = Calendar.getInstance();
					// proxiQ.set(yearQ, monthQ, dayQ + (15*i));
					java.util.Date proxiQDate = (java.util.Date) fechaProx;
					proxiQDate.setDate(dayQ + (15 * i));
					long proxiQ_Time = proxiQDate.getTime();

					if (!dias.isEmpty() && dias.size() == 2) {

						int pri = misDias[0];
						int sec = misDias[1];
						int mes = proxiQDate.getMonth();
						int anio = proxiQDate.getYear();

						if (cambio) {
							if (mes == elMes) {
								mes = mes + 1;
							}
						}

						// pri
						Date priQ = new Date(anio, mes, pri);
						java.util.Date priQDate = (java.util.Date) priQ;
						long priQ_Time = priQDate.getTime();
						// sec
						Date secQ = new Date(anio, mes, sec);
						java.util.Date secQDate = (java.util.Date) secQ;
						long secQ_Time = secQDate.getTime();

						long diffTimePri = Math.abs(proxiQ_Time - priQ_Time);
						long diffDaysPri = diffTimePri / (1000 * 60 * 60 * 24);

						long diffTimeSec = Math.abs(proxiQ_Time - secQ_Time);
						long diffDaysSec = diffTimeSec / (1000 * 60 * 60 * 24);

						if (diffDaysPri > diffDaysSec) {
							fechaProx = secQ;
							cambio = true;
						} else {
							fechaProx = priQ;
							cambio = false;
						}
					}

					break;

				// semanal
				case 3:

				default:
					break;

				}

				Cuota cuota = new Cuota(i, valor, 0, false, valorInteres, valorCapital, recibo);
				cuota.setFecha_cobro(fechaProx);
				recibo.addCuota(cuota);
				session.save(cuota);

				elMes = fechaProx.getMonth();
			}
		} catch (Exception e) {
			System.out.println("Excepcion: Cuotas ya guardadas");
		}
		session.getTransaction().commit();

		res = true;
		return res;
	}

	public boolean guardarFechaCuota(String fecha, int idRecibo, int idCuota, double valorPagar) throws ParseException {
		// TODO Auto-generated method stub
		String startDate = fecha;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateI = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(dateI.getTime());

		Recibo elRecibo = (Recibo) session.get(Recibo.class, idRecibo);
		Cuota laCuota = elRecibo.darCuotaId(idCuota);
		laCuota.setFecha_cobro(sqlStartDate);
		laCuota.setValor(valorPagar);

		session.beginTransaction();
		session.update(laCuota);
		session.getTransaction().commit();

		return true;

	}

	public boolean actualizarEstadoCuota(String fechaActual) throws ParseException {
		// TODO Auto-generated method stub
		boolean re = false;
		List<Cuota> cuotas = session.createCriteria(Cuota.class).list();

		String startDate = fechaActual;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateI = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(dateI.getTime());

		session.beginTransaction();
		for (Cuota c : cuotas) {
			java.sql.Date fechaCobro = c.getFecha_cobro();
			if (fechaCobro != null) {
				int com = fechaCobro.compareTo(sqlStartDate);
				double valor = c.getValor();
				if (com < 0) {
					double valPagado = c.getValor_pagado();
					if (valPagado < valor) {
						c.setMora(true);

						Recibo recibo = c.getId_recibo();
						recibo.setMora(true);

						session.update(c);
						session.update(recibo);
					} else {
						c.setMora(false);
						session.update(c);
					}
				}
			}
		}

		// Verifique las cuotas de todos los recibos y verifique si salieron de mora
		ArrayList<Recibo> losRecibos = (ArrayList<Recibo>) session.createCriteria(Recibo.class).list();
		for (Recibo r : losRecibos) {
			if (r.isActivo()) {
				boolean mora = false;
				for (Cuota c : r.getCuotas()) {
					java.sql.Date fechaCobro = c.getFecha_cobro();
					if (fechaCobro != null) {
						int com = fechaCobro.compareTo(sqlStartDate);
						if (com < 0) {
							if (c.getValor_pagado() < c.getValor()) {
								mora = true;
							}
						}
					}
				}
				session.update(r);
			}
		}

		session.getTransaction().commit();
		re = true;
		return re;
	}

	public String validarReciboActivoCliente(String idCliente) {

		Cliente miCliente = (Cliente) session.get(Cliente.class, idCliente);
		String recActivo = miCliente.reciboActivo();

		return recActivo;
	}

	public ArrayList<Cuota> darCuotasMoraCliente(String idCliente) {
		// TODO Auto-generated method stub
		ArrayList<Cuota> cuotasMora = new ArrayList<Cuota>();
		Cliente miCliente = (Cliente) session.get(Cliente.class, idCliente);

		for (Recibo r : miCliente.getRecibos()) {

			if (r.isMora() && r.isActivo()) {
				for (Cuota c : r.getCuotas()) {
					if (c.isMora()) {
						cuotasMora.add(c);
					}
				}
			}
		}

		ComparadorCuota cmCu = new ComparadorCuota();
		cuotasMora.sort(cmCu);
		return cuotasMora;
	}

	public ArrayList<Mora> darInfMoraCliente(String idCliente) {

		ArrayList<Mora> infMora = new ArrayList<Mora>();

		Calendar actual = Calendar.getInstance();
		actual.setTime(new java.util.Date());
		java.util.Date actualDate = actual.getTime();
		long actualTime = actualDate.getTime();

		for (Cuota c : darCuotasMoraCliente(idCliente)) {
			Date cobroDate = c.getFecha_cobro();
			long cobroTime = cobroDate.getTime();
			long diffTime = actualTime - cobroTime;
			long diffDays = diffTime / (1000 * 60 * 60 * 24);

			infMora.add(new Mora(c, diffDays, (c.getValor() - c.getValor_pagado())));
		}

		return infMora;
	}

	public boolean guardarCodeudor(String idCliente, String nombre, String apellido, String cedula, String telFijo,
			String trabajo, String telCelular, String direccion, String barrio, String telTrabajo, boolean activo) {
		// TODO Auto-generated method stub
		boolean ret = false;
		session.beginTransaction();

		Cliente miCliente = (Cliente) session.get(Cliente.class, idCliente);
		Codeudor miCode = new Codeudor(cedula, nombre, apellido, direccion, telCelular, telFijo, barrio, trabajo,
				telTrabajo, miCliente);
		miCliente.addCodeudor(miCode);

		session.save(miCode);
		session.getTransaction().commit();

		ret = true;

		return ret;

	}

	public boolean actualizarCliente(String idCliente, String nom, String telFijo, String dir, String trabajo,
			String telCelular, String telTrabajo, String barrio) {
		// TODO Auto-generated method stub
		session.beginTransaction();

		Cliente miCliente = (Cliente) session.get(Cliente.class, idCliente);

		miCliente.setNombre(nom);
		miCliente.setTelefono_fijo(telFijo);
		miCliente.setDireccion(dir);
		miCliente.setTrabajo(trabajo);
		miCliente.setTelefono_celular(telCelular);
		miCliente.setTelefono_trabajo(telTrabajo);
		miCliente.setBarrio(barrio);

		session.update(miCliente);

		session.getTransaction().commit();

		return true;
	}

	public boolean actualizarCodeudor(String idCliente, String idCodeudor, String nom, String telFijo, String dir,
			String trabajo, String telCelular, String telTrabajo, String barrio) {
		// TODO Auto-generated method stub

		session.beginTransaction();

		// ArrayList<Codeudor> coders = (ArrayList<Codeudor>)
		// session.createCriteria(Codeudor.class).list();
		Cliente cl = (Cliente) session.get(Cliente.class, idCliente);

		if (!cl.getId_codeudor().isEmpty()) {
			for (Codeudor miCodeudor : cl.getId_codeudor()) {

				if (miCodeudor.getId_codeudor().equals(idCodeudor)) {
					miCodeudor.setNombre(nom);
					miCodeudor.setTelefono_fijo(telFijo);
					miCodeudor.setDireccion(dir);
					miCodeudor.setTrabajo(trabajo);
					miCodeudor.setTelefono_celular(telCelular);
					miCodeudor.setTelefono_trabajo(telTrabajo);
					miCodeudor.setBarrio(barrio);
					session.update(miCodeudor);
					break;
				}
			}
		} else {
			Codeudor unCodeudor = new Codeudor(idCodeudor, nom, "", dir, telCelular, telFijo, barrio, trabajo,
					telTrabajo, cl);
			cl.addCodeudor(unCodeudor);
			session.save(unCodeudor);
		}

		session.getTransaction().commit();

		return true;
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
	 * manager.exit();
	 * 
	 * }
	 */

}
