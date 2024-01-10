/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.AlumnoBean;
import bean.PaseListaBean;
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
 * @author seter
 */
public class ListadoDao {

    public List lista(int clase, int listay) throws SQLException {
        List lista = new ArrayList();
        PaseListaBean B = new PaseListaBean();
        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("  select clase.clase_id, lista_asistencia.lista_asistencia_id,carrera.nombre,materia.nombre,periodo.descripcion,profesor.nombre ,grupo.grupo,cuatrimestre.nivel,lista_asistencia.horas_por_semana from carrera inner join lista_asistencia on (lista_asistencia.carrera_id=carrera.carrera_id)inner join materia  on(materia.materia_id=lista_asistencia.materia_id)inner join periodo  on (periodo.periodo_id=lista_asistencia.periodo_id)inner join profesor  on(profesor.profesor_id=lista_asistencia.profesor_id)inner join grupo  on(grupo.grupo_id=lista_asistencia.grupo_id)inner join  grupo_carrera on(grupo_carrera.grupo_id=grupo.grupo_id)inner join cuatrimestre on(cuatrimestre.cuatrimestre_id=grupo_carrera.cuatrimestre_id)inner join  clase on(materia.materia_id=clase.materia_id)   where     clase.clase_id=? and  lista_asistencia.lista_asistencia_id=?");
        ps.setInt(1, clase);
        ps.setInt(2, listay);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            B.setL_id(rs.getInt("clase_id"));
            B.setCarerra(rs.getString("nombre"));
            B.setAsignatura(rs.getString("nombre"));
            B.setDocente(rs.getString("nombre"));
            // B.setPeriodo(rs.getString("periodo"));
            B.setCuatrimestre(rs.getString("nivel"));
            B.setHorasporsemana(rs.getInt("horas_por_semana"));
            B.setGrupo(rs.getString("grupo"));
            // B.setRevision(rs.getString("revision"));
            //  B.setUnidadtematica(rs.getString("unidadtematica"));
            //  B.setDias(rs.getInt("dias"));
            lista.add(B);
        }
        rs.close();
        ps.close();

        return lista;
    }

    public List sesin(int eclase, int lista) {
        List listaalumno = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement(" select alumno.alumno_id, alumno.nombre,alumno.a_paterno, alumno.a_materno from alumno inner join historial_alumno  on(alumno.alumno_id=historial_alumno.alumno_id)inner join grupo_carrera on(historial_alumno.grupo_carrera_id=grupo_carrera.grupo_carrera_id)inner join grupo on(grupo.grupo_id=grupo_carrera.grupo_id)inner join lista_asistencia on(lista_asistencia.grupo_id=grupo.grupo_id)inner join periodo on(periodo.periodo_id=historial_alumno.periodo_id)inner join horarios on(horarios.periodo_id=periodo.periodo_id)inner join clase on(clase.horario_id=horarios.horarios_id)where   clase.clase_id=? and lista_asistencia.lista_asistencia_id=?");
            ps.setInt(1, eclase);
            ps.setInt(2, lista);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //System.out.println("ntrando");
                AlumnoBean B = new AlumnoBean();
                B.setAlumno_id(rs.getInt("alumno_id"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));

                listaalumno.add(B);
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaalumno;


    }

    public boolean asistencias(int alumnoh) throws SQLException {
        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("  insert into asistencia"
                + "(lista_asistencia_id,fecha,unidad_id,estado_id,historial_alumno_id)"
                + "values(8,'2012-08-07',1,1,?)");
        //  ps.setInt(1, estado);
        ps.setInt(1, alumnoh);
        if (ps.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws SQLException {
        ListadoDao L = new ListadoDao();
        L.asistencias(5);
    }
}
