package com.example.entregable_u2.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.entregable_u2.model.Vehiculo;
import com.example.entregable_u2.utils.AlertsUtils;

public class VehiculoFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private Label lblTitulo;

    @FXML
    private ComboBox<String> cbDescripcion;

    @FXML
    private TextField txtMarca, txtCapacidad;

    private Vehiculo vehiculo;
    private boolean saved = false;

    @FXML
    public void initialize() {
        cbDescripcion.getItems().addAll("auto", "combi", "bus");
    }

    public void setVehiculo(Vehiculo v) {
        this.vehiculo = v;
        if (v != null) {
            lblTitulo.setText("Editar vehiculo");
            cbDescripcion.setValue(v.getDescripcion());
            txtMarca.setText(v.getMarca());
            txtCapacidad.setText(String.valueOf(v.getCapacidad()));
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void cerrar() {
        Stage stage = (Stage) txtMarca.getScene().getWindow();
        stage.close();
    }

    private boolean validated() {
        if (cbDescripcion.getValue() == null || txtMarca.getText().isBlank()) {
            Alert alert = alertsUtils.viewAlerts("ERROR", "Tipo y marca son obligatorios", "WARNING");
            alert.show();
            return false;
        }
        try {
            int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            if (capacidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = alertsUtils.viewAlerts("ERROR",
                    "La capacidad debe ser un numero entero mayor a 0", "WARNING");
            alert.show();
            return false;
        }
        return true;
    }

    @FXML
    private void handleCancel() {
        cerrar();
    }

    @FXML
    private void handleSaved() {
        if (validated()) {
            if (vehiculo == null) vehiculo = new Vehiculo();
            vehiculo.setDescripcion(cbDescripcion.getValue());
            vehiculo.setMarca(txtMarca.getText().trim());
            vehiculo.setCapacidad(Integer.parseInt(txtCapacidad.getText().trim()));
            saved = true;
            cerrar();
        }
    }
}

