package com.example.entregable_u2.implementacion;

import com.example.entregable_u2.interfaces.IConductorDao;
import com.example.entregable_u2.model.Conductor;
import com.example.entregable_u2.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConductorDaoImpl implements IConductorDao {

    private Conductor mapear(ResultSet rs) throws SQLException {
        Conductor c = new Conductor();
        c.setIdconductor(rs.getInt("idconductor"));
        c.setNombres(rs.getString("nombres"));
        c.setApellidos(rs.getString("apellidos"));
        c.setDni(rs.getString("dni"));
        c.setEdad(rs.getInt("edad"));
        c.setTelefono(rs.getString("telefono"));
        c.setCategoriaLicencia(rs.getString("categoriaLicencia"));
        return c;
    }

    @Override
    public boolean insert(Conductor c) {
        boolean flag = false;
        String query = "INSERT INTO conductor(nombres,apellidos,dni,edad,telefono,categoriaLicencia) " +
                "VALUES(?,?,?,?,?,?);";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setString(1, c.getNombres());
            st.setString(2, c.getApellidos());
            st.setString(3, c.getDni());
            st.setInt(4, c.getEdad());
            st.setString(5, c.getTelefono());
            st.setString(6, c.getCategoriaLicencia());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar conductor" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Conductor c) {
        boolean flag = false;
        String query = " UPDATE conductor SET nombres=?,apellidos=?,dni=?,edad=?,telefono=?,categoriaLicencia=? " +
                " WHERE idconductor=? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setString(1, c.getNombres());
            st.setString(2, c.getApellidos());
            st.setString(3, c.getDni());
            st.setInt(4, c.getEdad());
            st.setString(5, c.getTelefono());
            st.setString(6, c.getCategoriaLicencia());
            st.setInt(7, c.getIdconductor());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar conductor" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Conductor c) {
        boolean flag = false;
        String query = "DELETE FROM conductor WHERE idconductor=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setInt(1, c.getIdconductor());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            // falla, por ejemplo, si el conductor ya tiene viajes registrados
            System.out.println("Error al eliminar conductor" + e.getMessage());
        }
        return flag;
    }

    @Override
    public Conductor searchById(int id) {
        Conductor c = null;
        String query = "SELECT * FROM conductor WHERE idconductor=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    c = mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar conductor" + e.getMessage());
        }
        return c;
    }

    @Override
    public Conductor searchByDni(String dni) {
        Conductor c = null;
        String query = "SELECT * FROM conductor WHERE dni=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, dni);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    c = mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar conductor por dni" + e.getMessage());
        }
        return c;
    }

    @Override
    public List<Conductor> listAll() {
        List<Conductor> lista = new ArrayList<>();
        String query = "SELECT * FROM conductor;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar conductores" + e.getMessage());
        }
        return lista;
    }
}