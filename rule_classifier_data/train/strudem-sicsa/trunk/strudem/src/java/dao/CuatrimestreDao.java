/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.CuatrimestreBean;
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
 * @author Mily
 */
public class CuatrimestreDao {
    
    public List consultaCuatrimestre() {
        List lista = new ArrayList();

        Connection conn;
        try {
            conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT cuatrimestre_id,nivel FROM cuatrimestre;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                CuatrimestreBean C = new CuatrimestreBean();
                C.setCuatrimestre_id(rs.getInt("cuatrimestre_id"));
                C.setNivel(rs.getInt("nivel"));
                

                lista.add(C);
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
