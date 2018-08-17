package finanzasjp.vista;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Codeudor;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

	public InfoCompletaCliente() {

	}

	public void initialize() {

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
