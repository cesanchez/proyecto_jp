package finanzasjp.vista;

import java.util.ArrayList;

import finanzasjp.modelo.Cuota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CuotasClienteController {
	
	private Main main;

	@FXML
	private Label lb_NomCliente;
	@FXML
	private Label lb_idRecibo;
	@FXML
	private Label lb_idCliente;
	@FXML
	private TextField txPrestamo;
	@FXML
	private TextField txInteres;
	@FXML
	private TextField tx_PagoTotal;	
	@FXML
	private ListView listaCuotas;
	private ObservableList<String> dataCuotas = FXCollections.observableArrayList();
	
	public CuotasClienteController() {
		
	}
	
	public void inicializarCuotas(String idCliente) {
		dataCuotas.addAll(darCuotas(idCliente));
	}
	
	private ArrayList<String> darCuotas(String idCliente) {
		// TODO Auto-generated method stub
		ArrayList<String> strCuotas = new ArrayList<String>();		
		ArrayList<Cuota> cuotas = main.darCuotasCliente(idCliente);
		
		for(Cuota c : cuotas) {
			strCuotas.add("Cuota # " + c.getId_cuota());
		}
		
		return strCuotas;
	}

	public void setCampos(String nombre, String idRecibo, String prestamo, String interes, String pagoTotal, String idCliente) {
		lb_NomCliente.setText(nombre);
		lb_idRecibo.setText(idRecibo);
		txPrestamo.setText(prestamo);
		txInteres.setText(interes);
		tx_PagoTotal.setText(pagoTotal);
		lb_idCliente.setText(idCliente);
	}
	
	public void initialize() {
		
		listaCuotas.setItems(dataCuotas);
		
		
	}

}
