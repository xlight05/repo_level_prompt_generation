/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import bean.GrupoProfesorBean;
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
public class GrupoProfesorDao {
    
    public List grupoProfesorLista(){
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("select profesor.nombre,grupo.grado,grupo.grupo from profesor "
                    + "inner join horarios on profesor.profesor_id=horarios.profesor_id "
                    + "inner join clase on horarios.horarios_id=clase.horario_id "
                    + "inner join grupo on clase.grupo_id=grupo.grupo_id where profesor.profesor_id=1");
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GrupoProfesorBean B = new GrupoProfesorBean();
                B.setGrupo_id(rs.getInt("grupo_id"));
                B.setGrado(rs.getInt("grado"));
                B.setGrupo(rs.getString("grupo"));

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
