package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Dominik on 2017-01-29.
 */
public class DbConnection {

    Connection conn;
    public Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/cookingbook", "root", "");
            System.out.println("Połączono");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Nie udalo sie nawiazac polaczenia2");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie udalo sie nawiazac polaczenia");
            return null;
        }

    }
}
