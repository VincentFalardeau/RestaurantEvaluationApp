package com.example.falardeauvincentaileoucuisse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection
{
    private static DbConnection instance = null;

    public Connection connection;

    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:BdRestaurants");
    }

    public static DbConnection getConnection() throws ClassNotFoundException, SQLException {
        if (instance == null)
        {
            instance = new DbConnection();
        }
        return instance;
    }
}
