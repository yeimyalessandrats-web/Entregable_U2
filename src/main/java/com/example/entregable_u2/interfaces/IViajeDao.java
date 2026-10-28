package com.example.entregable_u2.interfaces;

import com.example.entregable_u2.model.Viaje;

import java.util.List;

public interface IViajeDao {
    public boolean insert(Viaje v);
    public boolean update(Viaje v);
    public boolean delete(Viaje v);
    public Viaje searchById(int id);
    public List<Viaje> listAll();

}