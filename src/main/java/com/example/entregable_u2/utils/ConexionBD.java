package com.example.entregable_u2.utils;

import java.sql.*;

public class ConexionBD {
    private static Connection connection;

    public static Connection getConnection(){
        try{
            if (connection == null || connection.isClosed()){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:C:/DB/Entregable_U2.db";
                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try(Statement st = connection.createStatement()){
                    st.execute("PRAGMA foreign_keys = ON;");
                }
                System.out.println("Conexion exitosa a SQLite");
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error de conexion a la BD: "+e.getMessage(), e);
        }
    }

}