package finanzasjp.vista;

import java.io.IOException;
import java.text.ParseException;

import finanzasjp.modelo.*;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
	
	public static void generarListadoCobro(int dia, String fecha) throws ParseException {
		ArrayList<Cuota> cuotas = manager.generarListadoCobro(dia, fecha);
		manager.genListadoCsvCobro(cuotas);
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		manager = new Manager();
		manager.setup();
		manager.cargarInformacion();
		launch(args);
		
		manager.exit();
	}

}
