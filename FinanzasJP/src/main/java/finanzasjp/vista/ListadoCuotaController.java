package finanzasjp.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cobrador;
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
import javafx.scene.control.ComboBox;
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

	private String cedulaCobrador = "";

	@FXML
	private ComboBox cobrador;
	private ObservableList<Cobrador> dataCobrador = FXCollections.observableArrayList();
	@FXML
	private ListView listaClientes;

	@FXML
	private ListView listaCuotas;

	@FXML
	private TextField txDia;

	@FXML
	private DatePicker datePicker;
	@FXML
	private DatePicker dteFechaPago;

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
	private ObservableList<Cuota> listDataCuota = FXCollections.observableArrayList();

	private Cliente cl;

	// Date Now ### "To Date Picker"
	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	public ListadoCuotaController() {
		dataCobrador.addAll(darNomCobradores());
	}

	public void initialize() {

		datePicker.setValue(NOW_LOCAL_DATE());
		main.actualizarEstadoCuotas(datePicker.getValue().toString());
		txValorPagado.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					txValorPagado.setText("");
				}
			}
		});

		txValorCuota.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					txValorCuota.setText("");
				}
			}
		});

		txDia.focusedProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) { // when focus lost
					if (!txDia.getText().matches("([0-9]|[12][0-9]|3[01])")) {
						// when it not matches the pattern (1.0 - 6.0)
						// set the textField empty
						txDia.setText("");
					}
				}
			}
		});

		cobrador.setItems(dataCobrador);
		cobrador.setConverter(new javafx.util.StringConverter<Cobrador>() {
			@Override
			public Cobrador fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Cobrador object) {
				// TODO Auto-generated method stub
				return object.getNombre();
			}
		});

		cobrador.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				Cobrador miCobrador = (Cobrador) newValue;
				cedulaCobrador = miCobrador.getId_cobrador();
			}
		});
	}

	public void generarArchivoListaCobro() {

		try {

			LocalDate value = datePicker.getValue();
			String strFecha = value != null ? value.toString() : null;
			String strDia = !txDia.getText().equals("") ? txDia.getText() : "0";

			if (strFecha == null && strDia.equals("0")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Seleccione una fecha o un día de pago");
				alert.showAndWait();

			} else {
				main.generarArchivoListaCobro(Integer.parseInt(strDia), strFecha, cedulaCobrador);
				Alert conf = new Alert(AlertType.INFORMATION);
				conf.setTitle("Información");
				conf.setHeaderText(null);
				conf.setContentText("Se ha generado el archivo " + datePicker.getValue().toString() + "_"
						+ "ListaDeCobro.xlsx en el escritorio");
				conf.showAndWait();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText(
					"Sí tiene el archivo ListaDeCobro.xls abierto, por favor cierrelo para generar uno nuevo");
			alert.showAndWait();

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generarListadoClientes() {
		LocalDate value = datePicker.getValue();
		String strFecha = value != null ? value.toString() : null;
		String strDia = !txDia.getText().equals("") ? txDia.getText() : "0";

		try {

			if (strFecha == null && strDia.equals("0")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Seleccione una fecha o un día de pago");
				alert.showAndWait();

			} else {

				ArrayList<Cliente> lClientes = main.darListadoCobro(Integer.parseInt(strDia), strFecha, cedulaCobrador);
				listDataCliente.addAll(lClientes);
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

					public void changed(ObservableValue observable, Object arg1, Object arg2) {
						// TODO Auto-generated method stub
						dteFechaPago.setValue(null);
						listDataCuota.clear();
						txValorPagado.setDisable(true);
						txValorCuota.setDisable(true);

						// Se asigna el cliente seleccionado desde la lista de clientes
						cl = (Cliente) arg2;

						String nombre = cl.getNombre();
						String ced = cl.getId();

						lbCliente.setText(nombre + " " + cl.getApellido());
						Recibo reciboCl = main.darReciboCliente(cl);
						txRecibo.setText("" + reciboCl.getId_recibo());
						txPrestamo.setText("" + reciboCl.getMonto_prestamo());
						txInteres.setText("" + (reciboCl.getInteres() * 100) + " %");
						txFechaPres.setText("" + reciboCl.getFecha_prestamo());
						txSaldo.setText("" + reciboCl.getSaldo());
						boolean moraRec = reciboCl.isMora();
						txMoraRecibo.setText(!moraRec ? "No" : "Sí");
						txFechaFin.setText("" + reciboCl.getFecha_fin());
						txPagoTotal.setText("" + reciboCl.getPago_total());

						ArrayList<Dia_Recibo> dias = main.darDias(reciboCl);
						String sdias = "";
						if (!dias.isEmpty()) {
							for (Dia_Recibo d : dias) {
								sdias += d.getId_dia() + ",";
							}
							sdias = sdias.substring(0, sdias.length() - 1);
						}

						txDias.setText(sdias);

						listDataCuota.addAll(darStrCuotas(cl));
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

							public void changed(ObservableValue arg0, Cuota arg1, Cuota arg2) {
								// TODO Auto-generated method stub

								dteFechaPago.setValue(null);
								txCuota.setText("");
								txFechaCobro.setText("");
								txValorCuota.setText("");
								txValorPagado.setText("");
								txValorPagado.setDisable(false);
								txValorCuota.setDisable(false);

								if (arg2 != null) {

									String noCuota = arg2.getId_cuota() + "";
									String strFecha = arg2.getFecha_cobro().toString();
									txCuota.setText(noCuota);
									txFechaCobro.setText(strFecha);

									Recibo reciboCl = main.darReciboCliente(cl);
									Cuota cut = reciboCl.darCuotaId(Integer.parseInt(noCuota));

									txValorCuota.setText("" + cut.getValor());
									txValorPagado.setText("" + cut.getValor_pagado());
									boolean mora = cut.isMora();
									txMora.setText(!mora ? "No" : "Sí");

									if (cut.getFecha_pago() != null) {
										dteFechaPago.setValue(cut.getFecha_pago().toLocalDate());
									}
								}

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

		String fechaPago = null;

		if (dteFechaPago.getValue() != null) {
			fechaPago = dteFechaPago.getValue().toString();
		}

		main.guardarPago(Double.parseDouble(txValorPagado.getText()), Double.parseDouble(txValorCuota.getText()),
				Integer.parseInt(txCuota.getText()), Integer.parseInt(txRecibo.getText()), fechaPago);

		double saldo = Double.parseDouble(txSaldo.getText());
		saldo = saldo - Double.parseDouble(txValorPagado.getText());
		txSaldo.setText("" + saldo);
	}

	public void generarNuevaCuota() {

		txCuota.setText("" + main.darNuevaCuotaRecibo(txRecibo.getText()));
		txValorCuota.setDisable(false);
		txValorPagado.setDisable(false);

		txValorCuota.setText("");
		txValorPagado.setText("");

		txFechaCobro.setText(NOW_LOCAL_DATE().toString());
	}

	public ArrayList<String> darNombreClientes(ArrayList<Cliente> lClientes) {
		ArrayList<String> nombresCl = new ArrayList<String>();
		for (Cliente cl : lClientes) {
			nombresCl.add(cl.getId() + ": " + cl.getNombre() + " " + cl.getApellido());
		}
		return nombresCl;
	}

	public ArrayList<Cuota> darStrCuotas(Cliente cl) {
		String idCliente = cl.getId();
		ArrayList<Cuota> cuotas = main.darCuotasCliente(idCliente);
		return cuotas;
	}

	private ArrayList<Cobrador> darNomCobradores() {
		// TODO Auto-generated method stub
		return main.darNomCobradores();
	}
}
