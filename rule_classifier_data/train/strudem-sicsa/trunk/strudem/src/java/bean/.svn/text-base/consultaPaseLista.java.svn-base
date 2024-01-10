/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.consultaPaseListaDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author alumno
 */
public class consultaPaseLista extends ActionSupport {
 private int lista_asistencia;
    private int clase_id;
    private String materia;
    private String profesor;
    private String apellidoP;
    private String apellidoM;
    private String carrera;
    private int horasporsemana;
    private String grupo;
    private String periodo;
    private String unidadtematica;
    int cuatrimestre;
    private List asistencia = new ArrayList();
    private List listaalumno = new ArrayList();

     public int getLista_asistencia() {
        return lista_asistencia;
    }

    public void setLista_asistencia(int lista_asistencia) {
        this.lista_asistencia = lista_asistencia;
    }
    public int getClase_id() {
        return clase_id;
    }

    public void setClase_id(int clase_id) {
        this.clase_id = clase_id;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public List getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List asistencia) {
        this.asistencia = asistencia;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
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

    public List getListaalumno() {
        return listaalumno;
    }

    public void setListaalumno(List listaalumno) {
        this.listaalumno = listaalumno;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getUnidadtematica() {
        return unidadtematica;
    }

    public void setUnidadtematica(String unidadtematica) {
        this.unidadtematica = unidadtematica;
    }

    @Override
    public String execute() throws SQLException {
    
        consultaPaseListaDao materiaLista = new consultaPaseListaDao();
     // JOptionPane.showMessageDialog(null, getClase_id());
        setAsistencia(materiaLista.lista(getClase_id()));
        setListaalumno(materiaLista.consultaalumno(getClase_id()));
        if (getAsistencia().size() != 0 && getListaalumno().size() != 0) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public static void main(String[] args) throws SQLException {
        consultaPaseLista l = new consultaPaseLista();
       // l.setClase_id(66);
        l.execute();

    }
}


