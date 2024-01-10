/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.FormatoAsistenciaDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio Zarate Barrera
 */
public class FormatoAsistencia extends ActionSupport {
    //Datos de Encabezado
    private int lista_asistencia_id;
    private int profesor_id; //id para obtener lista de alumnos
    private int grupo_id;   //id para obtener lista de alumnos
    private int periodo_id;
    private String carrera;
    private String materia;
    private String profesor;
    private String periodo;
    private int cuatrimestre;
    private String grupo;
    private int horas_por_semana;
    private List<FormatoAsistencia> encabezado = new ArrayList();
    //Datos de Meses
    private String mes;
    private List meses;
    //Datos de Unidades
    private String unidad;
    private List unidades;
    //Datos de dias de clase
    private int dia;
    private List dias = new ArrayList();
    //Datos de alumnos
    private int alumno_id;
    private String nombre, a_paterno, a_materno;
    private List alumnos;

    //Metodo para mostrar unicamente el formato de lista de asistencia de una lista_asistencia seleccionada
    public String listaAsistenciaVacia() {
        FormatoAsistenciaDao dao = new FormatoAsistenciaDao();
        setEncabezado(dao.consultarListaAsistencia(getLista_asistencia_id()));
        System.out.println("***El encabezado tiene: " + encabezado.size());
        setMeses(meses);
        setUnidades(unidades);
        setDias(dao.diasDeClase());
        FormatoAsistencia b = (FormatoAsistencia) getEncabezado().get(0);
       
        setAlumnos(dao.consultarAlumnosHistorial(b.getPeriodo_id(), b.getGrupo_id(), b.getProfesor_id()));
        return SUCCESS;
    }

//Metodo para llenar el formarto de lista de asistencia con las asistencias de una lista_asistencia seleccionada
    public String generarListaAsistencia() {

        return SUCCESS;
    }

    /**
     * @return the carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @return the profesor
     */
    public String getProfesor() {
        return profesor;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @return the cuatrimestre
     */
    public int getCuatrimestre() {
        return cuatrimestre;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @return the horas_por_semana
     */
    public int getHoras_por_semana() {
        return horas_por_semana;
    }

    /**
     * @return the encabezado
     */
    public List getEncabezado() {
        return encabezado;
    }

    /**
     * @return the mes
     */
    public String getMes() {
        return mes;
    }

    /**
     * @return the meses
     */
    public List getMeses() {
        return meses;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @return the unidades
     */
    public List getUnidades() {
        return unidades;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @return the dias
     */
    public List getDias() {
        return dias;
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
     * @return the alumnos
     */
    public List getAlumnos() {
        return alumnos;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @param cuatrimestre the cuatrimestre to set
     */
    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @param horas_por_semana the horas_por_semana to set
     */
    public void setHoras_por_semana(int horas_por_semana) {
        this.horas_por_semana = horas_por_semana;
    }

    /**
     * @param encabezado the encabezado to set
     */
    public void setEncabezado(List encabezado) {
        this.encabezado = encabezado;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    /**
     * @param meses the meses to set
     */
    public void setMeses(List meses) {
        this.meses = meses;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @param unidades the unidades to set
     */
    public void setUnidades(List unidades) {
        this.unidades = unidades;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(List dias) {
        this.dias = dias;
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
     * @param alumnos the alumnos to set
     */
    public void setAlumnos(List alumnos) {
        this.alumnos = alumnos;
    }

    /**
     * @return the lista_asistencia_id
     */
    public int getLista_asistencia_id() {
        return lista_asistencia_id;
    }

    /**
     * @param lista_asistencia_id the lista_asistencia_id to set
     */
    public void setLista_asistencia_id(int lista_asistencia_id) {
        this.lista_asistencia_id = lista_asistencia_id;
    }

    /**
     * @return the carrera_id
     */
    public int getProfesor_id() {
        return profesor_id;
    }

    /**
     * @return the grupo_id
     */
    public int getGrupo_id() {
        return grupo_id;
    }

    /**
     * @param carrera_id the carrera_id to set
     */
    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    /**
     * @param grupo_id the grupo_id to set
     */
    public void setGrupo_id(int grupo_id) {
        this.grupo_id = grupo_id;
    }

    /**
     * @return the periodo_id
     */
    public int getPeriodo_id() {
        return periodo_id;
    }

    /**
     * @param periodo_id the periodo_id to set
     */
    public void setPeriodo_id(int periodo_id) {
        this.periodo_id = periodo_id;
    }
}
