package finanzasjp.vista;

import java.util.ArrayList;

import antlr.debug.NewLineListener;
import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cliente_VIP;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class InfoClientesVipController {

	private Main main;

	private String cedulaClienteVip = "";

	@FXML
	private ListView listaClientesVip;
	@FXML
	private ListView listaClientes;
	@FXML
	private TextField txNombre;
	@FXML
	private TextField txCedula;
	@FXML
	private TextField txTelefono;
	@FXML
	private TextField txCapital;

	private ObservableList<Cliente_VIP> dataClienteVip = FXCollections.observableArrayList();
	private ObservableList<Cliente> dataCliente = FXCollections.observableArrayList();

	public InfoClientesVipController() {

	}

	public void initialize() {

		dataClienteVip.addAll(darNomClientesVip());

		listaClientesVip.setItems(dataClienteVip);
		listaClientesVip.setCellFactory(new Callback<ListView<Cliente_VIP>, ListCell<Cliente_VIP>>() {

			public ListCell<Cliente_VIP> call(ListView<Cliente_VIP> param) {
				// TODO Auto-generated method stub
				ListCell<Cliente_VIP> cell = new ListCell<Cliente_VIP>() {
					@Override
					protected void updateItem(Cliente_VIP t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getNombre() + " " + t.getApellido());
						}
					}
				};
				return cell;
			}
		});
		
		listaClientesVip.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
				dataCliente.clear();
				
				Cliente_VIP cl = (Cliente_VIP) arg2;	
				cedulaClienteVip = cl.getId();
				txNombre.setText(cl.getNombre() + " " + cl.getApellido());
				txCedula.setText(cl.getId());
				txTelefono.setText(cl.getTelefono());
				txCapital.setText(Double.toString(cl.getMonto_inicial()));
				
				dataCliente.addAll(darClientes(cedulaClienteVip));
				listaClientes.setItems(dataCliente);
				listaClientes.setCellFactory(new Callback<ListView<Cliente>, ListCell<Cliente>>() {

					public ListCell<Cliente> call(ListView<Cliente> param) {
						// TODO Auto-generated method stub
						ListCell<Cliente> cell = new ListCell<Cliente>() {
							@Override
							protected void updateItem(Cliente t, boolean bln) {
								super.updateItem(t, bln);
								if (t != null) {
									setText(t.getNombre() + " " + t.getApellido());
								}
							}
						};
						return cell;
					}
				});
				
			}
		});

	}
	
	public ArrayList<Cliente> darClientes(String id_clVip){
		return main.darClientes(id_clVip);
	}

	public ArrayList<Cliente_VIP> darNomClientesVip() {
		return main.darClientesVip();
	}

}
