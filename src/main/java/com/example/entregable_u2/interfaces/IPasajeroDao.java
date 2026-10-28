package com.example.entregable_u2.interfaces;

import com.example.entregable_u2.model.Pasajero;

import java.util.List;

public interface IPasajeroDao {

    public boolean insert(Pasajero p);
    public boolean update(Pasajero p);
    public boolean delete(Pasajero p);
    public Pasajero searchById(int id);
    public Pasajero searchByDni(String dni);
    public List<Pasajero> listAll();

}
