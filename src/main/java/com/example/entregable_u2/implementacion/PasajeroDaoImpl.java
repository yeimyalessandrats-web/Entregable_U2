package com.example.entregable_u2.implementacion;

import com.example.entregable_u2.interfaces.IPasajeroDao;
import com.example.entregable_u2.model.Pasajero;
import com.example.entregable_u2.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PasajeroDaoImpl implements IPasajeroDao {

    @Override
    public boolean insert(Pasajero p) {
        boolean flag = false;
        String query = "INSERT INTO pasajero(nombres,apellidos,dni,edad,telefono,direccion,fechaNacimiento) " +
                "VALUES(?,?,?,?,?,?,?);";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setString(1, p.getNombres());
            st.setString(2, p.getApellidos());
            st.setString(3, p.getDni());
            st.setInt(4, p.getEdad());
            st.setString(5, p.getTelefono());
            st.setString(6, p.getDireccion());
            st.setString(7, p.getFechaNacimiento() == null ? null : p.getFechaNacimiento().toString());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar pasajero" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Pasajero p) {
        boolean flag = false;
        String query = " UPDATE pasajero SET nombres=?,apellidos=?,dni=?,edad=?,telefono=?,direccion=?," +
                " fechaNacimiento=? WHERE idpasajero=? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setString(1, p.getNombres());
            st.setString(2, p.getApellidos());
            st.setString(3, p.getDni());
            st.setInt(4, p.getEdad());
            st.setString(5, p.getTelefono());
            st.setString(6, p.getDireccion());
            st.setString(7, p.getFechaNacimiento() == null ? null : p.getFechaNacimiento().toString());
            st.setInt(8, p.getIdpasajero());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar pasajero" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Pasajero p) {
        boolean flag = false;
        String query = "DELETE FROM pasajero WHERE idpasajero=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setInt(1, p.getIdpasajero());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            // falla, por ejemplo, si el pasajero ya tiene pasajes registrados
            System.out.println("Error al eliminar pasajero" + e.getMessage());
        }
        return flag;
    }

    @Override
    public Pasajero searchById(int id) {
        Pasajero p = null;
        String query = "SELECT * FROM pasajero WHERE idpasajero=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    p = new Pasajero();
                    p.setIdpasajero(rs.getInt("idpasajero"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setDni(rs.getString("dni"));
                    p.setEdad(rs.getInt("edad"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setDireccion(rs.getString("direccion"));
                    String fecha = rs.getString("fechaNacimiento");
                    if (fecha != null && !fecha.isBlank()) {
                        p.setFechaNacimiento(LocalDate.parse(fecha));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar pasajero" + e.getMessage());
        }
        return p;
    }

    @Override
    public Pasajero searchByDni(String dni) {
        Pasajero p = null;
        String query = "SELECT * FROM pasajero WHERE dni=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setString(1, dni);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    p = new Pasajero();
                    p.setIdpasajero(rs.getInt("idpasajero"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setDni(rs.getString("dni"));
                    p.setEdad(rs.getInt("edad"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setDireccion(rs.getString("direccion"));
                    String fecha = rs.getString("fechaNacimiento");
                    if (fecha != null && !fecha.isBlank()) {
                        p.setFechaNacimiento(LocalDate.parse(fecha));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar pasajero por dni" + e.getMessage());
        }
        return p;
    }

    @Override
    public List<Pasajero> listAll() {
        List<Pasajero> lista = new ArrayList<>();
        String query = "SELECT * FROM pasajero;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Pasajero p = new Pasajero();
                p.setIdpasajero(rs.getInt("idpasajero"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDni(rs.getString("dni"));
                p.setEdad(rs.getInt("edad"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                String fecha = rs.getString("fechaNacimiento");
                if (fecha != null && !fecha.isBlank()) {
                    p.setFechaNacimiento(LocalDate.parse(fecha));
                }
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pasajeros" + e.getMessage());
        }
        return lista;
    }
}
