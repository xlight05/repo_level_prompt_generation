/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.LoginBean;
import bean.MiHorarioBean;
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
public class HorariosTodosDao {
    
    
    
    

    public List horariosTodosLista(int id) {
        List lista = new ArrayList();
        
            try {
                Connection conn = ConexionSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement("select clase.clase_id,clase.nombre,clase.horario_id,clase.ubicacion_id,aula.descripcion as aula,edificio.descripcion as edificio, clase.materia_id,clase.grupo_id,grupo.grado,grupo.grupo,hora_clase.hora_inicio,hora_clase.hora_fin,dias_semana.dia from clase inner join ubicacion on ubicacion.ubicacion_id = clase.ubicacion_id inner join aula on aula.aula_id=ubicacion.aula_id inner join edificio on edificio.edificio_id=ubicacion.edificio_id inner join grupo on grupo.grupo_id=clase.grupo_id inner join hora_clase on clase.hora_clase_id=hora_clase.hora_clase_id inner join dias_semana on clase.dias_semana_id=dias_semana.dias_semana_id where horario_id=?;");
                ps.setInt(1, id);
                
                ResultSet rs = ps.executeQuery();
                

                while (rs.next()) {

                    MiHorarioBean B = new MiHorarioBean();
                    B.setClase_id(rs.getInt("clase_id"));
                    B.setNombre(rs.getString("nombre"));
                    B.setHorario_id(rs.getInt("horario_id"));
                    B.setUbicacion_id(rs.getInt("ubicacion_id"));
                    B.setAula(rs.getString("aula"));
                    B.setEdificio(rs.getString("edificio"));
                    B.setMateria_id(rs.getInt("materia_id"));
                    B.setGrupo_id(rs.getInt("grupo_id"));
                    B.setGrado(rs.getInt("grado"));
                    B.setGrupo(rs.getString("grupo"));
                    B.setHora_inicio(rs.getString("hora_inicio"));
                    B.setHora_fin(rs.getString("hora_fin"));
                    B.setDia(rs.getString("dia"));


                    lista.add(B);
                }
                System.out.println("La lista tiene: "+lista.size());
                
                conn.close();
                ps.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
        return lista;
    }
    
    public List profesoresLista(int id) {
        List lista2 = new ArrayList();
        
        
            try {
                Connection conn = ConexionSQL.getConnection();
                PreparedStatement ps = conn.prepareStatement("select profesor_id,nombre,a_paterno,a_materno from profesor where profesor_id=?;");
                
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                

                while (rs.next()) {

                    MiHorarioBean B = new MiHorarioBean();
                    B.setProfesor_id(rs.getInt("profesor_id"));
                    B.setNombreProfesor(rs.getString("nombre"));
                    B.setA_paterno(rs.getString("a_paterno"));
                    B.setA_materno(rs.getString("a_materno"));
                    


                    lista2.add(B);
                }
                
                
                conn.close();
                ps.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
        return lista2;
    }
}
