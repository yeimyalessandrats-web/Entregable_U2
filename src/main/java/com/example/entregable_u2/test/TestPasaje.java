package com.example.entregable_u2.test;

import com.example.entregable_u2.implementacion.PasajeDaoImpl;
import com.example.entregable_u2.interfaces.IPasajeDao;
import com.example.entregable_u2.model.Pasaje;
import com.example.entregable_u2.model.Pasajero;
import com.example.entregable_u2.model.Viaje;

import java.util.List;

public class TestPasaje {

    static IPasajeDao dao = new PasajeDaoImpl();

    public void insert() {
        Pasaje p = new Pasaje();
        Pasajero pa = new Pasajero();
        Viaje v = new Viaje();

        pa.setIdpasajero(1);
        p.setPasajero(pa);

        v.setIdviaje(1);
        p.setViaje(v);

        p.setCosto(80);
        boolean result = dao.insert(p);
        System.out.println(result ? "Pasaje success" : "Pasaje error");
    }

    public void list() {
        List<Pasaje> lista = dao.listAll();
        if (lista != null && !lista.isEmpty()) {
            for (Pasaje p : lista) {
                System.out.println("ID_PASAJE:" + p.getIdpasaje());
                System.out.println("PASAJERO:" + p.getPasajero().getNombres());
                System.out.println("VIAJE:" + p.getViaje().getLugarOrigen()
                        + " -> " + p.getViaje().getLugarDestino());
                System.out.println("COSTO:" + p.getCosto());
            }
        } else {
            System.out.println("no hay registros");
        }
    }

    public void validateCapacidad() {
        Pasaje p = new Pasaje();
        Viaje v = new Viaje();
        v.setIdviaje(1);
        p.setViaje(v);
        boolean lleno = dao.validatedCapacity(p);
        System.out.println(lleno ? "viaje lleno" : "hay asientos disponibles");
    }

    public static void main(String[] args) {
        TestPasaje t = new TestPasaje();
        // t.insert();
        // t.list();
        t.validateCapacidad();
    }
}
