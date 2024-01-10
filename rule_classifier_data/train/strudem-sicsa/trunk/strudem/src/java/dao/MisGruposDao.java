/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.AulaBean;
import bean.LoginBean;
import bean.MisGruposBean;
import bean.ProfesorBean;
import com.opensymphony.xwork2.ActionContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConexionSQL;

/**
 *
 * @author Gilberto Tenorio
 */
public class MisGruposDao {

    public List misGrupos() {
        Map session = ActionContext.getContext().getSession();
        LoginBean p = (LoginBean) session.get("datosUsuario");
        
        int id = p.getProfesor_id();
        List lista = new ArrayList();


        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("select grupo_profesor_id,grupo_profesor.grupo_id,grupo.grado,grupo.grupo,profesor.nombre as profesor,grupo_profesor.profesor_id FROM grupo_profesor inner join "
                    + "grupo on grupo_profesor.grupo_id=grupo.grupo_id "
                    + "inner join profesor on grupo_profesor.profesor_id=profesor.profesor_id where grupo_profesor.profesor_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MisGruposBean B = new MisGruposBean();
                B.setGrupo_profesor_id(rs.getInt("grupo_profesor_id"));
                B.setGrupo_id(rs.getInt("grupo_id"));
                B.setProfesor_id(rs.getInt("profesor_id"));
                B.setProfesor(rs.getString("profesor"));
                B.setGrupo(rs.getString("grupo"));
                B.setGrado(rs.getInt("grado"));


                lista.add(B);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
