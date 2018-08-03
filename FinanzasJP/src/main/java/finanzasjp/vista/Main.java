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
	
	public static void generarArchivoListaCobro(int dia, String fecha) throws ParseException {
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
	
	public static ArrayList<Cuota> darCuotasCliente(Cliente cliente){
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
		if(losDias.length<1) {
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


}
