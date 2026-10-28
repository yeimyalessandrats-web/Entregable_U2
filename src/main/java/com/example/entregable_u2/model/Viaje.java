package com.example.entregable_u2.model;
import javafx.beans.property.*;
import java.time.LocalDate;
public class Viaje {
    private final IntegerProperty idviaje = new SimpleIntegerProperty();
    private final StringProperty lugarOrigen = new SimpleStringProperty();
    private final StringProperty lugarDestino = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fechaSalida = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> fechaLlegada = new SimpleObjectProperty<>();
    private final ObjectProperty<Conductor> conductor = new SimpleObjectProperty<>();
    private final ObjectProperty<Vehiculo> vehiculo = new SimpleObjectProperty<>();

    public Viaje() {
    }

    public Viaje(int idviaje, String lugarOrigen, String lugarDestino, LocalDate fechaSalida, LocalDate fechaLlegada, Conductor conductor, Vehiculo vehiculo) {
        setIdviaje(idviaje);
        setLugarOrigen(lugarOrigen);
        setLugarDestino(lugarDestino);
        setFechaSalida(fechaSalida);
        setFechaLlegada(fechaLlegada);
        setConductor(conductor);
        setVehiculo(vehiculo);
    }

    public final int getIdviaje() {
        return idviaje.get();
    }

    public final void setIdviaje(int value) {
        idviaje.set(value);
    }

    public final IntegerProperty idviajeProperty() {
        return idviaje;
    }

    public final String getLugarOrigen() {
        return lugarOrigen.get();
    }

    public final void setLugarOrigen(String value) {
        lugarOrigen.set(value);
    }

    public final StringProperty lugarOrigenProperty() {
        return lugarOrigen;
    }

    public final String getLugarDestino() {
        return lugarDestino.get();
    }

    public final void setLugarDestino(String value) {
        lugarDestino.set(value);
    }

    public final StringProperty lugarDestinoProperty() {
        return lugarDestino;
    }

    public final LocalDate getFechaSalida() {
        return fechaSalida.get();
    }

    public final void setFechaSalida(LocalDate value) {
        fechaSalida.set(value);
    }

    public final ObjectProperty<LocalDate> fechaSalidaProperty() {
        return fechaSalida;
    }

    public final LocalDate getFechaLlegada() {
        return fechaLlegada.get();
    }

    public final void setFechaLlegada(LocalDate value) {
        fechaLlegada.set(value);
    }

    public final ObjectProperty<LocalDate> fechaLlegadaProperty() {
        return fechaLlegada;
    }

    public final Conductor getConductor() {
        return conductor.get();
    }

    public final void setConductor(Conductor value) {
        conductor.set(value);
    }

    public final ObjectProperty<Conductor> conductorProperty() {
        return conductor;
    }

    public final Vehiculo getVehiculo() {
        return vehiculo.get();
    }

    public final void setVehiculo(Vehiculo value) {
        vehiculo.set(value);
    }

    public final ObjectProperty<Vehiculo> vehiculoProperty() {
        return vehiculo;
    }

    // Texto que se muestra en los ComboBox
    @Override
    public String toString() {
        return getLugarOrigen() + " -> " + getLugarDestino() + " (" + getFechaSalida() + ")";
    }
}

