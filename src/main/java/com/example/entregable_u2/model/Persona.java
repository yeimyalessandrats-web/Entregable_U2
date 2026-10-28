package com.example.entregable_u2.model;

import javafx.beans.property.*;

public class Persona {
    private final StringProperty nombres = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final StringProperty dni = new SimpleStringProperty();
    private final IntegerProperty edad = new SimpleIntegerProperty();
    private final StringProperty telefono = new SimpleStringProperty();

    public Persona() {
    }

    public Persona(String nombres, String apellidos, String dni, int edad, String telefono) {
        setNombres(nombres);
        setApellidos(apellidos);
        setDni(dni);
        setEdad(edad);
        setTelefono(telefono);
    }

    public final String getNombres() {
        return nombres.get();
    }

    public final void setNombres(String value) {
        nombres.set(value);
    }

    public final StringProperty nombresProperty() {
        return nombres;
    }

    public final String getApellidos() {
        return apellidos.get();
    }

    public final void setApellidos(String value) {
        apellidos.set(value);
    }

    public final StringProperty apellidosProperty() {
        return apellidos;
    }

    public final String getDni() {
        return dni.get();
    }

    public final void setDni(String value) {
        dni.set(value);
    }

    public final StringProperty dniProperty() {
        return dni;
    }

    public final int getEdad() {
        return edad.get();
    }

    public final void setEdad(int value) {
        edad.set(value);
    }

    public final IntegerProperty edadProperty() {
        return edad;
    }

    public final String getTelefono() {
        return telefono.get();
    }

    public final void setTelefono(String value) {
        telefono.set(value);
    }

    public final StringProperty telefonoProperty() {
        return telefono;
    }

}