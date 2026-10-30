package com.example.entregable_u2.model;

import javafx.beans.property.*;

public class Vehiculo {
    private final IntegerProperty idvehiculo = new SimpleIntegerProperty();
    private final StringProperty descripcion = new SimpleStringProperty();
    private final StringProperty marca = new SimpleStringProperty();
    private final IntegerProperty capacidad = new SimpleIntegerProperty();

    public Vehiculo() {
    }

    public Vehiculo(int idvehiculo, String descripcion, String marca, int capacidad) {
        setIdvehiculo(idvehiculo);
        setDescripcion(descripcion);
        setMarca(marca);
        setCapacidad(capacidad);
    }

    public final int getIdvehiculo() {
        return idvehiculo.get();
    }

    public final void setIdvehiculo(int value) {
        idvehiculo.set(value);
    }

    public final IntegerProperty idvehiculoProperty() {
        return idvehiculo;
    }

    public final String getDescripcion() {
        return descripcion.get();
    }

    public final void setDescripcion(String value) {
        descripcion.set(value);
    }

    public final StringProperty descripcionProperty() {
        return descripcion;
    }

    public final String getMarca() {
        return marca.get();
    }

    public final void setMarca(String value) {
        marca.set(value);
    }

    public final StringProperty marcaProperty() {
        return marca;
    }

    public final int getCapacidad() {
        return capacidad.get();
    }

    public final void setCapacidad(int value) {
        capacidad.set(value);
    }

    public final IntegerProperty capacidadProperty() {
        return capacidad;
    }
    @Override
    public String toString() {
        return getDescripcion() + " " + getMarca() + " (" + getCapacidad() + " personas)";
    }
}