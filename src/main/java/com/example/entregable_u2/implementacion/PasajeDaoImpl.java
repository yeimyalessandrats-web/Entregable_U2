package com.example.entregable_u2.implementacion;

import com.example.entregable_u2.interfaces.IPasajeDao;
import com.example.entregable_u2.model.Pasaje;
import com.example.entregable_u2.model.Pasajero;
import com.example.entregable_u2.model.Viaje;
import com.example.entregable_u2.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PasajeDaoImpl implements IPasajeDao {

    @Override
    public boolean insert(Pasaje p) {
        boolean flag = false;
        String query = " INSERT INTO pasaje(pasajeroId,viajeId,costo) VALUES(?,?,?); ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {
            st.setInt(1, p.getPasajero().getIdpasajero());
            st.setInt(2, p.getViaje().getIdviaje());
            st.setDouble(3, p.getCosto());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar pasaje" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Pasaje p) {
        boolean flag = false;
        String query = " UPDATE pasaje SET pasajeroId=?,viajeId=?,costo=? WHERE idpasaje=? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {
            st.setInt(1, p.getPasajero().getIdpasajero());
            st.setInt(2, p.getViaje().getIdviaje());
            st.setDouble(3, p.getCosto());
            st.setInt(4, p.getIdpasaje());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar pasaje" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Pasaje p) {
        boolean flag = false;
        String query = "DELETE FROM pasaje WHERE idpasaje=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {
            st.setInt(1, p.getIdpasaje());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pasaje" + e.getMessage());
        }
        return flag;
    }

    @Override
    public Pasaje searchById(int id) {
        Pasaje p = null;
        // El pasaje llama al pasajero y al viaje
        String query = " SELECT p.*, pa.nombres AS pa_nombres, pa.apellidos AS pa_apellidos, pa.dni AS pa_dni, " +
                " vi.lugarOrigen, vi.lugarDestino, vi.fecha_salida, vi.fecha_llegada " +
                " FROM pasaje p, pasajero pa, viaje vi " +
                " WHERE p.pasajeroId = pa.idpasajero " +
                " AND p.viajeId = vi.idviaje " +
                " AND p.idpasaje = ? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Pasajero pa = new Pasajero();
                    Viaje vi = new Viaje();
                    p = new Pasaje();
                    pa.setIdpasajero(rs.getInt("pasajeroId"));
                    pa.setNombres(rs.getString("pa_nombres"));
                    pa.setApellidos(rs.getString("pa_apellidos"));
                    pa.setDni(rs.getString("pa_dni"));
                    vi.setIdviaje(rs.getInt("viajeId"));
                    vi.setLugarOrigen(rs.getString("lugarOrigen"));
                    vi.setLugarDestino(rs.getString("lugarDestino"));
                    vi.setFechaSalida(LocalDate.parse(rs.getString("fecha_salida")));
                    vi.setFechaLlegada(LocalDate.parse(rs.getString("fecha_llegada")));
                    p.setIdpasaje(rs.getInt("idpasaje"));
                    p.setCosto(rs.getDouble("costo"));
                    p.setPasajero(pa);
                    p.setViaje(vi);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar pasaje" + e.getMessage());
        }
        return p;
    }

    @Override
    public List<Pasaje> listAll() {
        List<Pasaje> lista = new ArrayList<>();
        // El pasaje llama al pasajero y al viaje
        String query = " SELECT p.*, pa.nombres AS pa_nombres, pa.apellidos AS pa_apellidos, pa.dni AS pa_dni, " +
                " vi.lugarOrigen, vi.lugarDestino, vi.fecha_salida, vi.fecha_llegada " +
                " FROM pasaje p, pasajero pa, viaje vi " +
                " WHERE p.pasajeroId = pa.idpasajero " +
                " AND p.viajeId = vi.idviaje ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Pasajero pa = new Pasajero();
                Viaje vi = new Viaje();
                Pasaje p = new Pasaje();
                pa.setIdpasajero(rs.getInt("pasajeroId"));
                pa.setNombres(rs.getString("pa_nombres"));
                pa.setApellidos(rs.getString("pa_apellidos"));
                pa.setDni(rs.getString("pa_dni"));
                vi.setIdviaje(rs.getInt("viajeId"));
                vi.setLugarOrigen(rs.getString("lugarOrigen"));
                vi.setLugarDestino(rs.getString("lugarDestino"));
                vi.setFechaSalida(LocalDate.parse(rs.getString("fecha_salida")));
                vi.setFechaLlegada(LocalDate.parse(rs.getString("fecha_llegada")));
                p.setIdpasaje(rs.getInt("idpasaje"));
                p.setCosto(rs.getDouble("costo"));
                p.setPasajero(pa);
                p.setViaje(vi);
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pasaje" + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean validatedCapacity(Pasaje p) {
        boolean lleno = false;
        // pasajes ya vendidos del viaje (sin contar el que se esta editando) vs capacidad del vehiculo
        String query = " SELECT (SELECT COUNT(*) FROM pasaje WHERE viajeId = ? AND idpasaje <> ?) AS vendidos, " +
                " ve.capacidad FROM viaje vi, vehiculo ve " +
                " WHERE vi.vehiculoId = ve.idvehiculo AND vi.idviaje = ? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
        ) {
            st.setInt(1, p.getViaje().getIdviaje());
            st.setInt(2, p.getIdpasaje());
            st.setInt(3, p.getViaje().getIdviaje());
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    lleno = rs.getInt("vendidos") >= rs.getInt("capacidad");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al validar la capacidad del viaje" + e.getMessage());
        }
        return lleno;
    }
}