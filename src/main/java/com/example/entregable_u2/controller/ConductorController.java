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
import com.example.entregable_u2.implementacion.ConductorDaoImpl;
import com.example.entregable_u2.interfaces.IConductorDao;
import com.example.entregable_u2.model.Conductor;
import com.example.entregable_u2.utils.AlertsUtils;

import java.util.Optional;

public class ConductorController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Conductor> tableConductor;
    @FXML
    private TableColumn<Conductor, Number> ColNumero;
    @FXML
    private TableColumn<Conductor, Integer> ColId;
    @FXML
    private TableColumn<Conductor, String> ColNombres;
    @FXML
    private TableColumn<Conductor, String> ColApellidos;
    @FXML
    private TableColumn<Conductor, String> ColDni;
    @FXML
    private TableColumn<Conductor, Integer> ColEdad;
    @FXML
    private TableColumn<Conductor, String> ColTelefono;
    @FXML
    private TableColumn<Conductor, String> ColCategoria;

    private final IConductorDao conductorDao = new ConductorDaoImpl();
    private ObservableList<Conductor> listaConductor;

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
                cellData -> cellData.getValue().idconductorProperty().asObject());
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
        ColCategoria.setCellValueFactory(
                cellData -> cellData.getValue().categoriaLicenciaProperty());
        //cargar datos iniciales
        cargarDatos();
    }

    private void cargarDatos() {
        listaConductor = FXCollections.observableArrayList(conductorDao.listAll());
        tableConductor.setItems(listaConductor);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Conductor seleccionado = tableConductor.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Conductor completo = conductorDao.searchById(seleccionado.getIdconductor());
            showForm(completo);
        } else {
            Alert alert = alertsUtils.viewAlerts("Seleccion requerida",
                    "Seleccione un registro de la tabla", "WARNING");
            alert.show();
        }
    }

    @FXML
    private void handleDelete() {
        Conductor seleccionado = tableConductor.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirm = alertsUtils.viewAlerts("Confirmar Eliminacion",
                    "Estas seguro de eliminar este registro?", "CONFIRMATION");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (conductorDao.delete(seleccionado)) {
                    listaConductor.remove(seleccionado);
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

    private void showForm(Conductor x) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class
                    .getResource("/com/example/entregable_u2/views/conductor-form.fxml"));
            Parent root = loader.load();
            ConductorFormController controller = loader.getController();
            controller.setConductor(x); //pasamos null si es nuevo
            Stage stage = new Stage();
            stage.setTitle(x == null ? "Nuevo conductor" : "Editar conductor");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Conductor procesado = controller.getConductor();

                if (x == null) {
                    boolean result = conductorDao.insert(procesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts("Success", "Registro satisfactorio!!!", "CONFIRMATION");
                        alert.show();
                    } else {
                        Alert alert = alertsUtils.viewAlerts("Error", "Error al insertar!!!", "ERROR");
                        alert.show();
                    }
                } else {
                    boolean result = conductorDao.update(procesado);
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

