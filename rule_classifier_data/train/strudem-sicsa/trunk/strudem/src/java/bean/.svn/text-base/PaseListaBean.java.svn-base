/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.ListadoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author seter
 */
public class PaseListaBean extends ActionSupport {

    private int l_id;
    private String carerra;
    private String asignatura;
    private String docente;
    private String periodo;
    private String cuatrimestre;
    private String grupo;
    private int horasporsemana;
    private String revision;
    private String mes;
    private String unidadtematica;
    private int dias;
    private int lista_clase;
    private int clasee_id;
    private List asistencia = new ArrayList();
    private List listaalumno = new ArrayList();

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getCarerra() {
        return carerra;
    }

    public void setCarerra(String carerra) {
        this.carerra = carerra;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(String cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getHorasporsemana() {
        return horasporsemana;
    }

    public void setHorasporsemana(int horasporsemana) {
        this.horasporsemana = horasporsemana;
    }

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getUnidadtematica() {
        return unidadtematica;
    }

    public void setUnidadtematica(String unidadtematica) {
        this.unidadtematica = unidadtematica;
    }

    public List getListaalumno() {
        return listaalumno;
    }

    public void setListaalumno(List listaalumno) {
        this.listaalumno = listaalumno;
    }

    public List getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public String execute() throws SQLException {

        ListadoDao alumno = new ListadoDao();
        setAsistencia(alumno.lista(getClasee_id(), getLista_clase()));
        //JOptionPane.showMessageDialog(null, getAsistencia().size());
        consultas();
        if (!getAsistencia().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public int getClasee_id() {
        return clasee_id;
    }

    public void setClasee_id(int clasee_id) {
        this.clasee_id = clasee_id;
    }

    public int getLista_clase() {
        return lista_clase;
    }

    public void setLista_clase(int lista_clase) {
        this.lista_clase = lista_clase;
    }

    public String consultas() {

        ListadoDao alumno = new ListadoDao();
        //JOptionPane.showMessageDialog(null, getClasee_id() + "---" + getLista_clase());
        setListaalumno(alumno.sesin(getClasee_id(), getLista_clase()));
        //JOptionPane.showMessageDialog(null, getListaalumno().size());
        if (!getListaalumno().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
