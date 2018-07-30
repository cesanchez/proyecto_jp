package finanzasjp.vista;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IndexController {
	
	private Main main;
	
	@FXML
	private TextField myText; 
	
	@FXML
	private void verClientes() {
		
		try {
			main.verInfoClientes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
						
	}
	
	@FXML
	private void sample() {
		myText.setText("Hola");
	}
	
}
