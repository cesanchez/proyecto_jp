package finanzasjp.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cuota;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

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
	private TextField tx_cuota;
	@FXML
	private TextField tx_valorPago;
	@FXML
	private DatePicker dt_fechaCuota;
	@FXML
	private ListView listaCuotas;
	private ObservableList<Cuota> dataCuotas = FXCollections.observableArrayList();
	
	public static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
	
	public CuotasClienteController() {
		
	}
	
	public void inicializarCuotas(String idCliente) {
		dataCuotas.addAll(darCuotas(idCliente));
	}
	
	public void initialize() {
		
		listaCuotas.setItems(dataCuotas);
		
		listaCuotas.setCellFactory(new Callback<ListView<Cuota>, ListCell<Cuota>>() {

			public ListCell<Cuota> call(ListView<Cuota> param) {
				// TODO Auto-generated method stub
				ListCell<Cuota> cell = new ListCell<Cuota>(){ 
                    @Override
                    protected void updateItem(Cuota t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("Cuota # " + t.getId_cuota());
                        }
                    } 
                };                 
                return cell;
			}
		});
		listaCuotas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cuota>() {

			public void changed(ObservableValue<? extends Cuota> observable, Cuota oldValue, Cuota newValue) {
				// TODO Auto-generated method stub
				tx_cuota.setText("" + newValue.getId_cuota());
				tx_valorPago.setText("" + newValue.getValor());
				String strFecha = "";
				if(newValue.getFecha_cobro() != null) {
					strFecha = newValue.getFecha_cobro().toString();
					dt_fechaCuota.setValue(LOCAL_DATE(strFecha));
				}								
			}			
		});
		
	}
	
	@FXML
	private void guardarFechaCuota() {
		main.guardarFechaCuota(dt_fechaCuota.getValue().toString(), lb_idRecibo.getText(), tx_cuota.getText());
	}
	
	private ArrayList<Cuota> darCuotas(String idCliente) {
		// TODO Auto-generated method stub
		//ArrayList<String> strCuotas = new ArrayList<String>();		
		ArrayList<Cuota> cuotas = main.darCuotasCliente(idCliente);
		
//		for(Cuota c : cuotas) {
//			strCuotas.add("Cuota # " + c.getId_cuota());
//		}
		
		return cuotas;
	}

	public void setCampos(String nombre, String idRecibo, String prestamo, String interes, String pagoTotal, String idCliente) {
		lb_NomCliente.setText(nombre);
		lb_idRecibo.setText(idRecibo);
		txPrestamo.setText(prestamo);
		txInteres.setText(interes);
		tx_PagoTotal.setText(pagoTotal);
		lb_idCliente.setText(idCliente);
	}
}
