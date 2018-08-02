package finanzasjp.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import com.sun.javafx.css.converters.StringConverter;

import antlr.Token;
import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cliente_VIP;
import finanzasjp.modelo.Dia;
import finanzasjp.modelo.Recibo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class InfoClientesController {

	private Main main;
	
	private String cedulaClienteVip = "";

	@FXML
	private ListView listaClientes;
	
	@FXML
	private ListView listaCuotas;

	@FXML
	private GridPane gridRecibo_admin;

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
	
	@FXML
	private TextField txSaldo;
	
	@FXML
	private ComboBox pertenece_admin;
	
	@FXML
	private TextField txDias_admin;

	final Tooltip tooltip = new Tooltip();
	
	private ObservableList<Cliente> listViewData = FXCollections.observableArrayList();
	private ObservableList<Cliente_VIP> dataPert_admin = FXCollections.observableArrayList();
	
	//Admin nuevo cliente
	
	@FXML
	private TextField txNombreAdmin;
	@FXML
	private TextField txApellidoAdmin;
	@FXML
	private TextField txCedulaAdmin;
	@FXML
	private TextField txTelefonoAdmin;
	@FXML
	private TextField txDireccionAdmin;
	
	//Admin nuevo recibo
	
	@FXML
	private TextField txRecibo_admin;

	@FXML
	private TextField txPrestamo_admin;

	@FXML
	private DatePicker txFechaPres_admin;

	@FXML
	private DatePicker txFechaFin_admin;

	@FXML
	private TextField txInteres_admin;
	
	@FXML
	private TextField txCuotas_admin;
	
	@FXML
	private Label lbPagoTotal_admin;
	
	@FXML
	private Label lbRecibo;

	public InfoClientesController() {

		listViewData.addAll(darNomClientes());
		dataPert_admin.addAll(darNomClientesVip());
	}

	public void initialize() {
		
		tooltip.setText("Agregue los días separados por coma (ej: 5, 20)");
		txDias_admin.setTooltip(tooltip);

		pertenece_admin.setItems(dataPert_admin);		
		pertenece_admin.setConverter(new javafx.util.StringConverter<Cliente_VIP>() {
			@Override
			public Cliente_VIP fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public String toString(Cliente_VIP object) {
				// TODO Auto-generated method stub
				return object.getNombre();
			}
		});
		
		pertenece_admin.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub				
				Cliente_VIP clvip = (Cliente_VIP) newValue;
				cedulaClienteVip = clvip.getId();				
			}
		});
		
		// Init ListView and listen for selection changes
		listaClientes.setItems(listViewData);
		
		listaClientes.setCellFactory(new Callback<ListView<Cliente>, ListCell<Cliente>>() {

			public ListCell<Cliente> call(ListView<Cliente> param) {
				// TODO Auto-generated method stub
				ListCell<Cliente> cell = new ListCell<Cliente>(){ 
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
				
		listaClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
				Cliente cl = (Cliente) arg2;
				
				//String[] data = arg2.toString().split(":");
				//String nombre = data[1];
				//txNombre.setText(nombre);
				txNombre.setText(cl.getNombre() + " " + cl.getApellido());

				//String ced = data[0].trim();
				String ced = cl.getId();
				//Cliente cl = darCliente(ced);

				txCedula.setText(cl.getId());
				txTelefono.setText(cl.getTelefono());
				txDireccion.setText(cl.getDireccion());

				Recibo rec = main.darReciboCliente(cl);
				
				if(rec != null) {
					
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
					
				}else {
					
					txRecibo.setText("");
					txActivo.setText("");
					txFechaFin.setText("");
					txFechaPres.setText("");
					txMora.setText("");
					txPrestamo.setText("");
					txInteres.setText("");
					txPagoTotal.setText("");
					txDias.setText("");
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Información");
					alert.setHeaderText(null);
					alert.setContentText("El cliente seleccionado no tiene recibo activo");
					alert.showAndWait();
				}

			}
		});
	}
	
	public ArrayList<Cliente> darNomClientes() {
		return main.darClientes();
	}
	
	/*public ArrayList<String> darNomClientes() {
		ArrayList<String> nombresCl = new ArrayList<String>();
		for (Cliente cl : main.darClientes()) {

			nombresCl.add(cl.getId() + ": " + cl.getNombre() + " " + cl.getApellido());
		}

		return nombresCl;
	}*/
	
	public ArrayList<Cliente_VIP> darNomClientesVip() {
		return main.darClientesVip();
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
	
	@FXML
	private void finalizarPrestamo() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Mensaje de confirmación");
		alert.setHeaderText("Terminación del prestamo al cliente: " + txNombre.getText());
		alert.setContentText("¿Está seguro que desea terminar el prestamo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    main.desactivarRecibo(txRecibo.getText());
		    
		    Alert conf = new Alert(AlertType.CONFIRMATION);
		    conf.setTitle("Información");
		    conf.setHeaderText(null);
		    conf.setContentText("El prestamo ha terminado");
		    conf.showAndWait();
		    
		    txActivo.setText("No");
		    
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
	
	@FXML
	private void guardarCliente() {
		String cedCl = "";
		cedCl = txCedulaAdmin.getText();
		if(cedulaClienteVip.equals("") || cedCl.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("1. Debes indicar a quien pertenece \n2. Debes escribir la cédula del nuevo cliente");
			alert.showAndWait();
		}else {	
			
			Alert alertConf = new Alert(AlertType.CONFIRMATION);
			alertConf.setTitle("Confirmación");
			alertConf.setHeaderText("¿Confirma que desea almacenar los siguientes datos?");
			alertConf.setContentText(" Nombre: " + txNombreAdmin.getText() + "\n Apellido: " + txApellidoAdmin.getText()+"\n Cédula: " + txCedulaAdmin.getText()+"\n Teléfono: " + txTelefonoAdmin.getText()+"\n Dirección: " + txDireccionAdmin.getText());

			Optional<ButtonType> result = alertConf.showAndWait();			
			boolean resp = false;
			boolean flagOk = false; //flag usado para confirmar que se hizo clic en ok y asi mostrar el respectivo mensaje
			
			if (result.get() == ButtonType.OK){
				resp = main.guardarCliente(cedulaClienteVip, txCedulaAdmin.getText(), txNombreAdmin.getText(), 
						txApellidoAdmin.getText(),txTelefonoAdmin.getText(), txDireccionAdmin.getText());
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
				
				//Se habilita el grid del recibo
				gridRecibo_admin.setDisable(false);
				lbRecibo.setText(Integer.toString(main.darNumUltRecibo()));
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Ya existe un cliente con cédula: " + cedCl);
				alert.showAndWait();
			}
		}		
	}
	
	@FXML
	private void guardarRecibo() {
		//int cuotas = Integer.parseInt(txCuotas_admin.getText());
		//String ds = txDias_admin.getText();
		
		try {
			
			Alert alertConf = new Alert(AlertType.CONFIRMATION);
			alertConf.setTitle("Confirmación");
			alertConf.setHeaderText(null);
			alertConf.setContentText("¿Confirma que desea almacenar el recibo?");
			Optional<ButtonType> result = alertConf.showAndWait();			
			boolean resp = false;
			boolean flagOk = false; //flag usado para confirmar que se hizo clic en ok y asi mostrar el respectivo mensaje
			
			if (result.get() == ButtonType.OK){
				String[] losDias = txDias_admin.getText().split(",");
				
				resp = main.guardarRecibo(Integer.parseInt(lbRecibo.getText()), txCedulaAdmin.getText(), Double.parseDouble(txPrestamo_admin.getText()), Double.parseDouble(txInteres_admin.getText()), txFechaPres_admin.getValue().toString(), txFechaFin_admin.getValue().toString(),
						Double.parseDouble(lbPagoTotal_admin.getText()), losDias);
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
				alert.setContentText("El recibo No. " + lbRecibo.getText()+ " ya existe");
				alert.showAndWait();
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
