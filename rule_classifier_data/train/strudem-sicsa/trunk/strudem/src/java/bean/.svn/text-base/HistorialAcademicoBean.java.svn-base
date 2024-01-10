/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.HistorialAcademicoDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio
 */
public class HistorialAcademicoBean extends ActionSupport{
    private int historial_alumno_id;
    private int alumno_id;
    private String nombre;
    private String a_paterno;
    private String a_materno;
    private int periodo_id;
    private String periodo;
    private int grupo_carrera_id;
    private int grado;
    private String grupo;
    private String carrera;
    //private int cuatrimestre_cuatrimestre_id;
    //private int cuatrimestre;
    private List lista = new ArrayList();
    
    public String cargarPagina() {
        return SUCCESS;
    }
    
    public String consultas() {

        HistorialAcademicoDao dao = new HistorialAcademicoDao();
        setLista(dao.consultarTodo());

        if (!getLista().isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
    
    public String consultaAlumno() {
        HistorialAcademicoDao l = new HistorialAcademicoDao();
        setLista(l.consultarAlumno_Id(getAlumno_id()));
        System.out.println("la lista tiene: " + lista.size());

        if (!lista.isEmpty()) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    /**
     * @return the historial_alumno_id
     */
    public int getHistorial_alumno_id() {
        return historial_alumno_id;
    }

    /**
     * @return the alumno_id
     */
    public int getAlumno_id() {
        return alumno_id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the a_paterno
     */
    public String getA_paterno() {
        return a_paterno;
    }

    /**
     * @return the a_materno
     */
    public String getA_materno() {
        return a_materno;
    }

    /**
     * @return the periodo_id
     */
    public int getPeriodo_id() {
        return periodo_id;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @return the grupo_carrera_id
     */
    public int getGrupo_carrera_id() {
        return grupo_carrera_id;
    }

    /**
     * @return the grado
     */
    public int getGrado() {
        return grado;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @return the carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * @return the cuatrimestre_cuatrimestre_id
     */
//    public int getCuatrimestre_cuatrimestre_id() {
//        return cuatrimestre_cuatrimestre_id;
//    }
//
//    /**
//     * @return the cuatrimestre
//     */
//    public int getCuatrimestre() {
//        return cuatrimestre;
//    }

    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param historial_alumno_id the historial_alumno_id to set
     */
    public void setHistorial_alumno_id(int historial_alumno_id) {
        this.historial_alumno_id = historial_alumno_id;
    }

    /**
     * @param alumno_id the alumno_id to set
     */
    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param a_paterno the a_paterno to set
     */
    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    /**
     * @param a_materno the a_materno to set
     */
    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
    }

    /**
     * @param periodo_id the periodo_id to set
     */
    public void setPeriodo_id(int periodo_id) {
        this.periodo_id = periodo_id;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @param grupo_carrera_id the grupo_carrera_id to set
     */
    public void setGrupo_carrera_id(int grupo_carrera_id) {
        this.grupo_carrera_id = grupo_carrera_id;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(int grado) {
        this.grado = grado;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * @param cuatrimestre_cuatrimestre_id the cuatrimestre_cuatrimestre_id to set
     */
//    public void setCuatrimestre_cuatrimestre_id(int cuatrimestre_cuatrimestre_id) {
//        this.cuatrimestre_cuatrimestre_id = cuatrimestre_cuatrimestre_id;
//    }
//
//    /**
//     * @param cuatrimestre the cuatrimestre to set
//     */
//    public void setCuatrimestre(int cuatrimestre) {
//        this.cuatrimestre = cuatrimestre;
//    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }
}
