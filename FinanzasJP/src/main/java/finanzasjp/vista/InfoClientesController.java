package finanzasjp.vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.sun.javafx.css.converters.StringConverter;

import antlr.Token;
import finanzasjp.modelo.Cliente;
import finanzasjp.modelo.Cliente_VIP;
import finanzasjp.modelo.Dia_Recibo;
import finanzasjp.modelo.OpcionPrestamo;
import finanzasjp.modelo.Recibo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class InfoClientesController {

	private Main main;

	private Cliente miCliente;
	private String cedulaClienteVip = "";
	private String modoPago = "";

	@FXML
	private ListView listaClientes;

	@FXML
	private ListView listaCuotas;

	@FXML
	private GridPane gridRecibo_admin;
	@FXML
	private GridPane gridRecibo_mod;

	@FXML
	private TextField txNombre_mod;
	@FXML
	private TextField txCedula_mod;
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
	private ComboBox modoPago_admin;
	@FXML
	private ComboBox modoPago_mod;

	@FXML
	private TextField txDias_admin;
	@FXML
	private Button btGuardarCliente;
	@FXML
	private Button btGuardarRecibo;
	@FXML
	private Button btGuardarReciboMod;

	final Tooltip tooltip = new Tooltip();

	private ObservableList<Cliente> listViewData = FXCollections.observableArrayList();
	private ObservableList<Cliente_VIP> dataPert_admin = FXCollections.observableArrayList();
	private ObservableList<String> dataModoPago_admin = FXCollections.observableArrayList();

	// Admin nuevo cliente
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
	@FXML
	private TextField txTelFijoAdmin;
	@FXML
	private TextField txBarrioAdmin;
	@FXML
	private TextField txTrabajoAdmin;
	@FXML
	private TextField txTelTrabajoAdmin;
	@FXML
	private Label lbSinRecibo;

	// Admin nuevo recibo
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
	@FXML
	private Label lbRecibo_mod;

	@FXML
	private TextField txPrestamo_mod;

	@FXML
	private DatePicker txFechaPres_mod;

	@FXML
	private DatePicker txFechaFin_mod;

	@FXML
	private TextField txInteres_mod;

	@FXML
	private TextField txCuotas_mod;
	@FXML
	private TextField txDias_mod;

	@FXML
	private Label lbPagoTotal_mod;

	@FXML
	private DatePicker dtCurrentDate;

	// Date Now ### "To Date Picker"
	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	public InfoClientesController() {

		listViewData.addAll(darNomClientes());
		dataPert_admin.addAll(darNomClientesVip());
		dataModoPago_admin.addAll(darModosPago());
	}

	public void initialize() {

		btGuardarRecibo.setDisable(false);
		btGuardarReciboMod.setDisable(false);

		txInteres_admin.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {

					txInteres_admin.setText("");
				}
			}
		});

		txPrestamo_admin.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					txPrestamo_admin.setText("");
				}
			}
		});

		txPrestamo_mod.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					txPrestamo_mod.setText("");
				}
			}
		});

		txInteres_mod.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					txInteres_mod.setText("");
				}
			}
		});

		dtCurrentDate.setValue(NOW_LOCAL_DATE());
		main.actualizarEstadoCuotas(dtCurrentDate.getValue().toString());

		tooltip.setText("Agregue los días separados por coma (ej: 5, 20)");
		txDias_admin.setTooltip(tooltip);

		modoPago_admin.setItems(dataModoPago_admin);
		modoPago_admin.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				modoPago = newValue;
				txDias_admin.setText("");
				txDias_admin.setDisable(false);

				if (newValue.equals("Semanal")) {
					txDias_admin.setText(newValue);
					txDias_admin.setDisable(true);
				}

			}

		});

		modoPago_mod.setItems(dataModoPago_admin);
		modoPago_mod.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				modoPago = newValue;
				txDias_mod.setText("");
				txDias_mod.setDisable(false);

				if (newValue.equals("Semanal")) {
					txDias_mod.setText(newValue);
					txDias_mod.setDisable(true);
				}

			}
		});

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

			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub

				lbSinRecibo.setText("");
				Cliente cl = (Cliente) arg2;
				miCliente = cl;
				// String[] data = arg2.toString().split(":");
				// String nombre = data[1];
				// txNombre.setText(nombre);
				txNombre.setText(cl.getNombre() + " " + cl.getApellido());

				// String ced = data[0].trim();
				String ced = cl.getId();
				// Cliente cl = darCliente(ced);

				txCedula.setText(cl.getId());
				txTelefono.setText(cl.getTelefono_celular());
				txDireccion.setText(cl.getDireccion());

				Recibo rec = main.darReciboCliente(cl);

				if (rec != null) {

					txRecibo.setText("" + rec.getId_recibo());
					txActivo.setText(rec.isActivo() ? "Sí" : "No");
					txFechaFin.setText("" + rec.getFecha_fin());
					txFechaPres.setText("" + rec.getFecha_prestamo());
					txMora.setText(rec.isMora() ? "Sí" : "No");
					txPrestamo.setText("" + rec.getMonto_prestamo());
					txInteres.setText("" + rec.getInteres());
					txPagoTotal.setText("" + rec.getPago_total());
					txSaldo.setText("" + rec.getSaldo());

					ArrayList<Dia_Recibo> dias = main.darDias(rec);
					if (!dias.isEmpty()) {
						String sdias = "";
						for (Dia_Recibo d : dias) {
							sdias += d.getId_dia() + ",";
						}
						sdias = sdias.substring(0, sdias.length() - 1);
						txDias.setText(sdias);
					}

				} else {

					txRecibo.setText("");
					txActivo.setText("");
					txFechaFin.setText("");
					txFechaPres.setText("");
					txMora.setText("");
					txPrestamo.setText("");
					txInteres.setText("");
					txPagoTotal.setText("");
					txDias.setText("");
					txSaldo.setText("");

					lbSinRecibo.setText("El cliente seleccionado no tiene recibo activo");
				}

				txNombre_mod.setText(cl.getNombre());
				txCedula_mod.setText(cl.getId());
			}
		});
	}

	public ArrayList<Cliente> darNomClientes() {
		return main.darClientes();
	}

	public ArrayList<Cliente_VIP> darNomClientesVip() {
		return main.darClientesVip();
	}

	public ArrayList<String> darModosPago() {

		ArrayList<String> modos = new ArrayList<String>();
		modos.add("Mensual");
		modos.add("Quincenal");
		modos.add("Semanal");

		return modos;
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
		if (result.get() == ButtonType.OK) {
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
		if (cedulaClienteVip.equals("") || cedCl.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText("1. Debes indicar a quien pertenece \n2. Debes escribir la cédula del nuevo cliente");
			alert.showAndWait();
		} else {

			Alert alertConf = new Alert(AlertType.CONFIRMATION);
			alertConf.setTitle("Confirmación");
			alertConf.setHeaderText("¿Confirma que desea almacenar los siguientes datos?");
			alertConf.setContentText(" Nombre: " + txNombreAdmin.getText() + "\n Apellido: " + txApellidoAdmin.getText()
					+ "\n Cédula: " + txCedulaAdmin.getText() + "\n Teléfono: " + txTelefonoAdmin.getText()
					+ "\n Dirección: " + txDireccionAdmin.getText());

			Optional<ButtonType> result = alertConf.showAndWait();
			boolean resp = false;
			boolean flagOk = false; // flag usado para confirmar que se hizo clic en ok y asi mostrar el respectivo
									// mensaje

			if (result.get() == ButtonType.OK) {
				resp = main.guardarCliente(cedulaClienteVip, txCedulaAdmin.getText(), txNombreAdmin.getText(),
						txApellidoAdmin.getText(), txTelefonoAdmin.getText(), txDireccionAdmin.getText(),
						txTelFijoAdmin.getText(), txBarrioAdmin.getText(), txTrabajoAdmin.getText(),
						txTelTrabajoAdmin.getText());
				flagOk = true;
			} else {
				// ... user chose CANCEL or closed the dialog
			}

			if (resp && flagOk) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmación");
				alert.setHeaderText(null);
				alert.setContentText("Datos guardados exitosamente");
				alert.showAndWait();

				// Inhabilitar campos de contacto
				txNombreAdmin.setDisable(true);
				txApellidoAdmin.setDisable(true);
				txCedulaAdmin.setDisable(true);
				txTelefonoAdmin.setDisable(true);
				txDireccionAdmin.setDisable(true);
				btGuardarCliente.setDisable(true);

				// Se habilita el grid del recibo
				gridRecibo_admin.setDisable(false);
				lbRecibo.setText(Integer.toString(main.darNumUltRecibo()));
			} else {
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

		try {

			if (txPrestamo_admin.getText() == "" || txInteres_admin.getText() == ""
					|| txFechaPres_admin.getValue() == null || txFechaFin_admin.getValue() == null
					|| txCuotas_admin.getText() == "" || txDias_admin.getText() == "") {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Ningún campo del recibo puede quedar vacío");
				alert.showAndWait();
			} else {

				Alert alertConf = new Alert(AlertType.CONFIRMATION);
				alertConf.setTitle("Confirmación");
				alertConf.setHeaderText(null);
				alertConf.setContentText("¿Confirma que desea almacenar el recibo?");
				Optional<ButtonType> result = alertConf.showAndWait();
				boolean resp = false;
				boolean flagOk = false; // flag usado para confirmar que se hizo clic en ok y asi mostrar el respectivo
										// mensaje

				if (result.get() == ButtonType.OK) {
					String[] losDias = null;
					if (!txDias_admin.getText().equals("Semanal") && !txDias_admin.getText().equals("Diario")) {
						losDias = txDias_admin.getText().split(",");
					}

					double pagTotal = main.calcularPagoTotalLabel(Double.parseDouble(txPrestamo_admin.getText()),
							Double.parseDouble(txInteres_admin.getText()), modoPago,
							Integer.parseInt(txCuotas_admin.getText()));
					lbPagoTotal_admin.setText("" + pagTotal);

					if (Double.parseDouble(txInteres_admin.getText()) < 1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Información");
						alert.setHeaderText(null);
						alert.setContentText("El interés no puede ser menor que el 1%");
						alert.showAndWait();
					} else {
						// Guarda el RECIBO
						resp = main.guardarRecibo(Integer.parseInt(lbRecibo.getText()), txCedulaAdmin.getText(),
								Double.parseDouble(txPrestamo_admin.getText()),
								Double.parseDouble(txInteres_admin.getText()), txFechaPres_admin.getValue().toString(),
								txFechaFin_admin.getValue().toString(), pagTotal, losDias);

						// Genera las CUOTAS
						boolean resCuota = main.generarCuotas(txPrestamo_admin.getText(), txInteres_admin.getText(),
								modoPago, txCuotas_admin.getText(), lbRecibo.getText());

						if (resp && resCuota) {
							flagOk = true;
						}

					}

				} else {
					// ... user chose CANCEL or closed the dialog
				}

				if (resp && flagOk) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Confirmación");
					alert.setHeaderText(null);
					alert.setContentText("Datos guardados exitosamente");
					alert.showAndWait();

					btGuardarRecibo.setDisable(true);

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Información");
					alert.setHeaderText(null);
					alert.setContentText("El recibo No. " + lbRecibo.getText() + " ya existe");
					alert.showAndWait();
				}
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void generarCuotas() {

		try {
			main.verInfoCuotas(txNombreAdmin.getText(), lbRecibo.getText(), txPrestamo_admin.getText(),
					txInteres_admin.getText(), lbPagoTotal_admin.getText(), txCedulaAdmin.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void verCuotas() {

		try {
			main.verInfoCuotas(txNombre.getText(), txRecibo.getText(), txPrestamo.getText(), txInteres.getText(),
					txPagoTotal.getText(), txCedula.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void generarCuotasMod() {

		try {
			main.verInfoCuotas(txNombre_mod.getText(), lbRecibo_mod.getText(), txPrestamo_mod.getText(),
					txInteres_mod.getText(), lbPagoTotal_mod.getText(), txCedula_mod.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void limpiarDatos() {

		txNombreAdmin.setText("");
		txApellidoAdmin.setText("");
		txCedulaAdmin.setText("");
		txTelefonoAdmin.setText("");
		txDireccionAdmin.setText("");
		txBarrioAdmin.setText("");
		txTelFijoAdmin.setText("");
		txTelTrabajoAdmin.setText("");
		txTrabajoAdmin.setText("");

		txPrestamo_admin.setText("");
		txInteres_admin.setText("");
		txCuotas_admin.setText("");
		txDias_admin.setText("");
		txFechaPres_admin.setValue(null);
		txFechaFin_admin.setValue(null);

		txNombreAdmin.setDisable(false);
		txApellidoAdmin.setDisable(false);
		txCedulaAdmin.setDisable(false);
		txTelefonoAdmin.setDisable(false);
		txDireccionAdmin.setDisable(false);
		btGuardarCliente.setDisable(false);
		txBarrioAdmin.setDisable(false);
		txTelFijoAdmin.setDisable(false);
		txTelTrabajoAdmin.setDisable(false);
		txTrabajoAdmin.setDisable(false);

		btGuardarRecibo.setDisable(false);
	}

	@FXML
	private void crearPrestamoClienteExistente() {
		String val = main.validarReciboActivoCliente(txCedula_mod.getText());

		if (val.equals("")) {
			// Se habilita el grid del recibo
			gridRecibo_mod.setDisable(false);
			lbRecibo_mod.setText(Integer.toString(main.darNumUltRecibo()));
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Información");
			alert.setHeaderText(null);
			alert.setContentText(
					"El cliente " + txNombre_mod.getText() + " actualmente tiene el siguiente recibo activo: " + val);
			alert.showAndWait();
		}
	}

	@FXML
	private void guardarReciboMod() {

		try {

			if (txPrestamo_mod.getText() == "" || txInteres_mod.getText() == "" || txFechaPres_mod.getValue() == null
					|| txFechaFin_mod.getValue() == null || txCuotas_mod.getText() == ""
					|| txDias_mod.getText() == "") {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Información");
				alert.setHeaderText(null);
				alert.setContentText("Ningún campo del recibo puede quedar vacío");
				alert.showAndWait();
			} else {

				Alert alertConf = new Alert(AlertType.CONFIRMATION);
				alertConf.setTitle("Confirmación");
				alertConf.setHeaderText(null);
				alertConf.setContentText("¿Confirma que desea almacenar el recibo?");
				Optional<ButtonType> result = alertConf.showAndWait();
				boolean resp = false;
				boolean flagOk = false; // flag usado para confirmar que se hizo clic en ok y asi mostrar el respectivo
										// mensaje

				if (result.get() == ButtonType.OK) {
					String[] losDias = null;
					if (!txDias_mod.getText().equals("Semanal") && !txDias_mod.getText().equals("Diario")) {
						losDias = txDias_mod.getText().split(",");
					}

					if (Double.parseDouble(txInteres_mod.getText()) < 1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Información");
						alert.setHeaderText(null);
						alert.setContentText("El interés no puede ser menor que el 1%");
						alert.showAndWait();
					} else {
						double pagTotal = main.calcularPagoTotalLabel(Double.parseDouble(txPrestamo_mod.getText()),
								Double.parseDouble(txInteres_mod.getText()), modoPago,
								Integer.parseInt(txCuotas_mod.getText()));
						lbPagoTotal_mod.setText("" + pagTotal);

						// Guardar el RECIBO
						resp = main.guardarRecibo(Integer.parseInt(lbRecibo_mod.getText()), txCedula_mod.getText(),
								Double.parseDouble(txPrestamo_mod.getText()),
								Double.parseDouble(txInteres_mod.getText()), txFechaPres_mod.getValue().toString(),
								txFechaFin_mod.getValue().toString(), pagTotal, losDias);
						// Generar las CUOTAS
						boolean resCuo = main.generarCuotas(txPrestamo_mod.getText(), txInteres_mod.getText(), modoPago,
								txCuotas_mod.getText(), lbRecibo_mod.getText());

						if (resp && resCuo) {
							flagOk = true;
						}
					}

				} else {
					// ... user chose CANCEL or closed the dialog
				}

				if (resp && flagOk) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Confirmación");
					alert.setHeaderText(null);
					alert.setContentText("Datos guardados exitosamente");
					alert.showAndWait();

					btGuardarReciboMod.setDisable(true);

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Información");
					alert.setHeaderText(null);
					alert.setContentText("El recibo No. " + lbRecibo_mod.getText() + " ya existe");
					alert.showAndWait();
				}
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void verListaCuotasMora() {
		try {
			main.verListaCuotasMora();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void verDatosCodeudor() {
		try {
			main.verDatosCodeudor(txNombreAdmin.getText(), txCedulaAdmin.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void verDatosCompletos() {
		try {
			main.verDatosCompletos(miCliente);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
