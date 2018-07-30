package finanzasjp.vista;

import java.text.ParseException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class GenListadosController {
	
	private Main main;
	
	@FXML
	private TextField txDia;
	
	@FXML
	private DatePicker datePicker;
	
	public GenListadosController() {
		
	}
	
	@FXML
	private void generarListado() {
		
		LocalDate value = datePicker.getValue();
		String strFecha = value!=null ? value.toString():null;	
		
		try {
			main.generarListadoCobro(Integer.parseInt(txDia.getText()),strFecha);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
