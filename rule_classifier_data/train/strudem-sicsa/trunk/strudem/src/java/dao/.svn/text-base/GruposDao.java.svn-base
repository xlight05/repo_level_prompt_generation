/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.GruposBean;
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
public class GruposDao {

    public List consultaGrupos() {
        List lista = new ArrayList();

        Connection conn;
        try {
            conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT grupo_id,grado,grupo FROM grupo;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                GruposBean B = new GruposBean();
                B.setGrupo_id(rs.getInt("grupo_id"));
                B.setGrado(rs.getInt("grado"));
                B.setGrupo(rs.getString("grupo"));


                lista.add(B);
                }
                conn.close();
                ps.close();
                rs.close();
              
            
        } catch (SQLException ex) {
            Logger.getLogger(GruposDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;

    }
}
