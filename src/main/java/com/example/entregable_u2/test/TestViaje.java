package com.example.entregable_u2.test;

import com.example.entregable_u2.implementacion.ViajeDaoImpl;
import com.example.entregable_u2.interfaces.IViajeDao;
import com.example.entregable_u2.model.Conductor;
import com.example.entregable_u2.model.Vehiculo;
import com.example.entregable_u2.model.Viaje;

import java.time.LocalDate;
import java.util.List;

public class TestViaje {

    static IViajeDao dao = new ViajeDaoImpl();

    public void insert() {
        Viaje v = new Viaje();
        Conductor c = new Conductor();
        Vehiculo ve = new Vehiculo();

        c.setIdconductor(1);
        v.setConductor(c);

        ve.setIdvehiculo(1);
        v.setVehiculo(ve);

        v.setLugarOrigen("Lima");
        v.setLugarDestino("Juliaca");
        v.setFechaSalida(LocalDate.parse("2026-10-23"));
        v.setFechaLlegada(LocalDate.parse("2026-10-24"));
        boolean result = dao.insert(v);
        System.out.println(result ? "Viaje success" : "Viaje error");
    }

    public void list() {
        List<Viaje> lista = dao.listAll();
        if (lista != null && !lista.isEmpty()) {
            for (Viaje v : lista) {
                System.out.println("ID_VIAJE:" + v.getIdviaje());
                System.out.println("ORIGEN:" + v.getLugarOrigen());
                System.out.println("DESTINO:" + v.getLugarDestino());
                System.out.println("SALIDA:" + v.getFechaSalida());
                System.out.println("LLEGADA:" + v.getFechaLlegada());
                System.out.println("CONDUCTOR:" + v.getConductor().getNombres());
                System.out.println("VEHICULO:" + v.getVehiculo().getDescripcion()
                        + " " + v.getVehiculo().getMarca());
                System.out.println("CAPACIDAD:" + v.getVehiculo().getCapacidad());
            }
        } else {
            System.out.println("no hay registros");
        }
    }

    public static void main(String[] args) {
        TestViaje t = new TestViaje();
        // t.insert();
        t.list();
    }
}
