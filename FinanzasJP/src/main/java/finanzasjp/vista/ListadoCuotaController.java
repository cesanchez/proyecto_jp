package finanzasjp.vista;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cuota;
import finanzasjp.modelo.Dia_Recibo;
import finanzasjp.modelo.Recibo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ListadoCuotaController {

	private Main main;

	@FXML
	private ListView listaClientes;
	
	@FXML
	private ListView listaCuotas;

	@FXML
	private TextField txDia;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextField txCuota;
	
	@FXML
	private TextField txFechaCobro;
	
	@FXML
	private TextField txValorCuota;	
	@FXML
	private TextField txValorPagado;
		
	@FXML
	private TextField txMora;
	
	@FXML
	private Label lbCliente;
	
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
	private TextField txMoraRecibo;

	@FXML
	private TextField txDias;
	
	@FXML
	private TextField txSaldo;
	
	private ObservableList<Cliente> listDataCliente = FXCollections.observableArrayList();
	private ObservableList<String> listDataCuota= FXCollections.observableArrayList();
	
	private Cliente cl;

	public ListadoCuotaController() {
		
		//listDataCliente.addAll(darNombreClientes());		
	}

	public void initialize() {
		
		//Agregar patron al campo valor pagado
		
		txDia.focusedProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
		        if (newValue != null) { //when focus lost
		            if(!txDia.getText().matches("([0-9]|[12][0-9]|3[01])")){
		                //when it not matches the pattern (1.0 - 6.0)
		                //set the textField empty
		            	txDia.setText("");
		            }
		        }
			}
		});
	
	}
	
	public void generarArchivoListaCobro() {
		
		try {
			
			LocalDate value = datePicker.getValue();
			String strFecha = value!=null ? value.toString():null;
			String strDia = !txDia.getText().equals("") ? txDia.getText():"0";
			
			if(strFecha == null && strDia.equals("0")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Seleccione una fecha o un día de pago");
				alert.showAndWait();
				
			}else {
				main.generarArchivoListaCobro(Integer.parseInt(strDia),strFecha);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generarListadoClientes() {
		LocalDate value = datePicker.getValue();
		String strFecha = value!=null ? value.toString():null;
		String strDia = !txDia.getText().equals("") ? txDia.getText():"0";
		
		try {
			
			if(strFecha == null && strDia.equals("0")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Seleccione una fecha o un día de pago");
				alert.showAndWait();
				
			}else {
				
				ArrayList<Cliente> lClientes = main.darListadoCobro(Integer.parseInt(strDia),strFecha);					
				listDataCliente.addAll(lClientes);
				listaClientes.setItems(listDataCliente);
				
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

					public void changed(ObservableValue observable, Object arg1, Object arg2) {
						// TODO Auto-generated method stub				
						listDataCuota.clear();
						
						//Se asigna el cliente seleccionado desde la lista de clientes
						cl = (Cliente) arg2;
						
						String nombre = cl.getNombre();				
						String ced = cl.getId();
												
						lbCliente.setText(nombre + " " +cl.getApellido());
						Recibo reciboCl = main.darReciboCliente(cl);
						txRecibo.setText(""+reciboCl.getId_recibo());
						txPrestamo.setText(""+reciboCl.getMonto_prestamo());
						txInteres.setText(""+reciboCl.getInteres());
						txFechaPres.setText(""+reciboCl.getFecha_prestamo());
						txSaldo.setText(""+reciboCl.getSaldo());
						boolean moraRec = reciboCl.isMora();							
						txMoraRecibo.setText(!moraRec ? "No":"Sí");
						txFechaFin.setText(""+reciboCl.getFecha_fin());
						txPagoTotal.setText(""+reciboCl.getPago_total());
						
						ArrayList<Dia_Recibo> dias = main.darDias(reciboCl);
						String sdias = "";
						if(!dias.isEmpty()) {
							for (Dia_Recibo d : dias) {
								sdias += d.getId_dia() + ",";
							}
							sdias = sdias.substring(0, sdias.length() - 1);
						}
						
						txDias.setText(sdias);
						
						listDataCuota.addAll(darStrCuotas(cl));
						listaCuotas.setItems(listDataCuota);
						
						listaCuotas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

							public void changed(ObservableValue arg0, Object arg1, Object arg2) {
								// TODO Auto-generated method stub
								
								txCuota.setText("");
								txFechaCobro.setText("");
								txValorCuota.setText("");
								txValorPagado.setText("");
								
								String[] dataCu = arg2.toString().split(":");
								String noCuota = dataCu[0];
								String strFecha = dataCu[1];
								txCuota.setText(noCuota);
								txFechaCobro.setText(strFecha);
								
								Recibo reciboCl = main.darReciboCliente(cl);							
								Cuota cut = reciboCl.darCuotaId(Integer.parseInt(noCuota));
								
								txValorCuota.setText(""+cut.getValor());
								txValorPagado.setText(""+cut.getValor_pagado());
								boolean mora = cut.isMora();							
								txMora.setText(!mora ? "No":"Sí");
							}
						});
					}
				});	
			}		
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
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

	public void guardarPago() {
		
		main.guardarPago(Double.parseDouble(txValorPagado.getText()), Integer.parseInt(txCuota.getText()), Integer.parseInt(txRecibo.getText()));
		System.out.println("Pago re");
	}
	
	public ArrayList<String> darNombreClientes(ArrayList<Cliente> lClientes) {
		ArrayList<String> nombresCl = new ArrayList<String>();
		for (Cliente cl : lClientes) {
			nombresCl.add(cl.getId() + ": " + cl.getNombre() + " " + cl.getApellido());
		}
		return nombresCl;
	}
	
	public ArrayList<String> darStrCuotas(Cliente cl){
		ArrayList<String> strCuotas = new ArrayList<String>();
		String idCliente = cl.getId();
		ArrayList<Cuota> cuotas = main.darCuotasCliente(idCliente);
		
		for(Cuota cu : cuotas) {			
			strCuotas.add(cu.getId_cuota() + ": " + cu.getFecha_cobro());
		}
		
		return strCuotas;
	}
}
