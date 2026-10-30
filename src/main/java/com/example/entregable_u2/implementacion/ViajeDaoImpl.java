package com.example.entregable_u2.implementacion;

import com.example.entregable_u2.interfaces.IViajeDao;
import com.example.entregable_u2.model.Conductor;
import com.example.entregable_u2.model.Vehiculo;
import com.example.entregable_u2.model.Viaje;
import com.example.entregable_u2.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViajeDaoImpl implements IViajeDao {

    @Override
    public boolean insert(Viaje v) {
        boolean flag = false;
        String query = " INSERT INTO viaje(conductorId,vehiculoId,lugarOrigen,lugarDestino, " +
                " fecha_salida,fecha_llegada) VALUES(?,?,?,?,?,?); ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {
            st.setInt(1, v.getConductor().getIdconductor());
            st.setInt(2, v.getVehiculo().getIdvehiculo());
            st.setString(3, v.getLugarOrigen());
            st.setString(4, v.getLugarDestino());
            st.setString(5, v.getFechaSalida().toString());
            st.setString(6, v.getFechaLlegada().toString());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar viaje" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean update(Viaje v) {
        boolean flag = false;
        String query = " UPDATE viaje SET conductorId=?,vehiculoId=?,lugarOrigen=?,lugarDestino=?, " +
                " fecha_salida=?,fecha_llegada=? WHERE idviaje=? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {
            st.setInt(1, v.getConductor().getIdconductor());
            st.setInt(2, v.getVehiculo().getIdvehiculo());
            st.setString(3, v.getLugarOrigen());
            st.setString(4, v.getLugarDestino());
            st.setString(5, v.getFechaSalida().toString());
            st.setString(6, v.getFechaLlegada().toString());
            st.setInt(7, v.getIdviaje());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar viaje" + e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(Viaje v) {
        boolean flag = false;
        String query = "DELETE FROM viaje WHERE idviaje=?;";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {
            st.setInt(1, v.getIdviaje());
            st.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            // falla, por ejemplo, si el viaje ya tiene pasajes vendidos
            System.out.println("Error al eliminar viaje" + e.getMessage());
        }
        return flag;
    }

    @Override
    public Viaje searchById(int id) {
        Viaje v = null;
        // El viaje llama al conductor y al vehiculo
        String query = " SELECT vi.*, c.nombres AS c_nombres, c.apellidos AS c_apellidos, c.dni AS c_dni, " +
                " ve.descripcion, ve.marca, ve.capacidad " +
                " FROM viaje vi, conductor c, vehiculo ve " +
                " WHERE vi.conductorId = c.idconductor " +
                " AND vi.vehiculoId = ve.idvehiculo " +
                " AND vi.idviaje = ? ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Conductor c = new Conductor();
                    Vehiculo ve = new Vehiculo();
                    v = new Viaje();
                    c.setIdconductor(rs.getInt("conductorId"));
                    c.setNombres(rs.getString("c_nombres"));
                    c.setApellidos(rs.getString("c_apellidos"));
                    c.setDni(rs.getString("c_dni"));
                    ve.setIdvehiculo(rs.getInt("vehiculoId"));
                    ve.setDescripcion(rs.getString("descripcion"));
                    ve.setMarca(rs.getString("marca"));
                    ve.setCapacidad(rs.getInt("capacidad"));
                    v.setIdviaje(rs.getInt("idviaje"));
                    v.setLugarOrigen(rs.getString("lugarOrigen"));
                    v.setLugarDestino(rs.getString("lugarDestino"));
                    v.setFechaSalida(LocalDate.parse(rs.getString("fecha_salida")));
                    v.setFechaLlegada(LocalDate.parse(rs.getString("fecha_llegada")));
                    v.setConductor(c);
                    v.setVehiculo(ve);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar viaje" + e.getMessage());
        }
        return v;
    }

    @Override
    public List<Viaje> listAll() {
        List<Viaje> lista = new ArrayList<>();
        // El viaje llama al conductor y al vehiculo
        String query = " SELECT vi.*, c.nombres AS c_nombres, c.apellidos AS c_apellidos, c.dni AS c_dni, " +
                " ve.descripcion, ve.marca, ve.capacidad " +
                " FROM viaje vi, conductor c, vehiculo ve " +
                " WHERE vi.conductorId = c.idconductor " +
                " AND vi.vehiculoId = ve.idvehiculo ";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Conductor c = new Conductor();
                Vehiculo ve = new Vehiculo();
                Viaje v = new Viaje();
                c.setIdconductor(rs.getInt("conductorId"));
                c.setNombres(rs.getString("c_nombres"));
                c.setApellidos(rs.getString("c_apellidos"));
                c.setDni(rs.getString("c_dni"));
                ve.setIdvehiculo(rs.getInt("vehiculoId"));
                ve.setDescripcion(rs.getString("descripcion"));
                ve.setMarca(rs.getString("marca"));
                ve.setCapacidad(rs.getInt("capacidad"));
                v.setIdviaje(rs.getInt("idviaje"));
                v.setLugarOrigen(rs.getString("lugarOrigen"));
                v.setLugarDestino(rs.getString("lugarDestino"));
                v.setFechaSalida(LocalDate.parse(rs.getString("fecha_salida")));
                v.setFechaLlegada(LocalDate.parse(rs.getString("fecha_llegada")));
                v.setConductor(c);
                v.setVehiculo(ve);
                lista.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar viaje" + e.getMessage());
        }
        return lista;
    }
}

