package com.example.entregable_u2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.entregable_u2.model.Pasajero;
import com.example.entregable_u2.utils.AlertsUtils;

public class PasajeroFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNombres, txtApellidos, txtDni, txtEdad, txtTelefono, txtDireccion;

    @FXML
    private DatePicker dpFechaNacimiento;

    private Pasajero pasajero;
    private boolean saved = false;

    public void setPasajero(Pasajero p) {
        this.pasajero = p;
        if (p != null) {
            lblTitulo.setText("Editar pasajero");
            txtNombres.setText(p.getNombres());
            txtApellidos.setText(p.getApellidos());
            txtDni.setText(p.getDni());
            txtEdad.setText(String.valueOf(p.getEdad()));
            txtTelefono.setText(p.getTelefono());
            txtDireccion.setText(p.getDireccion());
            dpFechaNacimiento.setValue(p.getFechaNacimiento());
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    private void cerrar() {
        Stage stage = (Stage) txtNombres.getScene().getWindow();
        stage.close();
    }

    private boolean validated() {
        if (txtNombres.getText().isBlank() || txtApellidos.getText().isBlank() || txtDni.getText().isBlank()) {
            Alert alert = alertsUtils.viewAlerts("ERROR", "Nombres, apellidos y DNI son obligatorios", "WARNING");
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
            if (pasajero == null) pasajero = new Pasajero();
            pasajero.setNombres(txtNombres.getText().trim());
            pasajero.setApellidos(txtApellidos.getText().trim());
            pasajero.setDni(txtDni.getText().trim());
            pasajero.setEdad(Integer.parseInt(txtEdad.getText().trim()));
            pasajero.setTelefono(txtTelefono.getText().trim());
            pasajero.setDireccion(txtDireccion.getText().trim());
            pasajero.setFechaNacimiento(dpFechaNacimiento.getValue());
            saved = true;
            cerrar();
        }
    }
}
