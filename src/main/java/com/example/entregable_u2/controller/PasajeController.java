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
import com.example.entregable_u2.implementacion.PasajeDaoImpl;
import com.example.entregable_u2.interfaces.IPasajeDao;
import com.example.entregable_u2.model.Pasaje;
import com.example.entregable_u2.utils.AlertsUtils;

import java.time.LocalDate;
import java.util.Optional;

public class PasajeController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Pasaje> tablePasaje;
    @FXML
    private TableColumn<Pasaje, Number> ColNumero;
    @FXML
    private TableColumn<Pasaje, Integer> ColId;
    @FXML
    private TableColumn<Pasaje, String> ColPasajero;
    @FXML
    private TableColumn<Pasaje, String> ColDni;
    @FXML
    private TableColumn<Pasaje, String> ColOrigen;
    @FXML
    private TableColumn<Pasaje, String> ColDestino;
    @FXML
    private TableColumn<Pasaje, LocalDate> ColSalida;
    @FXML
    private TableColumn<Pasaje, Double> ColCosto;

    private final IPasajeDao pasajeDao = new PasajeDaoImpl();
    private ObservableList<Pasaje> listaPasaje;

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

        //llenar las columnas
        ColId.setCellValueFactory(
                cellData -> cellData.getValue().idpasajeProperty().asObject());
        ColPasajero.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPasajero().getNombres() + " " + cellData.getValue().getPasajero().getApellidos()));
        ColDni.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getPasajero().getDni()));
        ColOrigen.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getViaje().getLugarOrigen()));
        ColDestino.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getViaje().getLugarDestino()));
        ColSalida.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getViaje().getFechaSalida()));
        ColCosto.setCellValueFactory(
                cellData -> cellData.getValue().costoProperty().asObject());
        //cargar datos iniciales
        cargarDatos();
    }

    private void cargarDatos() {
        listaPasaje = FXCollections.observableArrayList(pasajeDao.listAll());
        tablePasaje.setItems(listaPasaje);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Pasaje seleccionado = tablePasaje.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Pasaje completo = pasajeDao.searchById(seleccionado.getIdpasaje());
            showForm(completo);
        } else {
            Alert alert = alertsUtils.viewAlerts("Seleccion requerida",
                    "Seleccione un registro de la tabla", "WARNING");
            alert.show();
        }
    }

    @FXML
    private void handleDelete() {
        Pasaje seleccionado = tablePasaje.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = alertsUtils.viewAlerts("Confirmar Eliminacion",
                    "Estas seguro de eliminar este registro?", "CONFIRMATION");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (pasajeDao.delete(seleccionado)) {
                    listaPasaje.remove(seleccionado);
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

    private void showForm(Pasaje p) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class
                    .getResource("/com/example/entregable_u2/views/pasaje-form.fxml"));
            Parent root = loader.load();
            PasajeFormController controller = loader.getController();
            controller.setPasaje(p);
            Stage stage = new Stage();
            stage.setTitle(p == null ? "Nuevo pasaje" : "Editar pasaje");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Pasaje procesado = controller.getPasaje();
                // validar la capacidad de personas del vehiculo del viaje
                if (pasajeDao.validatedCapacity(procesado)) {
                    Alert alert = alertsUtils.viewAlerts("Capacidad completa",
                            "El viaje ya no tiene asientos disponibles (capacidad del vehiculo)", "WARNING");
                    alert.show();
                    return;
                }

                if (p == null) {
                    boolean result = pasajeDao.insert(procesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts("Success", "Registro satisfactorio!!!", "CONFIRMATION");
                        alert.show();
                    } else {
                        Alert alert = alertsUtils.viewAlerts("Error", "Error al insertar!!!", "ERROR");
                        alert.show();
                    }
                } else {
                    boolean result = pasajeDao.update(procesado);
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

