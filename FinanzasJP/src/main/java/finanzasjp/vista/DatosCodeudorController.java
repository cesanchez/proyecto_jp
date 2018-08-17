package finanzasjp.vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class DatosCodeudorController {

	private Main main;
	@FXML
	private Label lbNombreCliente;

	@FXML
	private TextField nombre;
	@FXML
	private TextField apellido;
	@FXML
	private TextField cedula;
	@FXML
	private TextField telFijo;
	@FXML
	private TextField trabajo;
	@FXML
	private TextField telCelular;
	@FXML
	private TextField direccion;
	@FXML
	private TextField barrio;
	@FXML
	private TextField telTrabajo;
	@FXML
	private Button btnGuardar;

	private String idCliente; 

	public DatosCodeudorController() {

	}

	public void initialize() {
		

	}

	public void setLbNombreCliente(String nombreCliente, String idCliente) {
		this.idCliente = idCliente; 
		lbNombreCliente.setText(nombreCliente);
	}

	public void guardarCodeudor() {
		boolean res = main.guardarCodeudor(idCliente, nombre.getText(), apellido.getText(), cedula.getText(), telFijo.getText(),
				trabajo.getText(), telCelular.getText(), direccion.getText(), barrio.getText(), telTrabajo.getText());
		
		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Codeudor guardado satisfactoriamente");
			alert.showAndWait();
			
			nombre.setDisable(true);
			apellido.setDisable(true);
			cedula.setDisable(true);
			telFijo.setDisable(true);			
			trabajo.setDisable(true);
			telCelular.setDisable(true);
			direccion.setDisable(true);
			barrio.setDisable(true);
			telTrabajo.setDisable(true);
			btnGuardar.setDisable(true);
				
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Se presentó un error al guardar al codeudor");
			alert.showAndWait();
		}
	}
}
