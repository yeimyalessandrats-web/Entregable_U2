package com.example.entregable_u2.interfaces;

import com.example.entregable_u2.model.Vehiculo;

import java.util.List;

public interface IVehiculoDao {
    public boolean insert(Vehiculo v);

    public boolean update(Vehiculo v);

    public boolean delete(Vehiculo v);

    public Vehiculo searchById(int id);

    public List<Vehiculo> listAll();
}