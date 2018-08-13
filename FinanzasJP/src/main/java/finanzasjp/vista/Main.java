package finanzasjp.vista;

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
			Scene scene = new Scene(root,295,175);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
	
	public static ArrayList<Dia> darDias(Recibo recibo) {
		
		return manager.darDiasRecibo(recibo);		
	}
	
	public static void generarArchivoListaCobro(int dia, String fecha) throws ParseException, IOException {
		ArrayList<Cuota> cuotas = manager.generarListadoCobro(dia, fecha);
		manager.genListadoCsvCobro(cuotas);
	}
	
	public static ArrayList<Cliente> darListadoCobro(int dia, String fecha) throws ParseException {
		ArrayList<Cliente> clientes = manager.darListaClientesCobro(dia, fecha);
		return clientes;		
	}
	
	public static void desactivarRecibo(String id_recibo) {
		
		int id = Integer.parseInt(id_recibo);
		manager.desactivarRecibo(id);
	}
	
	public static void verInfoClientes() throws IOException {
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
	
	public static void verInfoCuotas(String nombre, String idRecibo, String prestamo, String interes, String pagototal, String idCliente) throws IOException {
		FXMLLoader loader =  new FXMLLoader();
		loader.setLocation(Main.class.getResource("CuotasCliente.fxml"));
		BorderPane clienteVip = loader.load();
		
		CuotasClienteController cuotasCli = loader.getController();
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

	public static void guardarPago(double valor, int id_cuota, int id_recibo) {
		// TODO Auto-generated method stub
		manager.guardarPago(valor, id_cuota, id_recibo);
	}

	public static ArrayList<Cliente_VIP> darClientesVip() {
		// TODO Auto-generated method stub
		return manager.darClientesVip();
	}

	public static boolean guardarCliente(String cedCliVip, String ced, String nombre, String apellido, String tel,
			String dir) {
		
		return manager.guardarCliente(cedCliVip, ced, nombre, apellido, tel, dir);
		
	}

	public static boolean guardarRecibo(int id_rec, String ced, double prestamo, double interes, String fechaI, 
			String fechaF, double pagoTotal, String[] losDias) throws ParseException {
		// TODO Auto-generated method stub
		ArrayList dias = new ArrayList();
		if(losDias.length>=1) {
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

	public static boolean guardarClienteVip(String id, String nombre, String telefono, double capital) {
		// TODO Auto-generated method stub
		boolean ret = false;
		
		ret = manager.guardarClienteVip(id, nombre, telefono, capital);
		
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

	public static void guardarFechaCuota(String fecha, String idRecibo, String idCuota) {
		// TODO Auto-generated method stub
		try {
			manager.guardarFechaCuota(fecha, Integer.parseInt(idRecibo), Integer.parseInt(idCuota));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void actualizarEstadoCuotas(String fechaActual) {
		// TODO Auto-generated method stub
		try {
			manager.actualizarEstadoCuota(fechaActual);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public static String validarReciboActivoCliente(String idCliente) {
		return manager.validarReciboActivoCliente(idCliente);
	}


}
