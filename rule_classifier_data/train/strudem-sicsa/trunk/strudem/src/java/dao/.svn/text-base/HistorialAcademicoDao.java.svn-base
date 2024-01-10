/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class HistorialAcademicoDao {

    public List consultarTodo() {
        List lista = new ArrayList();
        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT historial_alumno.historial_alumno_id, historial_alumno.alumno_id, alumno.nombre, alumno.a_paterno, alumno.a_materno, historial_alumno.grupo_carrera_id, grupo.grado, grupo.grupo, carrera.nombre AS carrera, historial_alumno.periodo_id, periodo.descripcion AS periodo FROM historial_alumno INNER JOIN alumno ON historial_alumno.alumno_id = alumno.alumno_id INNER JOIN grupo_carrera ON historial_alumno.grupo_carrera_id = grupo_carrera.grupo_carrera_id INNER JOIN periodo ON historial_alumno.periodo_id = periodo.periodo_id INNER JOIN grupo ON grupo_carrera.grupo_id = grupo.grupo_id INNER JOIN carrera ON grupo_carrera.carrera_id = carrera.carrera_id;");
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

    public List consultarAlumno_Id(int clave) {
        List lista = new ArrayList();
        HistorialAcademicoBean B;

        try {
            Connection conn = ConexionSQL.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT historial_alumno.historial_alumno_id, historial_alumno.alumno_id, alumno.nombre, alumno.a_paterno, alumno.a_materno, historial_alumno.grupo_carrera_id, grupo.grado, grupo.grupo, carrera.nombre AS carrera, historial_alumno.periodo_id, periodo.descripcion AS periodo FROM historial_alumno INNER JOIN alumno ON historial_alumno.alumno_id = alumno.alumno_id INNER JOIN grupo_carrera ON historial_alumno.grupo_carrera_id = grupo_carrera.grupo_carrera_id INNER JOIN periodo ON historial_alumno.periodo_id = periodo.periodo_id INNER JOIN grupo ON grupo_carrera.grupo_id = grupo.grupo_id INNER JOIN carrera ON grupo_carrera.carrera_id = carrera.carrera_id WHERE historial_alumno.alumno_id = ?");
            ps.setInt(1, clave);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Recibiendo datos de historial academico de alumnos...");
                B = new HistorialAcademicoBean();
                B.setHistorial_alumno_id(rs.getInt("historial_alumno_id"));
                B.setNombre(rs.getString("nombre"));
                B.setA_paterno(rs.getString("a_paterno"));
                B.setA_materno(rs.getString("a_materno"));
                B.setAlumno_id(rs.getInt("alumno_id"));
                B.setGrupo_carrera_id(rs.getInt("grupo"));
                B.setGrado(rs.getInt("grado"));
                B.setGrupo(rs.getString("grupo"));
                B.setCarrera(rs.getString("carrera"));
//                B.setCuatrimestre_cuatrimestre_id(rs.getInt("cuatrimestre_id"));
//                B.setCuatrimestre(rs.getInt("cuatrimestre"));
                B.setPeriodo_id(rs.getInt("periodo_id"));
                B.setPeriodo(rs.getString("periodo"));
                lista.add(B);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistorialAcademicoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
