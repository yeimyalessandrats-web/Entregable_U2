package com.example.entregable_u2.model;

import javafx.beans.property.*;

public class Pasaje {
    private final IntegerProperty idpasaje = new SimpleIntegerProperty();
    private final DoubleProperty costo = new SimpleDoubleProperty();
    private final ObjectProperty<Pasajero> pasajero = new SimpleObjectProperty<>();
    private final ObjectProperty<Viaje> viaje = new SimpleObjectProperty<>();

    public Pasaje() {
    }

    public Pasaje(int idpasaje, double costo, Pasajero pasajero, Viaje viaje) {
        setIdpasaje(idpasaje);
        setCosto(costo);
        setPasajero(pasajero);
        setViaje(viaje);
    }

    public final int getIdpasaje() {
        return idpasaje.get();
    }

    public final void setIdpasaje(int value) {
        idpasaje.set(value);
    }

    public final IntegerProperty idpasajeProperty() {
        return idpasaje;
    }

    public final double getCosto() {
        return costo.get();
    }

    public final void setCosto(double value) {
        costo.set(value);
    }

    public final DoubleProperty costoProperty() {
        return costo;
    }

    public final Pasajero getPasajero() {
        return pasajero.get();
    }

    public final void setPasajero(Pasajero value) {
        pasajero.set(value);
    }

    public final ObjectProperty<Pasajero> pasajeroProperty() {
        return pasajero;
    }

    public final Viaje getViaje() {
        return viaje.get();
    }

    public final void setViaje(Viaje value) {
        viaje.set(value);
    }

    public final ObjectProperty<Viaje> viajeProperty() {
        return viaje;
    }

}
