package finanzasjp.vista;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cuota;
import finanzasjp.modelo.Recibo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
		
	private ObservableList<String> listDataCliente = FXCollections.observableArrayList();
	private ObservableList<String> listDataCuota= FXCollections.observableArrayList();
	
	private Cliente cl;

	public ListadoCuotaController() {
		
		//listDataCliente.addAll(darNombreClientes());		
	}

	public void initialize() {
		
	
	}
	
	public void generarListadoClientes() {
		LocalDate value = datePicker.getValue();
		String strFecha = value!=null ? value.toString():null;
		String strDia = !txDia.getText().equals("") ? txDia.getText():"0";
		
		try {
			ArrayList<Cliente> lClientes = main.darListadoCobro(Integer.parseInt(strDia),strFecha);			
			ArrayList<String> strClientes = darNombreClientes(lClientes);
			
			listDataCliente.addAll(strClientes);
			listaClientes.setItems(listDataCliente);
			listaClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

				public void changed(ObservableValue observable, Object arg1, Object arg2) {
					// TODO Auto-generated method stub				
					listDataCuota.clear();
					
					String[] data = arg2.toString().split(":");
					String nombre = data[1];				
					String ced = data[0].trim();
					
					//Se asigna el cliente seleccionado desde la lista de clientes
					cl = darCliente(ced);	
					
					listDataCuota.addAll(darStrCuotas(cl));
					listaCuotas.setItems(listDataCuota);
					
					listaCuotas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

						public void changed(ObservableValue arg0, Object arg1, Object arg2) {
							// TODO Auto-generated method stub

							String[] dataCu = arg2.toString().split(":");
							String noCuota = dataCu[0];
							String strFecha = dataCu[1];
							txCuota.setText(noCuota);
							txFechaCobro.setText(strFecha);
							
							Recibo reciboCl = main.darReciboCliente(cl);							
							Cuota cut = reciboCl.darCuotaId(Integer.parseInt(noCuota));
							
							txValorCuota.setText(""+cut.getValor());
							txValorPagado.setText(""+cut.getValor_pagado());
						}
					});
				}
			});	
			
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

	public ArrayList<String> darNombreClientes(ArrayList<Cliente> lClientes) {
		ArrayList<String> nombresCl = new ArrayList<String>();
		for (Cliente cl : lClientes) {
			nombresCl.add(cl.getId() + ": " + cl.getNombre() + " " + cl.getApellido());
		}
		return nombresCl;
	}
	
	public ArrayList<String> darStrCuotas(Cliente cl){
		ArrayList<String> strCuotas = new ArrayList<String>();
		ArrayList<Cuota> cuotas = main.darCuotasCliente(cl);
		
		for(Cuota cu : cuotas) {			
			strCuotas.add(cu.getId_cuota() + ": " + cu.getFecha_cobro());
		}
		
		return strCuotas;
	}
}
