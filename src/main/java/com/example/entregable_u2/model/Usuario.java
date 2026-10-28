package com.example.entregable_u2.model;

import javafx.beans.property.*;

public class Usuario {
    private final IntegerProperty idusuario = new SimpleIntegerProperty();
    private final StringProperty user = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty tipoUser = new SimpleStringProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final StringProperty dni = new SimpleStringProperty();

    public Usuario() {
    }

    public Usuario(int idusuario, String user, String password, String tipoUser, String nombre, String apellidos, String dni) {
        setIdusuario(idusuario);
        setUser(user);
        setPassword(password);
        setTipoUser(tipoUser);
        setNombre(nombre);
        setApellidos(apellidos);
        setDni(dni);
    }

    public final int getIdusuario() {
        return idusuario.get();
    }

    public final void setIdusuario(int value) {
        idusuario.set(value);
    }

    public final IntegerProperty idusuarioProperty() {
        return idusuario;
    }

    public final String getUser() {
        return user.get();
    }

    public final void setUser(String value) {
        user.set(value);
    }

    public final StringProperty userProperty() {
        return user;
    }

    public final String getPassword() {
        return password.get();
    }

    public final void setPassword(String value) {
        password.set(value);
    }

    public final StringProperty passwordProperty() {
        return password;
    }

    public final String getTipoUser() {
        return tipoUser.get();
    }

    public final void setTipoUser(String value) {
        tipoUser.set(value);
    }

    public final StringProperty tipoUserProperty() {
        return tipoUser;
    }

    public final String getNombre() {
        return nombre.get();
    }

    public final void setNombre(String value) {
        nombre.set(value);
    }

    public final StringProperty nombreProperty() {
        return nombre;
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

}