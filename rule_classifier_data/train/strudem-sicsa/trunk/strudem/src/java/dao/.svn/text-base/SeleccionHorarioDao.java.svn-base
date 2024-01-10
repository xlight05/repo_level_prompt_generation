/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import bean.SeleccionHorarioBean;
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
public class SeleccionHorarioDao {
    
    public List seleccionHorario(){
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT profesor_id, nombre, a_paterno, a_materno FROM profesor;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                SeleccionHorarioBean B = new SeleccionHorarioBean();
                B.setProfesor_id(rs.getInt("profesor_id"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));


                lista.add(B);
            }

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
