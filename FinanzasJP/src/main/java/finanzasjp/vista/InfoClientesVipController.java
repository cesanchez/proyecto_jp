package finanzasjp.vista;

import java.util.ArrayList;
import java.util.Optional;

import antlr.debug.NewLineListener;
import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cliente_VIP;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	@FXML
	private TextField txNombreAdmin;
	@FXML
	private TextField txApellidoAdmin;
	@FXML
	private TextField txCedulaAdmin;
	@FXML
	private TextField txTelefonoAdmin;
	@FXML
	private TextField txCapitalAdmin;

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
	
	@FXML
	private void guardarClienteVip() {
		

		String cedCl = "";
		cedCl = txCedulaAdmin.getText();
		if(cedCl.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("Debes escribir la cédula del nuevo cliente vip");
			alert.showAndWait();
		}else {	
			
			Alert alertConf = new Alert(AlertType.CONFIRMATION);
			alertConf.setTitle("Confirmación");
			alertConf.setHeaderText("¿Confirma que desea almacenar los siguientes datos?");
			alertConf.setContentText(" Nombre: " + txNombreAdmin.getText() + "\n Cédula: " + txCedulaAdmin.getText()+"\n Teléfono: " + txTelefonoAdmin.getText()+"\n Capital: " + txCapitalAdmin.getText());

			Optional<ButtonType> result = alertConf.showAndWait();			
			boolean resp = false;
			boolean flagOk = false; //flag usado para confirmar que se hizo clic en ok y asi mostrar el respectivo mensaje
			
			if (result.get() == ButtonType.OK){
				resp = main.guardarClienteVip(txCedulaAdmin.getText(), txNombreAdmin.getText(), txApellidoAdmin.getText(), txTelefonoAdmin.getText(), Double.parseDouble(txCapitalAdmin.getText()));
				flagOk = true;
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
					
			if(resp && flagOk) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmación");
				alert.setHeaderText(null);
				alert.setContentText("Datos guardados exitosamente");
				alert.showAndWait();
				
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Ya existe un cliente con cédula: " + cedCl);
				alert.showAndWait();
			}
		}		
	
	}
	

}
