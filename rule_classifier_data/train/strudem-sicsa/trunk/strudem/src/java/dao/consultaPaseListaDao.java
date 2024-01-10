/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import bean.alummnoBeanPaseLista;
import bean.consultaPaseLista;
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
 * @author alumno
 */
public class consultaPaseListaDao {

    public List lista(int clase) throws SQLException {
        List lista = new ArrayList();
        System.out.println("---------------------------" + clase);
       
        Connection conn = ConexionSQL.getConnection();
        PreparedStatement ps = conn.prepareStatement("select clase.clase_id,periodo.descripcion as periodo,lista_asistencia.lista_asistencia_id,carrera.nombre,materia.nombre as asignatura,periodo.descripcion,profesor.nombre as docente,grupo.grupo,grupo.grado,lista_asistencia.horas_por_semana from carrera inner join lista_asistencia on (lista_asistencia.carrera_id=carrera.carrera_id)inner join materia  on(materia.materia_id=lista_asistencia.materia_id)inner join periodo  on (periodo.periodo_id=lista_asistencia.periodo_id)inner join profesor  on(profesor.profesor_id=lista_asistencia.profesor_id)inner join grupo  on(grupo.grupo_id=lista_asistencia.grupo_id)inner join  grupo_carrera on(grupo_carrera.grupo_id=grupo.grupo_id)inner join  clase on(materia.materia_id=clase.materia_id)   where     clase.clase_id=?");
        ps.setInt(1, clase);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             consultaPaseLista B = new consultaPaseLista();
            paseListaAsitenciaDao u=new paseListaAsitenciaDao();
            B.setClase_id(rs.getInt("clase_id"));
            B.setCarrera(rs.getString("nombre"));
            B.setMateria(rs.getString("asignatura"));
            B.setProfesor(rs.getString("docente"));
            B.setPeriodo(rs.getString("periodo"));
            B.setCuatrimestre(rs.getInt("grado"));
            B.setHorasporsemana(rs.getInt("horas_por_semana"));
            B.setGrupo(rs.getString("grupo"));
            // B.setRevision(rs.getString("revision"));
             // B.setUnidadtematica(rs.getString("unidadtematica"));
            //  B.setDias(rs.getInt("dias"));
            B.setLista_asistencia(rs.getInt("lista_asistencia_id"));
           // B.setUnidadtematica(rs.getString("unidadtematica"));
            System.out.println("-*-*-"+B.getLista_asistencia());

         u.consultaunidad(B.getLista_asistencia(),B.getLista_asistencia());
            lista.add(B);
        }
        rs.close();
        ps.close();

        return lista;
    }

    public List consultaalumno(int eclase) {
        List listaalumno = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("select lista_asistencia.lista_asistencia_id,historial_alumno.historial_alumno_id,  alumno.alumno_id, alumno.nombre,alumno.a_paterno,alumno.a_materno from alumno inner join historial_alumno on(alumno.alumno_id=historial_alumno.alumno_id)inner join grupo_carrera on(grupo_carrera.grupo_carrera_id=historial_alumno.grupo_carrera_id)inner join grupo on(grupo.grupo_id=grupo_carrera.grupo_id)inner join clase on(clase.grupo_id=grupo.grupo_id)inner join materia on(materia.materia_id=clase.materia_id)inner join lista_asistencia on(lista_asistencia.materia_id=materia.materia_id) where clase.clase_id=?");
            ps.setInt(1, eclase);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("ntrando");
                alummnoBeanPaseLista B = new alummnoBeanPaseLista();
                B.setAlumno_id(rs.getInt("alumno_id"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));
                B.setHistorialalumno(rs.getInt("historial_alumno_id"));
                B.setListaasistencia(rs.getInt("lista_asistencia_id"));
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

    public static void main(String[] args) throws SQLException {
        consultaPaseListaDao o=new consultaPaseListaDao();
       ///o.lista(2);
    }
}
