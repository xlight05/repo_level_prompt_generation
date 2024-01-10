/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import bean.GrupoProfesorListaBean;
import bean.UsuarioBean;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts2.interceptor.SessionAware;
import utilerias.ConexionSQL;

/**
 *
 * @author Gilberto Tenorio
 */
public class GrupoProfesorListaDao implements SessionAware{
    private Map session;
    
    public List consultaGrupoProfesor(){
        List lista = new ArrayList();
        UsuarioBean usuario;
        try {
            usuario = (UsuarioBean) session.get("usuario");
            
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("select profesor.nombre,grupo.grado,grupo.grupo from profesor "
                    + "inner join horarios on profesor.profesor_id=horarios.profesor_id "
                    + "inner join clase on horarios.horarios_id=clase.horario_id "
                    + "inner join grupo on clase.grupo_id=grupo.grupo_id where profesor.profesor_id="+usuario.getUsuario_id());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                GrupoProfesorListaBean B = new GrupoProfesorListaBean();
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

    @Override
    public void setSession(Map<String, Object> map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
