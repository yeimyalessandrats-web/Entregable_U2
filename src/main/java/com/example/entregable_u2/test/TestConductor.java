package com.example.entregable_u2.test;

import com.example.entregable_u2.implementacion.ConductorDaoImpl;
import com.example.entregable_u2.interfaces.IConductorDao;
import com.example.entregable_u2.model.Conductor;

import java.util.List;

public class TestConductor {
    static IConductorDao dao = new ConductorDaoImpl();

    public void insertar() {
        Conductor c = new Conductor();
        c.setNombres("Carlos");
        c.setApellidos("Ramirez Soto");
        c.setDni("99999999");
        c.setEdad(35);
        c.setTelefono("987654321");
        c.setCategoriaLicencia("A-IIIb");
        boolean result = dao.insert(c);
        if (result) {
            System.out.println("Success insert");
        } else {
            System.out.println("Error insert");
        }
    }

    public void update() {
        Conductor c = dao.searchByDni("99999999");
        if (c == null) {
            System.out.println("Error update: no existe el registro de prueba");
            return;
        }
        c.setNombres("Carlos actualizado");
        c.setApellidos("Ramirez actualizado");
        c.setEdad(36);
        c.setTelefono("912345678");
        c.setCategoriaLicencia("A-IIIc");
        boolean result = dao.update(c);
        if (result) {
            System.out.println("Success update");
        } else {
            System.out.println("Error update");
        }
    }

    public void delete() {
        Conductor c = dao.searchByDni("99999999");
        if (c == null) {
            System.out.println("Error delete: no existe el registro de prueba");
            return;
        }
        boolean result = dao.delete(c);
        if (result) {
            System.out.println("Success delete");
        } else {
            System.out.println("Error delete");
        }
    }

    public void listar() {
        List<Conductor> list = dao.listAll();
        if (list != null && !list.isEmpty()) {
            for (Conductor c : list) {
                System.out.println("ID_CONDUCTOR:" + c.getIdconductor());
                System.out.println("NOMBRES:" + c.getNombres());
                System.out.println("APELLIDOS:" + c.getApellidos());
                System.out.println("DNI:" + c.getDni());
                System.out.println("EDAD:" + c.getEdad());
                System.out.println("TELEFONO:" + c.getTelefono());
                System.out.println("CATEGORIA LICENCIA:" + c.getCategoriaLicencia());
            }
        } else {
            System.out.println("no hay registros");
        }
    }

    public void searchById() {
        Conductor prueba = dao.searchByDni("99999999");
        if (prueba == null) {
            System.out.println("no hay registros");
            return;
        }
        Conductor c = dao.searchById(prueba.getIdconductor());
        if (c != null) {
            System.out.println("ID_CONDUCTOR:" + c.getIdconductor());
            System.out.println("NOMBRES:" + c.getNombres());
            System.out.println("APELLIDOS:" + c.getApellidos());
            System.out.println("DNI:" + c.getDni());
            System.out.println("EDAD:" + c.getEdad());
            System.out.println("TELEFONO:" + c.getTelefono());
            System.out.println("CATEGORIA LICENCIA:" + c.getCategoriaLicencia());
        } else {
            System.out.println("no hay registros");
        }
    }

    public void searchByDni() {
        Conductor c = dao.searchByDni("99999999");
        if (c != null) {
            System.out.println("ID_CONDUCTOR:" + c.getIdconductor());
            System.out.println("NOMBRES:" + c.getNombres());
            System.out.println("APELLIDOS:" + c.getApellidos());
            System.out.println("DNI:" + c.getDni());
            System.out.println("EDAD:" + c.getEdad());
            System.out.println("TELEFONO:" + c.getTelefono());
            System.out.println("CATEGORIA LICENCIA:" + c.getCategoriaLicencia());
        } else {
            System.out.println("no hay registros");
        }
    }

    public static void main(String[] args) {
        TestConductor t = new TestConductor();
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
        // El registro de prueba se elimina al final para no dejar datos de prueba.
        // Si quieres conservarlo, comenta las dos lineas siguientes.
        System.out.println("***** ELIMINAR *****");
        t.delete();
        t.searchByDni();
    }
}
