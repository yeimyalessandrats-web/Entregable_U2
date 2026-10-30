package com.example.entregable_u2.test;

import com.example.entregable_u2.implementacion.PasajeroDaoImpl;
import com.example.entregable_u2.interfaces.IPasajeroDao;
import com.example.entregable_u2.model.Pasajero;

import java.time.LocalDate;
import java.util.List;

public class TestPasajero {
    static IPasajeroDao dao = new PasajeroDaoImpl();

    public void insertar() {
        Pasajero p = new Pasajero();
        p.setNombres("Ana");
        p.setApellidos("Torres Quispe");
        p.setDni("88888888");
        p.setEdad(28);
        p.setTelefono("956123456");
        p.setDireccion("Av. Lima 123");
        p.setFechaNacimiento(LocalDate.parse("1998-05-14"));
        boolean result = dao.insert(p);
        if (result) {
            System.out.println("Success insert");
        } else {
            System.out.println("Error insert");
        }
    }

    public void update() {
        Pasajero p = dao.searchByDni("88888888");
        if (p == null) {
            System.out.println("Error update: no existe el registro de prueba");
            return;
        }
        p.setNombres("Ana actualizada");
        p.setApellidos("Torres actualizada");
        p.setEdad(29);
        p.setTelefono("900111222");
        p.setDireccion("Jr. Cusco 456");
        p.setFechaNacimiento(LocalDate.parse("1997-05-14"));
        boolean result = dao.update(p);
        if (result) {
            System.out.println("Success update");
        } else {
            System.out.println("Error update");
        }
    }

    public void delete() {
        Pasajero p = dao.searchByDni("88888888");
        if (p == null) {
            System.out.println("Error delete: no existe el registro de prueba");
            return;
        }
        boolean result = dao.delete(p);
        if (result) {
            System.out.println("Success delete");
        } else {
            System.out.println("Error delete");
        }
    }

    public void listar() {
        List<Pasajero> list = dao.listAll();
        if (list != null && !list.isEmpty()) {
            for (Pasajero p : list) {
                System.out.println("ID_PASAJERO:" + p.getIdpasajero());
                System.out.println("NOMBRES:" + p.getNombres());
                System.out.println("APELLIDOS:" + p.getApellidos());
                System.out.println("DNI:" + p.getDni());
                System.out.println("EDAD:" + p.getEdad());
                System.out.println("TELEFONO:" + p.getTelefono());
                System.out.println("DIRECCION:" + p.getDireccion());
                System.out.println("FECHA NACIMIENTO:" + p.getFechaNacimiento());
            }
        } else {
            System.out.println("no hay registros");
        }
    }

    public void searchById() {
        Pasajero prueba = dao.searchByDni("88888888");
        if (prueba == null) {
            System.out.println("no hay registros");
            return;
        }
        Pasajero p = dao.searchById(prueba.getIdpasajero());
        if (p != null) {
            System.out.println("ID_PASAJERO:" + p.getIdpasajero());
            System.out.println("NOMBRES:" + p.getNombres());
            System.out.println("APELLIDOS:" + p.getApellidos());
            System.out.println("DNI:" + p.getDni());
            System.out.println("EDAD:" + p.getEdad());
            System.out.println("TELEFONO:" + p.getTelefono());
            System.out.println("DIRECCION:" + p.getDireccion());
            System.out.println("FECHA NACIMIENTO:" + p.getFechaNacimiento());
        } else {
            System.out.println("no hay registros");
        }
    }

    public void searchByDni() {
        Pasajero p = dao.searchByDni("88888888");
        if (p != null) {
            System.out.println("ID_PASAJERO:" + p.getIdpasajero());
            System.out.println("NOMBRES:" + p.getNombres());
            System.out.println("APELLIDOS:" + p.getApellidos());
            System.out.println("DNI:" + p.getDni());
            System.out.println("EDAD:" + p.getEdad());
            System.out.println("TELEFONO:" + p.getTelefono());
            System.out.println("DIRECCION:" + p.getDireccion());
            System.out.println("FECHA NACIMIENTO:" + p.getFechaNacimiento());
        } else {
            System.out.println("no hay registros");
        }
    }

    public static void main(String[] args) {
        TestPasajero t = new TestPasajero();
        System.out.println("***** INSERTAR *****");
        t.insertar();
        System.out.println("***** BUSCAR POR DNI *****");
        t.searchByDni();
        System.out.println("***** BUSCAR POR ID *****");
        t.searchById();
        System.out.println("***** ACTUALIZAR *****");
        t.update();
        t.searchByDni();
        System.out.println("***** LISTAR *****");
        t.listar();
        System.out.println("***** ELIMINAR *****");
        t.delete();
        t.searchByDni();
    }
}
