package com.example.entregable_u2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.entregable_u2.model.Conductor;
import com.example.entregable_u2.utils.AlertsUtils;

public class ConductorFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNombres, txtApellidos, txtDni, txtEdad, txtTelefono, txtCategoria;

    private Conductor conductor;
    private boolean saved = false;

    public void setConductor(Conductor c) {
        this.conductor = c;
        if (c != null) {
            lblTitulo.setText("Editar conductor");
            txtNombres.setText(c.getNombres());
            txtApellidos.setText(c.getApellidos());
            txtDni.setText(c.getDni());
            txtEdad.setText(String.valueOf(c.getEdad()));
            txtTelefono.setText(c.getTelefono());
            txtCategoria.setText(c.getCategoriaLicencia());
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public Conductor getConductor() {
        return conductor;
    }

    private void cerrar() {
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        stage.close();
    }

    private boolean validated() {
        if (txtNombres.getText().isBlank() || txtApellidos.getText().isBlank()
                || txtDni.getText().isBlank() || txtCategoria.getText().isBlank()) {
            Alert alert = alertsUtils.viewAlerts("ERROR",
                    "Nombres, apellidos, DNI y categoria de licencia son obligatorios", "WARNING");
            alert.show();
            return false;
        }
        try {
            Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            Alert alert = alertsUtils.viewAlerts("ERROR", "La edad debe ser un numero entero", "WARNING");
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
            if (conductor == null) conductor = new Conductor();
            conductor.setNombres(txtNombres.getText().trim());
            conductor.setApellidos(txtApellidos.getText().trim());
            conductor.setDni(txtDni.getText().trim());
            conductor.setEdad(Integer.parseInt(txtEdad.getText().trim()));
            conductor.setTelefono(txtTelefono.getText().trim());
            conductor.setCategoriaLicencia(txtCategoria.getText().trim());
            saved = true;
            cerrar();
        }
    }
}
