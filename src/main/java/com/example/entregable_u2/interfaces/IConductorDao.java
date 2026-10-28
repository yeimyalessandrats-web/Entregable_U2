package com.example.entregable_u2.interfaces;

import com.example.entregable_u2.model.Conductor;
import java.util.List;

public interface IConductorDao {

    public boolean insert(Conductor c);
    public boolean update(Conductor c);
    public boolean delete(Conductor c);
    public Conductor searchById(int id);
    public Conductor searchByDni(String dni);
    public List<Conductor> listAll();

}
