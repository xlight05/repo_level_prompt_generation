/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio
 */
public class ConexionSQL {

    private static String ipaddress;
    private static String dbname;
    private static String username;
    private static String password;
    private static String service;
    private static ResourceBundle propiedades;
    private static Connection con = null;

    public static Connection getConnection() throws SQLException {


        if (propiedades == null) {
            propiedades = ResourceBundle.getBundle("utilerias.SQL");
            ipaddress = propiedades.getString("ip_address");
            dbname = propiedades.getString("dbname");
            username = propiedades.getString("username");
            password = propiedades.getString("password");
            service = propiedades.getString("service");
        }

        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://" + ipaddress + ":" + service + ";databaseName=" + dbname + ";integratedSecurity=false;";

            // Cargar Driver
            Class.forName(driver);
            // Realiza la conexión a la B.D.
            con = (Connection) DriverManager.getConnection(url, username, password);

            System.out.println("Conectsion etsitosa");
        } catch (Exception er) {
            System.out.println("Conectsion fallida");
            er.printStackTrace();
        }


        return con;
    }

    public static void main(String[] args) throws SQLException {
        Connection con = getConnection();
    }
}
