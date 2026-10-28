package com.example.entregable_u2.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Pasajero extends Persona {
    private final IntegerProperty idpasajero = new SimpleIntegerProperty();
    private final StringProperty direccion = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fechaNacimiento = new SimpleObjectProperty<>();

    public Pasajero() {
    }

    public Pasajero(String nombres, String apellidos, String dni, int edad, String telefono, int idpasajero, String direccion, LocalDate fechaNacimiento) {
        super(nombres, apellidos, dni, edad, telefono);
        setIdpasajero(idpasajero);
        setDireccion(direccion);
        setFechaNacimiento(fechaNacimiento);
    }

    public final int getIdpasajero() {
        return idpasajero.get();
    }

    public final void setIdpasajero(int value) {
        idpasajero.set(value);
    }

    public final IntegerProperty idpasajeroProperty() {
        return idpasajero;
    }

    public final String getDireccion() {
        return direccion.get();
    }

    public final void setDireccion(String value) {
        direccion.set(value);
    }

    public final StringProperty direccionProperty() {
        return direccion;
    }

    public final LocalDate getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public final void setFechaNacimiento(LocalDate value) {
        fechaNacimiento.set(value);
    }

    public final ObjectProperty<LocalDate> fechaNacimientoProperty() {
        return fechaNacimiento;
    }

    // Texto que se muestra en los ComboBox
    @Override
    public String toString() {
        return getNombres() + " " + getApellidos() + " - " + getDni();
    }
}
