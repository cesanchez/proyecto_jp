package finanzasjp.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

import finanzasjp.modelo.*;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends Application {
	
	private static Stage primaryStage;
	private static Manager manager;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		this.primaryStage = primaryStage;
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Index.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Créditos JP");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Cliente> darClientes(){
		
		return manager.darClientes();
	}
	
	public static Recibo darReciboCliente(Cliente cliente){
		
		return manager.darReciboCliente(cliente);
	}
	
	public static ArrayList<Dia_Recibo> darDias(Recibo recibo) {
		
		return manager.darDiasRecibo(recibo);		
	}
	
	public static void generarArchivoListaCobro(int dia, String fecha, String idCobrador) throws ParseException, IOException {
		ArrayList<Cuota> cuotas = manager.generarListadoCobro(dia, fecha, idCobrador);
		manager.genListadoCsvCobro(cuotas);
	}
	
	public static ArrayList<Cliente> darListadoCobro(int dia, String fecha, String idCobrador) throws ParseException {
		ArrayList<Cliente> clientes = manager.darListaClientesCobro(dia, fecha, idCobrador);
		return clientes;		
	}
	
	public static ArrayList<Cliente> darListaClientesMora(){
		return manager.darListaClientesMora();
	}
	
	public static void desactivarRecibo(String id_recibo) {
		
		int id = Integer.parseInt(id_recibo);
		manager.desactivarRecibo(id);
	}
	
	public static int darNuevaCuotaRecibo(String id_recibo) {
		return manager.darNumNuevaCuotaRecibo(Integer.parseInt(id_recibo));
	}
	
	public static void verInfoClientes() throws IOException {
		
		//manager.cargarCuotas();
		manager.cargarPagos();
		
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(Main.class.getResource("InfoClientes.fxml"));
		BorderPane clienteVip = loader.load();
		
		Stage clientVipDiaStage = new Stage();
		clientVipDiaStage.setTitle("Créditos JP");
		clientVipDiaStage.initModality(Modality.WINDOW_MODAL);
		clientVipDiaStage.initOwner(primaryStage);
		
		Scene scene = new Scene(clienteVip);
		clientVipDiaStage.setScene(scene);		
		clientVipDiaStage.showAndWait();		
	}
	
	public static void verInfoClientesVip() throws IOException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(Main.class.getResource("InfoClientesVip.fxml"));
		BorderPane clienteVip = loader.load();
		
		Stage clientVipDiaStage = new Stage();
		clientVipDiaStage.setTitle("Créditos JP");
		clientVipDiaStage.initModality(Modality.WINDOW_MODAL);
		clientVipDiaStage.initOwner(primaryStage);
		
		Scene scene = new Scene(clienteVip);
		clientVipDiaStage.setScene(scene);		
		clientVipDiaStage.showAndWait();		
	}
	
	public static void verInfoCuotas(String nombre, String idRecibo, String prestamo, String inte, String pagototal, String idCliente) throws IOException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(Main.class.getResource("CuotasCliente.fxml"));
		BorderPane clienteVip = loader.load();
		
		CuotasClienteController cuotasCli = loader.getController();
		String submiIn = inte.substring(0, 2);
		String interes = submiIn + "%";
		cuotasCli.setCampos(nombre, idRecibo, prestamo, interes, pagototal, idCliente);
		cuotasCli.inicializarCuotas(idCliente);
		
		Stage clientVipDiaStage = new Stage();
		clientVipDiaStage.setTitle("Créditos JP");
		clientVipDiaStage.initModality(Modality.WINDOW_MODAL);
		clientVipDiaStage.initOwner(primaryStage);
		
		Scene scene = new Scene(clienteVip);
		clientVipDiaStage.setScene(scene);		
		clientVipDiaStage.showAndWait();		
	}
	
	public static void verGenListados() throws IOException{
		FXMLLoader loader =  new FXMLLoader();
		//loader.setLocation(Main.class.getResource("GenListados.fxml"));
		loader.setLocation(Main.class.getResource("ListadoCuota.fxml"));
		BorderPane listado = loader.load();
		
		Stage listadoStage = new Stage();
		listadoStage.setTitle("Créditos JP");
		listadoStage.initModality(Modality.WINDOW_MODAL);
		listadoStage.initOwner(primaryStage);
		
		Scene scene = new Scene(listado);
		listadoStage.setScene(scene);		
		listadoStage.showAndWait();
	}
	
	public Cliente darCliente(String id) {

		Cliente elCl = null;
		for (Cliente cl : darClientes()) {
			if (cl.getId().equals(id)) {
				elCl = cl;
			}
		}
		return elCl;
	}
	
	public static ArrayList<Cuota> darCuotasCliente(String cliente){
		return manager.darCuotasCliente(cliente);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		manager = new Manager();
		manager.setup();
		manager.cargarInformacion();
		launch(args);
		
		manager.exit();
	}

	public static void guardarPago(double valor, double newValorCuota, int id_cuota, int id_recibo, String fecha) {
		// TODO Auto-generated method stub
		manager.guardarPago(valor, newValorCuota, id_cuota, id_recibo, fecha);
	}

	public static ArrayList<Cliente_VIP> darClientesVip() {
		// TODO Auto-generated method stub
		return manager.darClientesVip();
	}

	public static boolean guardarCliente(String cedCliVip, String ced, String nombre, String apellido, String tel,
			String dir, String telFijo, String barrio, String trabajo, String telTrabajo, String idCobrador) {
		
		return manager.guardarCliente(cedCliVip, ced, nombre, apellido, tel, dir, telFijo, barrio, trabajo, telTrabajo, idCobrador);
		
	}

	public static boolean guardarRecibo(int id_rec, String ced, double prestamo, double interes, String fechaI, 
			String fechaF, double pagoTotal, String[] losDias) throws ParseException {
		// TODO Auto-generated method stub
		ArrayList dias = new ArrayList();
		if(losDias != null) {
			for(int i = 0; i< losDias.length; i++) {
				dias.add(Integer.parseInt(losDias[i].trim()));
			}
		}
		
		return manager.guardarRecibo(id_rec, ced, prestamo, interes, fechaI, fechaF, pagoTotal, dias);
	}

	public static int darNumUltRecibo() {
		// TODO Auto-generated method stub
		return manager.darNumUltimoRecibo() + 1;
	}

	public static ArrayList<Cliente> darClientes(String id_clVip) {
		// TODO Auto-generated method stub
		return manager.darClientes(id_clVip);
	}

	public static boolean guardarClienteVip(String id, String nombre, String apellido, String telefono, double capital) {
		// TODO Auto-generated method stub
		boolean ret = false;
		
		ret = manager.guardarClienteVip(id, nombre, apellido, telefono, capital);
		
		return ret;
		
	}

	public static boolean generarCuotas(String prestamo, String interes, String modoPago, String cuotas, String idRecibo) {
		// TODO Auto-generated method stub
		int modo = 0;
		if(modoPago.equalsIgnoreCase("Mensual")) {
			modo = 1;
		}else if(modoPago.equalsIgnoreCase("Quincenal")) {
			modo = 2;
		}else if(modoPago.equalsIgnoreCase("Semanal")) {
			modo = 3;
		}else {
			modo = 4;
		}
		
		return manager.generarCuotas(Double.parseDouble(prestamo), Double.parseDouble(interes), modo, Integer.parseInt(cuotas), Integer.parseInt(idRecibo));
		
	}

	public static boolean guardarFechaCuota(String fecha, String idRecibo, String idCuota, String valorPagar) {
		// TODO Auto-generated method stub
		boolean res = false;
		try {
			res = manager.guardarFechaCuota(fecha, Integer.parseInt(idRecibo), Integer.parseInt(idCuota), Double.parseDouble(valorPagar));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return res;
		}		
	}

	public static boolean actualizarEstadoCuotas(String fechaActual) {
		// TODO Auto-generated method stub
		boolean ret = false;
		try {
			ret = manager.actualizarEstadoCuota(fechaActual);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static double calcularPagoTotal(double prestamo, double interes, String modoPago, int cuotas) {
		// TODO Auto-generated method stub
		int modo = 0;
		if(modoPago.equalsIgnoreCase("Mensual")) {
			modo = 1;
		}else if(modoPago.equalsIgnoreCase("Quincenal")) {
			modo = 2;
		}else if(modoPago.equalsIgnoreCase("Semanal")) {
			modo = 3;
		}else {
			modo = 4;
		}
		return manager.calcularPagoTotal(prestamo, interes, modo, cuotas);
	}
	
	public static double calcularPagoTotalLabel(double prestamo, double interes, String modoPago, int cuotas) {
		// TODO Auto-generated method stub
		int modo = 0;
		if(modoPago.equalsIgnoreCase("Mensual")) {
			modo = 1;
		}else if(modoPago.equalsIgnoreCase("Quincenal")) {
			modo = 2;
		}else if(modoPago.equalsIgnoreCase("Semanal")) {
			modo = 3;
		}else {
			modo = 4;
		}
		return manager.calcularPagoTotalLabel(prestamo, interes, modo, cuotas);
	}
	
	public static String validarReciboActivoCliente(String idCliente) {
		return manager.validarReciboActivoCliente(idCliente);
	}

	public static ArrayList<Cuota> darCuotasMoraCliente(String idCliente) {
		// TODO Auto-generated method stub
		return manager.darCuotasMoraCliente(idCliente);
	}

	public static void verListaCuotasMora() throws IOException{
		// TODO Auto-generated method stub
		FXMLLoader loader =  new FXMLLoader();
		//loader.setLocation(Main.class.getResource("GenListados.fxml"));
		loader.setLocation(Main.class.getResource("ListadoCuotaMora.fxml"));
		BorderPane listado = loader.load();
		
		Stage listadoStage = new Stage();
		listadoStage.setTitle("Créditos JP");
		listadoStage.initModality(Modality.WINDOW_MODAL);
		listadoStage.initOwner(primaryStage);
		
		Scene scene = new Scene(listado);
		listadoStage.setScene(scene);		
		listadoStage.showAndWait();
	}
	
	public static  ArrayList<Mora> darInfMoraCliente(String id) {
		return manager.darInfMoraCliente(id);
	}
	
	public static void generarListadoCsvMora() throws IOException {
		manager.generarListadoCsvMora();
		
	}

	public static void verDatosCodeudor(String nombreCliente, String idCliente) throws IOException {
		// TODO Auto-generated method stub
		
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(Main.class.getResource("DatosCodeudor.fxml"));
		BorderPane clienteVip = loader.load();
		
		DatosCodeudorController datos = loader.getController();
		datos.setLbNombreCliente(nombreCliente, idCliente);
		
		Stage clientVipDiaStage = new Stage();
		clientVipDiaStage.setTitle("Créditos JP");
		clientVipDiaStage.initModality(Modality.WINDOW_MODAL);
		clientVipDiaStage.initOwner(primaryStage);
		
		Scene scene = new Scene(clienteVip);
		clientVipDiaStage.setScene(scene);		
		clientVipDiaStage.showAndWait();
	}

	public static boolean guardarCodeudor(String idCliente, String nombre, String apellido, String cedula, String telFijo, String trabajo, String telCelular,
			String direccion, String barrio, String telTrabajo, boolean activo) {
		// TODO Auto-generated method stub
		return manager.guardarCodeudor(idCliente, nombre, apellido, cedula, telFijo, trabajo, telCelular,
				direccion, barrio, telTrabajo, activo);
	}

	public static void verDatosCompletos(Cliente cl) throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(Main.class.getResource("InfoCompletaCliente.fxml"));
		BorderPane clienteVip = loader.load();
		
		InfoCompletaCliente datos = loader.getController();
		datos.setInfoCliente(cl);
		
		Stage clientVipDiaStage = new Stage();
		clientVipDiaStage.setTitle("Créditos JP");
		clientVipDiaStage.initModality(Modality.WINDOW_MODAL);
		clientVipDiaStage.initOwner(primaryStage);
		
		Scene scene = new Scene(clienteVip);
		clientVipDiaStage.setScene(scene);		
		clientVipDiaStage.showAndWait();
	}

	public static boolean actualizarCliente(String idCliente, String nom, String telFijo, String dir, String trabajo, String telCelular, String telTrabajo,
			String barrio) {
		// TODO Auto-generated method stub
		return manager.actualizarCliente(idCliente, nom, telFijo, dir, trabajo, telCelular, telTrabajo, barrio);
	}

	public static boolean actualizarCodeudor(String idCliente, String idCodeudor, String nom, String telFijo, String dir, String trabajo, String telCelular, String telTrabajo,
			String barrio) {
		// TODO Auto-generated method stub
		return manager.actualizarCodeudor(idCliente, idCodeudor, nom, telFijo, dir, trabajo, telCelular, telTrabajo, barrio);
	}

	public static ArrayList<Cobrador> darNomCobradores() {
		// TODO Auto-generated method stub
		return manager.darCobradores();
	}

	


}
