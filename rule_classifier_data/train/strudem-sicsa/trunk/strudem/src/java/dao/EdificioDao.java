/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.EdificioBean;
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
 * @author Gilberto Tenorio
 */
public class EdificioDao {

    public List consultaEdificio() {
        List lista = new ArrayList();

        Connection conn;
        try {
            conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT edificio_id,descripcion FROM edificio;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                EdificioBean E = new EdificioBean();
                E.setEdificio_id(rs.getInt("edificio_id"));
                E.setDescripcion(rs.getString("descripcion"));
                

                lista.add(E);
                }
                conn.close();
                ps.close();
                rs.close();
              
            
        } catch (SQLException ex) {
            Logger.getLogger(EdificioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;

    }
}
