/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.CarreraBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConexionSQL;

/**
 *
 * @author TOSHIBA
 */
public class CarreraDao  {
    public List consultaCarrera() {
    List lista = new ArrayList();

        Connection conn;
        try {
            conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT carrera_id,nombre FROM carrera;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                CarreraBean B = new CarreraBean();
                B.setCarrera_id(rs.getInt("carrera_id"));
                B.setNombre(rs.getString("nombre"));
              


                lista.add(B);
                }
                conn.close();
                ps.close();
                rs.close();
              
            
        } catch (SQLException ex) {
            Logger.getLogger(CarreraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;

    }
}

