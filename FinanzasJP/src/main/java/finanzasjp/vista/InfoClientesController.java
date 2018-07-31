package finanzasjp.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

import antlr.Token;
import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Dia;
import finanzasjp.modelo.Recibo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class InfoClientesController {

	private Main main;

	@FXML
	private ListView listaClientes;
	
	@FXML
	private ListView listaCuotas;

	@FXML
	private GridPane grid;

	@FXML
	private TextField txNombre;
	@FXML
	private TextField txCedula;
	@FXML
	private TextField txTelefono;
	@FXML
	private TextField txDireccion;

	@FXML
	private TextField txRecibo;

	@FXML
	private TextField txPrestamo;

	@FXML
	private TextField txFechaPres;

	@FXML
	private TextField txFechaFin;

	@FXML
	private TextField txInteres;

	@FXML
	private TextField txPagoTotal;

	@FXML
	private TextField txActivo;

	@FXML
	private TextField txMora;

	@FXML
	private TextField txDias;

	private ObservableList<String> listViewData = FXCollections.observableArrayList();

	public InfoClientesController() {

		listViewData.addAll(darNomClientes());
	}

	public void initialize() {

		// Init ListView and listen for selection changes
		listaClientes.setItems(listViewData);
		listaClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				String[] data = arg2.toString().split(":");
				String nombre = data[1];
				txNombre.setText(nombre);

				String ced = data[0].trim();
				Cliente cl = darCliente(ced);

				txCedula.setText(cl.getId());
				txTelefono.setText(cl.getTelefono());
				txDireccion.setText(cl.getDireccion());

				Recibo rec = main.darReciboCliente(cl);

				txRecibo.setText("" + rec.getId_recibo());
				txActivo.setText(rec.isActivo() ? "Sí" : "No");
				txFechaFin.setText("" + rec.getFecha_fin());
				txFechaPres.setText("" + rec.getFecha_prestamo());
				txMora.setText(rec.isMora() ? "Sí" : "No");
				txPrestamo.setText("" + rec.getMonto_prestamo());
				txInteres.setText("" + rec.getInteres());
				txPagoTotal.setText("" + rec.getPago_total());

				ArrayList<Dia> dias = main.darDias(rec);

				String sdias = "";
				for (Dia d : dias) {
					sdias += d.getDia() + ",";
				}
				sdias = sdias.substring(0, sdias.length() - 1);

				txDias.setText(sdias);

			}
		});
	}
	
	public ArrayList<String> darNomClientes() {
		ArrayList<String> nombresCl = new ArrayList<String>();
		for (Cliente cl : main.darClientes()) {

			nombresCl.add(cl.getId() + ": " + cl.getNombre() + " " + cl.getApellido());
		}

		return nombresCl;
	}

	public Cliente darCliente(String id) {

		Cliente elCl = null;
		for (Cliente cl : main.darClientes()) {

			if (cl.getId().equals(id)) {
				elCl = cl;
			}
		}

		return elCl;
	}
	
	@FXML
	private void verGenListado() {

		try {
			main.verGenListados();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
