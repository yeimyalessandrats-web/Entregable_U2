package com.example.entregable_u2.controller;

import com.example.entregable_u2.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.entregable_u2.implementacion.ViajeDaoImpl;
import com.example.entregable_u2.interfaces.IViajeDao;
import com.example.entregable_u2.model.Viaje;
import com.example.entregable_u2.utils.AlertsUtils;

import java.time.LocalDate;
import java.util.Optional;

public class ViajeController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Viaje> tableViaje;
    @FXML
    private TableColumn<Viaje, Number> ColNumero;
    @FXML
    private TableColumn<Viaje, Integer> ColId;
    @FXML
    private TableColumn<Viaje, String> ColOrigen;
    @FXML
    private TableColumn<Viaje, String> ColDestino;
    @FXML
    private TableColumn<Viaje, LocalDate> ColSalida;
    @FXML
    private TableColumn<Viaje, LocalDate> ColLlegada;
    @FXML
    private TableColumn<Viaje, String> ColConductor;
    @FXML
    private TableColumn<Viaje, String> ColVehiculo;
    @FXML
    private TableColumn<Viaje, Integer> ColCapacidad;

    private final IViajeDao viajeDao = new ViajeDaoImpl();
    private ObservableList<Viaje> listaViaje;

    @FXML
    public void initialize() {
        ColNumero.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });


        ColId.setCellValueFactory(
                cellData -> cellData.getValue().idviajeProperty().asObject());
        ColOrigen.setCellValueFactory(
                cellData -> cellData.getValue().lugarOrigenProperty());
        ColDestino.setCellValueFactory(
                cellData -> cellData.getValue().lugarDestinoProperty());
        ColSalida.setCellValueFactory(
                cellData -> cellData.getValue().fechaSalidaProperty());
        ColLlegada.setCellValueFactory(
                cellData -> cellData.getValue().fechaLlegadaProperty());
        ColConductor.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getConductor().getNombres() + " " + cellData.getValue().getConductor().getApellidos()));
        ColVehiculo.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getVehiculo().getDescripcion() + " " + cellData.getValue().getVehiculo().getMarca()));
        ColCapacidad.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getVehiculo().getCapacidad()).asObject());

        cargarDatos();
    }

    private void cargarDatos() {
        listaViaje = FXCollections.observableArrayList(viajeDao.listAll());
        tableViaje.setItems(listaViaje);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Viaje seleccionado = tableViaje.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Viaje completo = viajeDao.searchById(seleccionado.getIdviaje());
            showForm(completo);
        } else {
            Alert alert = alertsUtils.viewAlerts("Seleccion requerida",
                    "Seleccione un registro de la tabla", "WARNING");
            alert.show();
        }
    }

    @FXML
    private void handleDelete() {
        Viaje seleccionado = tableViaje.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = alertsUtils.viewAlerts("Confirmar Eliminacion",
                    "Estas seguro de eliminar este registro?", "CONFIRMATION");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (viajeDao.delete(seleccionado)) {
                    listaViaje.remove(seleccionado);
                } else {
                    Alert error = alertsUtils.viewAlerts("Error",
                            "No se pudo eliminar, el registro tiene datos relacionados", "ERROR");
                    error.show();
                }
            }
        } else {
            Alert alert = alertsUtils.viewAlerts("Seleccion requerida",
                    "Seleccione un registro de la tabla", "WARNING");
            alert.show();
        }
    }

    private void showForm(Viaje x) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class
                    .getResource("/com/example/entregable_u2/views/viaje-form.fxml"));
            Parent root = loader.load();
            ViajeFormController controller = loader.getController();
            controller.setViaje(x); //pasamos null si es nuevo
            Stage stage = new Stage();
            stage.setTitle(x == null ? "Nuevo viaje" : "Editar viaje");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Viaje procesado = controller.getViaje();

                if (x == null) {
                    boolean result = viajeDao.insert(procesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts("Success", "Registro satisfactorio!!!", "CONFIRMATION");
                        alert.show();
                    } else {
                        Alert alert = alertsUtils.viewAlerts("Error", "Error al insertar!!!", "ERROR");
                        alert.show();
                    }
                } else {
                    boolean result = viajeDao.update(procesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts("Update", "Registro actualizado!!!", "WARNING");
                        alert.showAndWait();
                    } else {
                        Alert alert = alertsUtils.viewAlerts("Error", "Error al actualizar!!!", "ERROR");
                        alert.show();
                    }
                }
                cargarDatos();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}