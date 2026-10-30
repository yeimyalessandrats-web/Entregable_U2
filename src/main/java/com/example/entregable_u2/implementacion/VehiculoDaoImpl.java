package com.example.entregable_u2.implementacion;

import com.example.entregable_u2.interfaces.IVehiculoDao;
import com.example.entregable_u2.model.Vehiculo;
import com.example.entregable_u2.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDaoImpl implements IVehiculoDao {

    @Override
    public boolean insert(Vehiculo v) {
        boolean flag = false;
        String query = "INSERT INTO vehiculo(descripcion,marca,capacidad) VALUES(?,?,?);";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, v.getDescripcion());
            st.setString(2, v.getMarca());
            st.setInt(3, v.getCapacidad());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar vehiculo:" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Vehiculo v) {
        boolean flag = false;
        String query = "UPDATE vehiculo SET descripcion=?,marca=?,capacidad=? WHERE idvehiculo=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, v.getDescripcion());
            st.setString(2, v.getMarca());
            st.setInt(3, v.getCapacidad());
            st.setInt(4, v.getIdvehiculo());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar vehiculo:" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Vehiculo v) {
        boolean flag = false;
        String query = "DELETE FROM vehiculo WHERE idvehiculo=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, v.getIdvehiculo());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            // falla, por ejemplo, si el vehiculo ya tiene viajes registrados
            System.out.println("Error al eliminar vehiculo:" + e.getMessage());
        }
        return flag;
    }

    @Override
    public Vehiculo searchById(int id) {
        Vehiculo v = null;
        String query = "SELECT * FROM vehiculo WHERE idvehiculo=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    v = new Vehiculo();
                    v.setIdvehiculo(rs.getInt("idvehiculo"));
                    v.setDescripcion(rs.getString("descripcion"));
                    v.setMarca(rs.getString("marca"));
                    v.setCapacidad(rs.getInt("capacidad"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar vehiculo:" + e.getMessage());
        }
        return v;
    }

    @Override
    public List<Vehiculo> listAll() {
        List<Vehiculo> lista = new ArrayList<>();
        String query = "SELECT * FROM vehiculo;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setIdvehiculo(rs.getInt("idvehiculo"));
                v.setDescripcion(rs.getString("descripcion"));
                v.setMarca(rs.getString("marca"));
                v.setCapacidad(rs.getInt("capacidad"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar vehiculo:" + e.getMessage());
        }
        return lista;
    }
}
