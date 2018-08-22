package finanzasjp.vista;

import java.util.ArrayList;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cuota;
import finanzasjp.modelo.Mora;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;

public class ListadoCuotaMoraController {

	private Main main;

	private Cliente cl;

	@FXML
	private ListView listaClientes;

	@FXML
	private ListView listaCuotas;

	@FXML
	private TextField txCuota;

	@FXML
	private TextField txFechaCobro;

	@FXML
	private TextField txValorCuota;
	@FXML
	private TextField txTiempoMora;
	@FXML
	private TextField txValorPagado;

	@FXML
	private TextField txValorMora;

	private ArrayList<Mora> moraCliente = new ArrayList<Mora>();

	private ObservableList<Cliente> listDataCliente = FXCollections.observableArrayList();
	private ObservableList<Cuota> listDataCuota = FXCollections.observableArrayList();

	public ListadoCuotaMoraController() {

		listDataCliente.addAll(main.darListaClientesMora());
	}

	public void initialize() {

		listaClientes.setItems(listDataCliente);

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

		listaClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub

				listDataCuota.clear();
				cl = (Cliente) newValue;

				moraCliente = main.darInfMoraCliente(cl.getId());

				listDataCuota.addAll(darCuotasMora(cl));
				listaCuotas.setItems(listDataCuota);
				listaCuotas.setCellFactory(new Callback<ListView<Cuota>, ListCell<Cuota>>() {

					public ListCell<Cuota> call(ListView<Cuota> param) {
						// TODO Auto-generated method stub
						ListCell<Cuota> cell = new ListCell<Cuota>() {
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

						if (newValue != null) {
							txCuota.setText("" + newValue.getId_cuota());
							txValorPagado.setText("" + newValue.getValor_pagado());
							txFechaCobro.setText("" + newValue.getFecha_cobro().toString());
							txValorCuota.setText("" + newValue.getValor());

							for (Mora m : moraCliente) {
								if (m.getCuota().getId_cuota() == newValue.getId_cuota()) {
									txValorMora.setText("" + m.getValorMora());
									txTiempoMora.setText("" + m.getDiasMora() + " días");
								}
							}
						}

					}
				});
			}
		});

	}

	public ArrayList<Cuota> darCuotasMora(Cliente cl) {
		ArrayList<Cuota> strCuotas = new ArrayList<Cuota>();
		String idCliente = cl.getId();
		ArrayList<Cuota> cuotas = main.darCuotasMoraCliente(idCliente);

		return cuotas;
	}

	public void generarListadoCsvMora() {
		main.generarListadoCsvMora();

		Alert conf = new Alert(AlertType.INFORMATION);
		conf.setTitle("Información");
		conf.setHeaderText(null);
		conf.setContentText("Se ha generado el archivo ListaDeCuotasEnMora.xlsx en el escritorio");
		conf.showAndWait();
	}

}
