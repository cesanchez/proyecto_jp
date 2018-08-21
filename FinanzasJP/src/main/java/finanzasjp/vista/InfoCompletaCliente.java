package finanzasjp.vista;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Codeudor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class InfoCompletaCliente {

	private Main main;

	@FXML
	private TextField txNombre;
	@FXML
	private TextField txCedula;
	@FXML
	private TextField txTelefono_fijo;
	@FXML
	private TextField txDireccion;
	@FXML
	private TextField txTrabajo;
	@FXML
	private TextField txTel_celular;
	@FXML
	private TextField txTel_trabajo;
	@FXML
	private TextField txBarrio;

	@FXML
	private TextField txCodNombre;
	@FXML
	private TextField txCodCedula;
	@FXML
	private TextField txCodTelefono_fijo;
	@FXML
	private TextField txCodDireccion;
	@FXML
	private TextField txCodTrabajo;
	@FXML
	private TextField txCodTel_celular;
	@FXML
	private TextField txCodTel_trabajo;
	@FXML
	private TextField txCodBarrio;

	@FXML
	private Button btnGuardarCliente;
	@FXML
	private Button btnGuardarCodeudor;

	public InfoCompletaCliente() {

	}

	public void initialize() {

	}
	
	public void guardarInfoCodeudor() {
		boolean res = main.actualizarCodeudor(txCedula.getText(), txNombre.getText(),
				txTelefono_fijo.getText(),
				txDireccion.getText(),
				txTrabajo.getText(),
				txTel_celular.getText(),
				txTel_trabajo.getText(),
				txBarrio.getText());
		
		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Codeudor actualizado satisfactoriamente");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Se generó un error al modificar los datos del codeudor");
			alert.showAndWait();
		}
	}

	public void guardarInfCliente() {
		boolean res = main.actualizarCliente(txCedula.getText(), txNombre.getText(),
				txTelefono_fijo.getText(),
				txDireccion.getText(),
				txTrabajo.getText(),
				txTel_celular.getText(),
				txTel_trabajo.getText(),
				txBarrio.getText());
		
		if (res) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Cliente actualizado satisfactoriamente");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Se generó un error al modificar los datos del cliente");
			alert.showAndWait();
		}

	}
	

	public void modificarInfCliente() {
		txNombre.setDisable(false);

		txCedula.setDisable(false);

		txTelefono_fijo.setDisable(false);

		txDireccion.setDisable(false);

		txTrabajo.setDisable(false);

		txTel_celular.setDisable(false);

		txTel_trabajo.setDisable(false);

		txBarrio.setDisable(false);
		
		btnGuardarCliente.setDisable(false);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText("Modifica los datos que necesites y luego has clic en el botón Guardar");
		alert.showAndWait();
		
	}

	public void modificarInfCodeudor() {
		txCodNombre.setDisable(false);

		txCodCedula.setDisable(false);

		txCodTelefono_fijo.setDisable(false);

		txCodDireccion.setDisable(false);

		txCodTrabajo.setDisable(false);

		txCodTel_celular.setDisable(false);

		txCodTel_trabajo.setDisable(false);

		txCodBarrio.setDisable(false);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText("Modifica los datos que necesites y luego has clic en el botón Guardar");
		alert.showAndWait();

		btnGuardarCodeudor.setDisable(false);		
		
	}

	public void setInfoCliente(Cliente cl) {
		txNombre.setText(cl.getNombre());

		txCedula.setText(cl.getId());

		txTelefono_fijo.setText(cl.getTelefono_fijo());

		txDireccion.setText(cl.getDireccion());

		txTrabajo.setText(cl.getTrabajo());

		txTel_celular.setText(cl.getTelefono_celular());

		txTel_trabajo.setText(cl.getTelefono_trabajo());

		txBarrio.setText(cl.getBarrio());

		if (!cl.getId_codeudor().isEmpty()) {
			for (Codeudor cod : cl.getId_codeudor()) {
				if (cod != null) {

					txCodNombre.setText(cod.getNombre());

					txCodCedula.setText(cod.getId_codeudor());

					txCodTelefono_fijo.setText(cod.getTelefono_fijo());

					txCodDireccion.setText(cod.getDireccion());

					txCodTrabajo.setText(cod.getTrabajo());

					txCodTel_celular.setText(cod.getTelefono_celular());

					txCodTel_trabajo.setText(cod.getTelefono_trabajo());

					txCodBarrio.setText(cod.getBarrio());

				}
			}
		}
	}
}
