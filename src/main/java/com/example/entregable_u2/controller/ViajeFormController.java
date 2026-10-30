package com.example.entregable_u2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.entregable_u2.implementacion.ConductorDaoImpl;
import com.example.entregable_u2.implementacion.VehiculoDaoImpl;
import com.example.entregable_u2.model.Conductor;
import com.example.entregable_u2.model.Vehiculo;
import com.example.entregable_u2.model.Viaje;
import com.example.entregable_u2.utils.AlertsUtils;

public class ViajeFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtOrigen, txtDestino;

    @FXML
    private DatePicker dpSalida, dpLlegada;

    @FXML
    private ComboBox<Conductor> cbConductor;
    @FXML
    private ComboBox<Vehiculo> cbVehiculo;

    private Viaje viaje;
    private boolean saved = false;

    @FXML
    public void initialize() {
        cbConductor.getItems().addAll(new ConductorDaoImpl().listAll());
        cbVehiculo.getItems().addAll(new VehiculoDaoImpl().listAll());
    }

    public void setViaje(Viaje v) {
        this.viaje = v;
        if (v != null) {
            lblTitulo.setText("Editar viaje");
            txtOrigen.setText(v.getLugarOrigen());
            txtDestino.setText(v.getLugarDestino());
            dpSalida.setValue(v.getFechaSalida());
            dpLlegada.setValue(v.getFechaLlegada());
            for (Conductor c : cbConductor.getItems()) {
                if (c.getIdconductor() == v.getConductor().getIdconductor()) {
                    cbConductor.setValue(c);
                }
            }
            for (Vehiculo ve : cbVehiculo.getItems()) {
                if (ve.getIdvehiculo() == v.getVehiculo().getIdvehiculo()) {
                    cbVehiculo.setValue(ve);
                }
            }
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public Viaje getViaje() {
        return viaje;
    }

    private void cerrar() {
        Stage stage = (Stage) txtOrigen.getScene().getWindow();
        stage.close();
    }

    private boolean validated() {
        if (txtOrigen.getText().isBlank() || txtDestino.getText().isBlank()
                || dpSalida.getValue() == null || dpLlegada.getValue() == null
                || cbConductor.getValue() == null || cbVehiculo.getValue() == null) {
            Alert alert = alertsUtils.viewAlerts("ERROR", "Todos los campos son obligatorios", "WARNING");
            alert.show();
            return false;
        }
        if (dpLlegada.getValue().isBefore(dpSalida.getValue())) {
            Alert alert = alertsUtils.viewAlerts("ERROR",
                    "La fecha de llegada no puede ser anterior a la de salida", "WARNING");
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
            if (viaje == null) viaje = new Viaje();
            viaje.setLugarOrigen(txtOrigen.getText().trim());
            viaje.setLugarDestino(txtDestino.getText().trim());
            viaje.setFechaSalida(dpSalida.getValue());
            viaje.setFechaLlegada(dpLlegada.getValue());
            viaje.setConductor(cbConductor.getValue());
            viaje.setVehiculo(cbVehiculo.getValue());
            saved = true;
            cerrar();
        }
    }
}
