package com.example.entregable_u2.test;

import com.example.entregable_u2.implementacion.VehiculoDaoImpl;
import com.example.entregable_u2.interfaces.IVehiculoDao;
import com.example.entregable_u2.model.Vehiculo;

import java.util.List;

public class TestVehiculo {
    static IVehiculoDao dao = new VehiculoDaoImpl();

    public void insertar() {
        Vehiculo v = new Vehiculo();
        v.setDescripcion("bus");
        v.setMarca("Volvo");
        v.setCapacidad(40);
        boolean result = dao.insert(v);
        System.out.println(result ? "Success" : "Error");
    }

    public void listar() {
        List<Vehiculo> list = dao.listAll();
        if (list != null && !list.isEmpty()) {
            for (Vehiculo v : list) {
                System.out.println("ID_VEHICULO:" + v.getIdvehiculo());
                System.out.println("TIPO:" + v.getDescripcion());
                System.out.println("MARCA:" + v.getMarca());
                System.out.println("CAPACIDAD:" + v.getCapacidad());
            }
        } else {
            System.out.println("no hay registros");
        }
    }

    public static void main(String[] args) {
        TestVehiculo t = new TestVehiculo();
        System.out.println("*********");
        // t.insertar();
        t.listar();
    }
}

