package com.example.entregable_u2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.entregable_u2.implementacion.PasajeroDaoImpl;
import com.example.entregable_u2.implementacion.ViajeDaoImpl;
import com.example.entregable_u2.model.Pasaje;
import com.example.entregable_u2.model.Pasajero;
import com.example.entregable_u2.model.Viaje;
import com.example.entregable_u2.utils.AlertsUtils;

public class PasajeFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private Label lblTitulo;

    // El pasaje llama al pasajero y al viaje
    @FXML
    private ComboBox<Pasajero> cbPasajero;
    @FXML
    private ComboBox<Viaje> cbViaje;

    @FXML
    private TextField txtCosto;

    private Pasaje pasaje;
    private boolean saved = false;

    @FXML
    public void initialize() {
        cbPasajero.getItems().addAll(new PasajeroDaoImpl().listAll());
        cbViaje.getItems().addAll(new ViajeDaoImpl().listAll());
    }

    public void setPasaje(Pasaje p) {
        this.pasaje = p;
        if (p != null) {
            lblTitulo.setText("Editar pasaje");
            txtCosto.setText(String.valueOf(p.getCosto()));
            for (Pasajero pa : cbPasajero.getItems()) {
                if (pa.getIdpasajero() == p.getPasajero().getIdpasajero()) {
                    cbPasajero.setValue(pa);
                }
            }
            for (Viaje v : cbViaje.getItems()) {
                if (v.getIdviaje() == p.getViaje().getIdviaje()) {
                    cbViaje.setValue(v);
                }
            }
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public Pasaje getPasaje() {
        return pasaje;
    }

    private void cerrar() {
        Stage stage = (Stage) txtCosto.getScene().getWindow();
        stage.close();
    }

    private boolean validated() {
        if (cbPasajero.getValue() == null || cbViaje.getValue() == null) {
            Alert alert = alertsUtils.viewAlerts("ERROR", "Seleccione el pasajero y el viaje", "WARNING");
            alert.show();
            return false;
        }
        try {
            double costo = Double.parseDouble(txtCosto.getText().trim());
            if (costo < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = alertsUtils.viewAlerts("ERROR", "El costo debe ser un numero valido", "WARNING");
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
            if (pasaje == null) pasaje = new Pasaje();
            pasaje.setPasajero(cbPasajero.getValue());
            pasaje.setViaje(cbViaje.getValue());
            pasaje.setCosto(Double.parseDouble(txtCosto.getText().trim()));
            saved = true;
            cerrar();
        }
    }
}

