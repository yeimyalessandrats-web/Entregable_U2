package com.example.entregable_u2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        showHome();
    }

    @FXML
    private void showHome() {
        loadView("home-view.fxml");
    }

    @FXML
    private void showConductor() {
        loadView("conductor-view.fxml");
    }

    @FXML
    private void showPasajero() {
        loadView("pasajero-view.fxml");
    }

    @FXML
    private void showVehiculo() {
        loadView("vehiculo-view.fxml");
    }

    @FXML
    private void showViaje() {
        loadView("viaje-view.fxml");
    }

    @FXML
    private void showPasaje() {
        loadView("pasaje-view.fxml");
    }


    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/com/example/entregable_u2/views/" + fxml));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error de carga de la vista: " + fxml);
            e.printStackTrace();
        }
    }

}