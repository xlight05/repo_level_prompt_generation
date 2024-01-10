/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.UnidadesBean;
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
public class UnidadesDao {
    
    
    public List consultaUnidades() {
    List lista = new ArrayList();

        Connection conn;
        try {
            conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT unidades_id,fecha_inicio,parcial,materia_id FROM unidades;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                UnidadesBean B = new UnidadesBean();
                B.setUnidades_id(rs.getInt("unidades_id"));
                B.setFecha_inicio(rs.getString("fecha_inicio"));
                B.setParcial(rs.getInt("parcial"));
                B.setMateria_id(rs.getInt("materia_id"));
              


                lista.add(B);
                }
                conn.close();
                ps.close();
                rs.close();
              
            
        } catch (SQLException ex) {
            Logger.getLogger(UnidadesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;

    }
    
}
