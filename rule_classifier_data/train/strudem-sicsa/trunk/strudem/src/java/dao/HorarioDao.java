/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.horarioBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utilerias.ConexionSQL;

/**
 *
 * @author seter
 */
public class HorarioDao {

    public List horarioDocente() throws SQLException {
        List horario_docente = new ArrayList();
        Connection con = ConexionSQL.getConnection();
        for (int i = 1; i < 6; i++) {
            PreparedStatement ps = con.prepareStatement("  select  lista_asistencia.lista_asistencia_id, clase.clase_id,"
                    + " horas_clase.hora_inicio,horas_clase.hora_fin,dias.descripccion,"
                    + " aula.descripcion,edificio.descripcion as edificio,grupo.grupo,cuatrimestre.nivel,"
                    + " materia.nombre"
                    + " from horarios left join"
                    + " clase on(clase.horario_id=horarios.horarios_id and horarios.profesor_id=1 "
                    + " and dia_id=?)"
                    + " left join dias on(dias.dia_id=clase.dia_id)"
                    + " left join materia on(materia.materia_id=clase.materia_id)"
                    + " left join ubicacion on (ubicacion.ubicacion_id=clase.ubicacion_id)"
                    + " left join edificio on(edificio.edificio_id=ubicacion.edificio_id)"
                    + " left join aula on(aula.aula_id=ubicacion.aula_id)"
                    + " left join lista_asistencia on(materia.materia_id=lista_asistencia.materia_id)"
                    + "left join grupo on(grupo.grupo_id=clase.grupo_id)"
                    + " left join grupo_carrera on(grupo.grupo_id=grupo_carrera.grupo_id)"
                    + " left join cuatrimestre on(grupo_carrera.cuatrimestre_id=cuatrimestre.cuatrimestre_id)"
                    + "right join horas_clase on(horas_clase.horas_clase_id=clase.horas_clase_id)");

            ps.setInt(1, 1);
            ResultSet ces = ps.executeQuery();
            while (ces.next()) {
                horarioBean hb = new horarioBean();
                hb.setClasee_id(ces.getInt("clase_id"));
                hb.setLista_clase(ces.getInt("lista_asistencia_id"));
                hb.setHora_inicio(ces.getString("hora_inicio"));
                hb.setHora_fin(ces.getString("hora_fin"));
                hb.setDia(ces.getString("descripccion"));
                hb.setCuatromestre(ces.getString("nivel"));
                hb.setGrupo(ces.getString("grupo"));
                hb.setMaterria(ces.getString("nombre"));
                hb.setEdificio(ces.getString("descripcion"));
                hb.setAula(ces.getString("edificio"));
                horario_docente.add(hb);

                System.out.println("******************" + hb.getMaterria());
            }
        }/*
         * con.close(); ps.close(); ces.close();
         */
        return horario_docente;


    }

    public List horarioDocente2() throws SQLException {
        List horario_docente2 = new ArrayList();
        Connection con = ConexionSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("  select  lista_asistencia.lista_asistencia_id, clase.clase_id,"
                + " horas_clase.hora_inicio,horas_clase.hora_fin,dias.descripccion,"
                + " aula.descripcion,edificio.descripcion as edificio,grupo.grupo,cuatrimestre.nivel,"
                + " materia.nombre"
                + " from horarios left join"
                + " clase on(clase.horario_id=horarios.horarios_id and horarios.profesor_id=1 "
                + " and dia_id=?)"
                + " left join dias on(dias.dia_id=clase.dia_id)"
                + " left join materia on(materia.materia_id=clase.materia_id)"
                + " left join ubicacion on (ubicacion.ubicacion_id=clase.ubicacion_id)"
                + " left join edificio on(edificio.edificio_id=ubicacion.edificio_id)"
                + " left join aula on(aula.aula_id=ubicacion.aula_id)"
                + " left join lista_asistencia on(materia.materia_id=lista_asistencia.materia_id)"
                + "left join grupo on(grupo.grupo_id=clase.grupo_id)"
                + " left join grupo_carrera on(grupo.grupo_id=grupo_carrera.grupo_id)"
                + " left join cuatrimestre on(grupo_carrera.cuatrimestre_id=cuatrimestre.cuatrimestre_id)"
                + "right join horas_clase on(horas_clase.horas_clase_id=clase.horas_clase_id)");

        ps.setInt(1, 2);
        ResultSet ces = ps.executeQuery();
        while (ces.next()) {
            horarioBean hb = new horarioBean();
            hb.setClasee_id(ces.getInt("clase_id"));
            hb.setLista_clase(ces.getInt("lista_asistencia_id"));
            hb.setHora_inicio(ces.getString("hora_inicio"));
            hb.setHora_fin(ces.getString("hora_fin"));
            hb.setDia(ces.getString("descripccion"));
            hb.setCuatromestre(ces.getString("nivel"));
            hb.setGrupo(ces.getString("grupo"));
            hb.setMaterria(ces.getString("nombre"));
            hb.setEdificio(ces.getString("descripcion"));
            hb.setAula(ces.getString("edificio"));
            horario_docente2.add(hb);

            System.out.println("******************" + hb.getMaterria());
        }
        con.close();
        ps.close();
        ces.close();

        return horario_docente2;


    }

    public List horarioDocente3() throws SQLException {
        List horario_docente3 = new ArrayList();
        Connection con = ConexionSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("  select  lista_asistencia.lista_asistencia_id, clase.clase_id,"
                + " horas_clase.hora_inicio,horas_clase.hora_fin,dias.descripccion,"
                + " aula.descripcion,edificio.descripcion as edificio,grupo.grupo,cuatrimestre.nivel,"
                + " materia.nombre"
                + " from horarios left join"
                + " clase on(clase.horario_id=horarios.horarios_id and horarios.profesor_id=1 "
                + " and dia_id=?)"
                + " left join dias on(dias.dia_id=clase.dia_id)"
                + " left join materia on(materia.materia_id=clase.materia_id)"
                + " left join ubicacion on (ubicacion.ubicacion_id=clase.ubicacion_id)"
                + " left join edificio on(edificio.edificio_id=ubicacion.edificio_id)"
                + " left join aula on(aula.aula_id=ubicacion.aula_id)"
                + " left join lista_asistencia on(materia.materia_id=lista_asistencia.materia_id)"
                + "left join grupo on(grupo.grupo_id=clase.grupo_id)"
                + " left join grupo_carrera on(grupo.grupo_id=grupo_carrera.grupo_id)"
                + " left join cuatrimestre on(grupo_carrera.cuatrimestre_id=cuatrimestre.cuatrimestre_id)"
                + "right join horas_clase on(horas_clase.horas_clase_id=clase.horas_clase_id)");

        ps.setInt(1, 3);
        ResultSet ces = ps.executeQuery();
        while (ces.next()) {
            horarioBean hb = new horarioBean();
            hb.setClasee_id(ces.getInt("clase_id"));
            hb.setLista_clase(ces.getInt("lista_asistencia_id"));
            hb.setHora_inicio(ces.getString("hora_inicio"));
            hb.setHora_fin(ces.getString("hora_fin"));
            hb.setDia(ces.getString("descripccion"));
            hb.setCuatromestre(ces.getString("nivel"));
            hb.setGrupo(ces.getString("grupo"));
            hb.setMaterria(ces.getString("nombre"));
            hb.setEdificio(ces.getString("descripcion"));
            hb.setAula(ces.getString("edificio"));
            horario_docente3.add(hb);

            System.out.println("******************" + hb.getMaterria());
        }
        con.close();
        ps.close();
        ces.close();

        return horario_docente3;
    }

    public List horarioDocente4() throws SQLException {
        List horario_docente4 = new ArrayList();
        Connection con = ConexionSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("  select  lista_asistencia.lista_asistencia_id, clase.clase_id,"
                + " horas_clase.hora_inicio,horas_clase.hora_fin,dias.descripccion,"
                + " aula.descripcion,edificio.descripcion as edificio,grupo.grupo,cuatrimestre.nivel,"
                + " materia.nombre"
                + " from horarios left join"
                + " clase on(clase.horario_id=horarios.horarios_id and horarios.profesor_id=1 "
                + " and dia_id=?)"
                + " left join dias on(dias.dia_id=clase.dia_id)"
                + " left join materia on(materia.materia_id=clase.materia_id)"
                + " left join ubicacion on (ubicacion.ubicacion_id=clase.ubicacion_id)"
                + " left join edificio on(edificio.edificio_id=ubicacion.edificio_id)"
                + " left join aula on(aula.aula_id=ubicacion.aula_id)"
                + " left join lista_asistencia on(materia.materia_id=lista_asistencia.materia_id)"
                + "left join grupo on(grupo.grupo_id=clase.grupo_id)"
                + " left join grupo_carrera on(grupo.grupo_id=grupo_carrera.grupo_id)"
                + " left join cuatrimestre on(grupo_carrera.cuatrimestre_id=cuatrimestre.cuatrimestre_id)"
                + "right join horas_clase on(horas_clase.horas_clase_id=clase.horas_clase_id)");

        ps.setInt(1, 1);
        ResultSet ces = ps.executeQuery();
        while (ces.next()) {
            horarioBean hb = new horarioBean();
            hb.setClasee_id(ces.getInt("clase_id"));
            hb.setLista_clase(ces.getInt("lista_asistencia_id"));
            hb.setHora_inicio(ces.getString("hora_inicio"));
            hb.setHora_fin(ces.getString("hora_fin"));
            hb.setDia(ces.getString("descripccion"));
            hb.setCuatromestre(ces.getString("nivel"));
            hb.setGrupo(ces.getString("grupo"));
            hb.setMaterria(ces.getString("nombre"));
            hb.setEdificio(ces.getString("descripcion"));
            hb.setAula(ces.getString("edificio"));
            horario_docente4.add(hb);

            System.out.println("******************" + hb.getMaterria());
        }
        con.close();
        ps.close();
        ces.close();

        return horario_docente4;
    }

    public List horarioDocente5() throws SQLException {
        List horario_docente5 = new ArrayList();
        Connection con = ConexionSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("  select  lista_asistencia.lista_asistencia_id, clase.clase_id,"
                + " horas_clase.hora_inicio,horas_clase.hora_fin,dias.descripccion,"
                + " aula.descripcion,edificio.descripcion as edificio,grupo.grupo,cuatrimestre.nivel,"
                + " materia.nombre"
                + " from horarios left join"
                + " clase on(clase.horario_id=horarios.horarios_id and horarios.profesor_id=1 "
                + " and dia_id=?)"
                + " left join dias on(dias.dia_id=clase.dia_id)"
                + " left join materia on(materia.materia_id=clase.materia_id)"
                + " left join ubicacion on (ubicacion.ubicacion_id=clase.ubicacion_id)"
                + " left join edificio on(edificio.edificio_id=ubicacion.edificio_id)"
                + " left join aula on(aula.aula_id=ubicacion.aula_id)"
                + " left join lista_asistencia on(materia.materia_id=lista_asistencia.materia_id)"
                + " left join grupo on(grupo.grupo_id=clase.grupo_id)"
                + " left join grupo_carrera on(grupo.grupo_id=grupo_carrera.grupo_id)"
                + " left join cuatrimestre on(grupo_carrera.cuatrimestre_id=cuatrimestre.cuatrimestre_id)"
                + " right join horas_clase on(horas_clase.horas_clase_id=clase.horas_clase_id)");

        ps.setInt(1, 5);
        ResultSet ces = ps.executeQuery();
        while (ces.next()) {
            horarioBean hb = new horarioBean();
            hb.setClasee_id(ces.getInt("clase_id"));
            hb.setLista_clase(ces.getInt("lista_asistencia_id"));
            hb.setHora_inicio(ces.getString("hora_inicio"));
            hb.setHora_fin(ces.getString("hora_fin"));
            hb.setDia(ces.getString("descripccion"));
            hb.setCuatromestre(ces.getString("nivel"));
            hb.setGrupo(ces.getString("grupo"));
            hb.setMaterria(ces.getString("nombre"));
            hb.setEdificio(ces.getString("descripcion"));
            hb.setAula(ces.getString("edificio"));
            horario_docente5.add(hb);

            System.out.println("******************" + hb.getMaterria());
        }
        con.close();
        ps.close();
        ces.close();

        return horario_docente5;


    }
}
