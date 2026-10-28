package com.example.entregable_u2.interfaces;

import com.example.entregable_u2.model.Pasaje;

import java.util.List;

public interface IPasajeDao {
    public boolean insert(Pasaje p);
    public boolean update(Pasaje p);
    public boolean delete(Pasaje p);
    public Pasaje searchById(int id);
    public List<Pasaje> listAll();

    // true si el viaje ya vendio todos los asientos del vehiculo (capacidad de personas)
    public boolean validatedCapacity(Pasaje p);

}
