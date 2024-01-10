/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.prj.dao;

import java.sql.*;

/**
 * 
 * @author Gilberto Hiragi
 */
public class ConexaoMySQL {

    private static Connection instance   = null;
    private static final String DRIVER   = "com.mysql.jdbc.Driver";
    private static final String SERVIDOR = "localhost";
    private static final String PORTA    = "3306";
    private static final String SCHEMA   = "bd_prj_fac";
    private static final String USUARIO  = "root";
    private static final String SENHA    = "";

    public synchronized static Connection getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (instance == null) {
            instance = conectar();
        }
        return instance;
    }

    private static Connection conectar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName(DRIVER).newInstance();
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://"
                + SERVIDOR + ":" + PORTA + "/" + SCHEMA, USUARIO, SENHA);
        return con;
    }

}
