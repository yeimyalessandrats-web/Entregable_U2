package com.example.entregable_u2.controller;

import com.example.entregable_u2.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.entregable_u2.implementacion.PasajeroDaoImpl;
import com.example.entregable_u2.interfaces.IPasajeroDao;
import com.example.entregable_u2.model.Pasajero;
import com.example.entregable_u2.utils.AlertsUtils;

import java.time.LocalDate;
import java.util.Optional;

public class PasajeroController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Pasajero> tablePasajero;
    @FXML
    private TableColumn<Pasajero, Number> ColNumero;
    @FXML
    private TableColumn<Pasajero, Integer> ColId;
    @FXML
    private TableColumn<Pasajero, String> ColNombres;
    @FXML
    private TableColumn<Pasajero, String> ColApellidos;
    @FXML
    private TableColumn<Pasajero, String> ColDni;
    @FXML
    private TableColumn<Pasajero, Integer> ColEdad;
    @FXML
    private TableColumn<Pasajero, String> ColTelefono;
    @FXML
    private TableColumn<Pasajero, String> ColDireccion;
    @FXML
    private TableColumn<Pasajero, LocalDate> ColFechaNac;

    private final IPasajeroDao pasajeroDao = new PasajeroDaoImpl();
    private ObservableList<Pasajero> listaPasajero;

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
                cellData -> cellData.getValue().idpasajeroProperty().asObject());
        ColNombres.setCellValueFactory(
                cellData -> cellData.getValue().nombresProperty());
        ColApellidos.setCellValueFactory(
                cellData -> cellData.getValue().apellidosProperty());
        ColDni.setCellValueFactory(
                cellData -> cellData.getValue().dniProperty());
        ColEdad.setCellValueFactory(
                cellData -> cellData.getValue().edadProperty().asObject());
        ColTelefono.setCellValueFactory(
                cellData -> cellData.getValue().telefonoProperty());
        ColDireccion.setCellValueFactory(
                cellData -> cellData.getValue().direccionProperty());
        ColFechaNac.setCellValueFactory(
                cellData -> cellData.getValue().fechaNacimientoProperty());
        //cargar datos iniciales
        cargarDatos();
    }

    private void cargarDatos() {
        listaPasajero = FXCollections.observableArrayList(pasajeroDao.listAll());
        tablePasajero.setItems(listaPasajero);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Pasajero seleccionado = tablePasajero.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Pasajero completo = pasajeroDao.searchById(seleccionado.getIdpasajero());
            showForm(completo);
        } else {
            Alert alert = alertsUtils.viewAlerts("Seleccion requerida",
                    "Seleccione un registro de la tabla", "WARNING");
            alert.show();
        }
    }

    @FXML
    private void handleDelete() {
        Pasajero seleccionado = tablePasajero.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = alertsUtils.viewAlerts("Confirmar Eliminacion",
                    "Estas seguro de eliminar este registro?", "CONFIRMATION");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (pasajeroDao.delete(seleccionado)) {
                    listaPasajero.remove(seleccionado);
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

    private void showForm(Pasajero x) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class
                    .getResource("/com/example/entregable_u2/views/pasajero-form.fxml"));
            Parent root = loader.load();
            PasajeroFormController controller = loader.getController();
            controller.setPasajero(x); //pasamos null si es nuevo
            Stage stage = new Stage();
            stage.setTitle(x == null ? "Nuevo pasajero" : "Editar pasajero");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Pasajero procesado = controller.getPasajero();

                if (x == null) {
                    boolean result = pasajeroDao.insert(procesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts("Success", "Registro satisfactorio!!!", "CONFIRMATION");
                        alert.show();
                    } else {
                        Alert alert = alertsUtils.viewAlerts("Error", "Error al insertar!!!", "ERROR");
                        alert.show();
                    }
                } else {
                    boolean result = pasajeroDao.update(procesado);
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
