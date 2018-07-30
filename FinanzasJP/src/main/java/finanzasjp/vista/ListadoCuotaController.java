package finanzasjp.vista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ListadoCuotaController {
	
	private Main main;
	
	@FXML
	private GridPane grid;
	
	public ListadoCuotaController() {
		
	}
	
	public void test() {
		grid.add(new Label("Label 1"), 0, 0);
	}

}
