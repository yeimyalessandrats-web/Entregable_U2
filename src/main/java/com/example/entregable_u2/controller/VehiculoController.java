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

import com.example.entregable_u2.implementacion.VehiculoDaoImpl;
import com.example.entregable_u2.interfaces.IVehiculoDao;
import com.example.entregable_u2.model.Vehiculo;
import com.example.entregable_u2.utils.AlertsUtils;

import java.util.Optional;

public class VehiculoController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Vehiculo> tableVehiculo;
    @FXML
    private TableColumn<Vehiculo, Number> ColNumero;
    @FXML
    private TableColumn<Vehiculo, Integer> ColId;
    @FXML
    private TableColumn<Vehiculo, String> ColDescripcion;
    @FXML
    private TableColumn<Vehiculo, String> ColMarca;
    @FXML
    private TableColumn<Vehiculo, Integer> ColCapacidad;

    private final IVehiculoDao vehiculoDao = new VehiculoDaoImpl();
    private ObservableList<Vehiculo> listaVehiculo;

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
                cellData -> cellData.getValue().idvehiculoProperty().asObject());
        ColDescripcion.setCellValueFactory(
                cellData -> cellData.getValue().descripcionProperty());
        ColMarca.setCellValueFactory(
                cellData -> cellData.getValue().marcaProperty());
        ColCapacidad.setCellValueFactory(
                cellData -> cellData.getValue().capacidadProperty().asObject());
        //cargar datos iniciales
        cargarDatos();
    }

    private void cargarDatos() {
        listaVehiculo = FXCollections.observableArrayList(vehiculoDao.listAll());
        tableVehiculo.setItems(listaVehiculo);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Vehiculo seleccionado = tableVehiculo.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Vehiculo completo = vehiculoDao.searchById(seleccionado.getIdvehiculo());
            showForm(completo);
        } else {
            Alert alert = alertsUtils.viewAlerts("Seleccion requerida",
                    "Seleccione un registro de la tabla", "WARNING");
            alert.show();
        }
    }

    @FXML
    private void handleDelete() {
        Vehiculo seleccionado = tableVehiculo.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = alertsUtils.viewAlerts("Confirmar Eliminacion",
                    "Estas seguro de eliminar este registro?", "CONFIRMATION");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (vehiculoDao.delete(seleccionado)) {
                    listaVehiculo.remove(seleccionado);
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

    private void showForm(Vehiculo x) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class
                    .getResource("/com/example/entregable_u2/views/vehiculo-form.fxml"));
            Parent root = loader.load();
            VehiculoFormController controller = loader.getController();
            controller.setVehiculo(x); //pasamos null si es nuevo
            Stage stage = new Stage();
            stage.setTitle(x == null ? "Nuevo vehiculo" : "Editar vehiculo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Vehiculo procesado = controller.getVehiculo();

                if (x == null) {
                    boolean result = vehiculoDao.insert(procesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts("Success", "Registro satisfactorio!!!", "CONFIRMATION");
                        alert.show();
                    } else {
                        Alert alert = alertsUtils.viewAlerts("Error", "Error al insertar!!!", "ERROR");
                        alert.show();
                    }
                } else {
                    boolean result = vehiculoDao.update(procesado);
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
