package com.example.entregable_u2.model;



import javafx.beans.property.*;

public class Conductor extends Persona {
    private final IntegerProperty idconductor = new SimpleIntegerProperty();
    private final StringProperty categoriaLicencia = new SimpleStringProperty();

    public Conductor() {
    }

    public Conductor(String nombres, String apellidos, String dni, int edad, String telefono, int idconductor, String categoriaLicencia) {
        super(nombres, apellidos, dni, edad, telefono);
        setIdconductor(idconductor);
        setCategoriaLicencia(categoriaLicencia);
    }

    public final int getIdconductor() {
        return idconductor.get();
    }

    public final void setIdconductor(int value) {
        idconductor.set(value);
    }

    public final IntegerProperty idconductorProperty() {
        return idconductor;
    }

    public final String getCategoriaLicencia() {
        return categoriaLicencia.get();
    }

    public final void setCategoriaLicencia(String value) {
        categoriaLicencia.set(value);
    }

    public final StringProperty categoriaLicenciaProperty() {
        return categoriaLicencia;
    }

    // Texto que se muestra en los ComboBox
    @Override
    public String toString() {
        return getNombres() + " " + getApellidos() + " - " + getDni();
    }
}