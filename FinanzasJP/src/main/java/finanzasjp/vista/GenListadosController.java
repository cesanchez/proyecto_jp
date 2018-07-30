package finanzasjp.vista;

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
				
		main.generarListadoCobro(Integer.parseInt(txDia.getText()), value.toString());
		
	}

}
