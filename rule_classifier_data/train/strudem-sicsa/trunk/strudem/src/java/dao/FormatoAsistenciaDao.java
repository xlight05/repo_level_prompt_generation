/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.FormatoAsistencia;
import bean.HistorialAcademicoBean;
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
 * @author Mauricio
 */
public class FormatoAsistenciaDao {

    public List consultarHistorialAlumnoPorProfesor(int profesor_id) {
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT historial_alumno.historial_alumno_id, historial_alumno.alumno_id, alumno.nombre, alumno.a_paterno, alumno.a_materno, historial_alumno.grupo_carrera_id, grupo.grado, grupo.grupo, carrera.nombre AS carrera, historial_alumno.periodo_id, periodo.descripcion AS periodo, profesor.profesor_id, profesor.nombre + ' ' + profesor.a_paterno + ' ' + profesor.a_materno AS profesor "
                    + "FROM historial_alumno INNER JOIN alumno ON historial_alumno.alumno_id = alumno.alumno_id "
                    + "INNER JOIN grupo_carrera ON historial_alumno.grupo_carrera_id = grupo_carrera.grupo_carrera_id "
                    + "INNER JOIN periodo ON historial_alumno.periodo_id = periodo.periodo_id "
                    + "INNER JOIN grupo ON grupo_carrera.grupo_id = grupo.grupo_id "
                    + "INNER JOIN carrera ON grupo_carrera.carrera_id = carrera.carrera_id "
                    + "INNER JOIN grupo_profesor ON grupo.grupo_id = grupo_profesor.grupo_id "
                    + "INNER JOIN profesor ON grupo_profesor.profesor_id = profesor.profesor_id "
                    + "WHERE profesor.profesor_id= ?;");
            ps.setInt(1, profesor_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de historial academico de alumnos...");
                HistorialAcademicoBean B = new HistorialAcademicoBean();
                B.setHistorial_alumno_id(rs.getInt("historial_alumno_id"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));
                B.setAlumno_id(rs.getInt("alumno_id"));
                B.setGrupo_carrera_id(rs.getInt("grupo_carrera_id"));
                B.setGrado(rs.getInt("grado"));
                B.setGrupo(rs.getString("grupo"));
                B.setCarrera(rs.getString("carrera"));
                //B.setCuatrimestre_cuatrimestre_id(rs.getInt("cuatrimestre_cuatrimestre_id"));
                //B.setCuatrimestre(rs.getInt("cuatrimestre"));
                B.setPeriodo_id(rs.getInt("periodo_id"));
                B.setPeriodo(rs.getString("periodo"));
                lista.add(B);
            }

            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(HistorialAcademicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    public List consultarListaAsistencia(int lista_asistencia_id) {
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT lista_asistencia.periodo_id, lista_asistencia.grupo_id, lista_asistencia.profesor_id, carrera.nombre AS carrera, materia.nombre AS materia, profesor.nombre + ' ' + profesor.a_paterno + ' ' + profesor.a_materno AS profesor, periodo.descripcion AS periodo, grupo.grado, grupo.grupo, horas_por_semana  FROM lista_asistencia INNER JOIN carrera ON lista_asistencia.carrera_id = carrera.carrera_id INNER JOIN materia ON lista_asistencia.materia_id = materia.materia_id INNER JOIN profesor ON lista_asistencia.profesor_id = profesor.profesor_id INNER JOIN periodo ON lista_asistencia.periodo_id = periodo.periodo_id INNER JOIN grupo ON lista_asistencia.grupo_id = grupo.grupo_id WHERE lista_asistencia_id = ?");
            ps.setInt(1, lista_asistencia_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de encabezado de lista de asistencia...");
                FormatoAsistencia B = new FormatoAsistencia();
                B.setCarrera(rs.getString("carrera"));
                B.setMateria(rs.getString("materia"));
                B.setProfesor(rs.getString("profesor"));
                B.setPeriodo(rs.getString("periodo"));
                B.setCuatrimestre(rs.getInt("grado"));
                B.setGrupo(rs.getString("grupo"));
                B.setHoras_por_semana(rs.getInt("horas_por_semana"));
                B.setPeriodo_id(rs.getInt("periodo_id"));
                B.setGrupo_id(rs.getInt("grupo_id"));
                B.setProfesor_id(rs.getInt("profesor_id"));
                lista.add(B);
            }
            System.out.println("***se obtubo de encabezado: " + lista.size());
            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(HistorialAcademicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List consultarAlumnosHistorial(int periodo_id, int grupo_id, int profesor_id) {
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT historial_alumno.historial_alumno_id, historial_alumno.alumno_id, alumno.nombre, alumno.a_paterno, alumno.a_materno FROM historial_alumno INNER JOIN alumno ON historial_alumno.alumno_id = alumno.alumno_id INNER JOIN grupo_carrera ON historial_alumno.grupo_carrera_id = grupo_carrera.grupo_carrera_id INNER JOIN periodo ON historial_alumno.periodo_id = periodo.periodo_id AND periodo.periodo_id = ? INNER JOIN grupo ON grupo_carrera.grupo_id = grupo.grupo_id AND grupo.grupo_id = ? INNER JOIN carrera ON grupo_carrera.carrera_id = carrera.carrera_id INNER JOIN grupo_profesor ON grupo.grupo_id = grupo_profesor.grupo_id INNER JOIN profesor ON grupo_profesor.profesor_id = profesor.profesor_id WHERE profesor.profesor_id= ?;");
            ps.setInt(1, periodo_id);
            ps.setInt(2, grupo_id);
            ps.setInt(3, profesor_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de lista de alumnos con historial por profesor...");
                FormatoAsistencia B = new FormatoAsistencia();
                B.setAlumno_id(rs.getInt("alumno_id"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));
                lista.add(B);
            }
            conn.close();
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(HistorialAcademicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List diasDeClase() {
        List lista = new ArrayList();
        FormatoAsistencia bean = new FormatoAsistencia();
        for (int i = 0; i < 66; i++) {
            bean.setDia(i + 1);
            lista.add(bean);
        }

        return lista;
    }
}
