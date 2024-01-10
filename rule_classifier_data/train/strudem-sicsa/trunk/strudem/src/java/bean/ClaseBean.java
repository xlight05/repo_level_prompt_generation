/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.*;
import java.util.*;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 *
 * @author Mauricio Zarate Barrera
 */
@ParentPackage(value = "admin")
public class ClaseBean extends ActionSupport {
    //Atributos de tabla clase para interpretación e inserción

    private int clase_id;
    private String nombre;
    private int horario_id;
    private int ubicacion_id;
    private int materia_id;
    private int grupo_id;
    private Date hora;
    private String dia;
    //Importadas para mostrar claseLista
    private String profesor;    //pertenece a horario
    private String materia;
    private String ubicacion;   //edificio y aula DOUBLE
    private String grupo;       //grado y grupo DOUBLE
    //Identificadores para evaluar resultados de selectores primarios
    private int profesor_id;
    private int periodo_id;
    private int edificio_id;
    private int aula_id;
    private int cuatrimestre_id;
    private int especialidad_id;
    private int carrera_id;
    //Listas para agregar clase primarios antecedentes
    private Map<Integer, String> profesores;
    private Map<Integer, String> periodos;
    private Map<Integer, String> edificios;
    private Map<Integer, String> aulas;
    private Map<Integer, String> cuatrimestres;
    private Map<Integer, String> especialidades;
    private Map<Integer, String> carreras;
    private Map<Integer, String> grupos;
    //Lista de consulta
    private List lista = new ArrayList();
    private List reloadList = new ArrayList();
    /*
     * Orden de dependencia de atributos de la lista 1.1:profesor_id
     * 1.2:periodo_id 1:S:horarios_id 2.1:edificio_id 2.2:aula_id
     * 2:S:ubicacion_id 3.1:cuatrimestre_id 3:S:materia_id 4.1.1:especialidad_id
     * 4.1.2:carrera_id 4.2:grado 4:S:grupo_id 5.T:Hora_inicio 6.T:Hora_fin
     * 7.T:dia
     */

    @Actions({
        @Action(value = "/claseNuevo",
        results = {
            @Result(name = "success", type = "json")
        })
    })
    public String execute() {
        setProfesores(new HashMap<Integer, String>());
        setPeriodos(new HashMap<Integer, String>());
        setEdificios(new HashMap<Integer, String>());
        setAulas(new HashMap<Integer, String>());
        setCuatrimestres(new HashMap<Integer, String>());
        setEspecialidades(new HashMap<Integer, String>());
        setCarreras(new HashMap<Integer, String>());
        setGrupos(new HashMap<Integer, String>());

        /*
         * Convertidores de List a HashMap
         */

        //profesores
        ProfesorDao dao1 = new ProfesorDao();
        List lista1 = dao1.consultarTodo();
        ProfesorBean bean1;
        for (int i = 0; i < lista1.size(); i++) {
            bean1 = (ProfesorBean) lista1.get(i);
            profesores.put(bean1.getProfesor_id(), bean1.getNombre() + " " + bean1.getA_paterno() + " " + bean1.getA_materno());
        }

        //periodos
        PeriodoDao dao2 = new PeriodoDao();
        List lista2 = dao2.consultarTodo();
        PeriodoBean bean2;
        for (int i = 0; i < lista2.size(); i++) {
            bean2 = (PeriodoBean) lista2.get(i);
            periodos.put(bean2.getPeriodo_id(), bean2.getDescripcion());
        }

        //edificios
        EdificioDao dao3 = new EdificioDao();
        List lista3 = dao3.consultaEdificio();
        EdificioBean bean3;
        for (int i = 0; i < lista3.size(); i++) {
            bean3 = (EdificioBean) lista3.get(i);
            edificios.put(bean3.getEdificio_id(), bean3.getDescripcion());
        }
        //aulas
        AulaDao dao4 = new AulaDao();
        List lista4 = dao4.consultarTodo();
        AulaBean bean4;
        for (int i = 0; i < lista4.size(); i++) {
            bean4 = (AulaBean) lista4.get(i);
            aulas.put(bean4.getAula_id(), bean4.getDescripcion());
        }

        //cuatrimestres
        CuatrimestreDao dao5 = new CuatrimestreDao();
        List lista5 = dao5.consultaCuatrimestre();
        CuatrimestreBean bean5;
        for (int i = 0; i < lista5.size(); i++) {
            bean5 = (CuatrimestreBean) lista5.get(i);
            cuatrimestres.put(bean5.getCuatrimestre_id(), String.valueOf(bean5.getNivel()));
        }

        //especialidades
        EspecialidadDao dao6 = new EspecialidadDao();
        List lista6 = dao6.consultaEspecialidad();
        EspecialidadBean bean6;
        for (int i = 0; i < lista6.size(); i++) {
            bean6 = (EspecialidadBean) lista6.get(i);
            especialidades.put(bean6.getEspecialidad_id(), String.valueOf(bean6.getNombre()));
        }

        //carreras
        CarreraDao dao7 = new CarreraDao();
        List lista7 = dao7.consultaCarrera();
        CarreraBean bean7;
        for (int i = 0; i < lista7.size(); i++) {
            bean7 = (CarreraBean) lista7.get(i);
            carreras.put(bean7.getCarrera_id(), String.valueOf(bean7.getNombre()));
        }

        //grupos
        GruposDao dao8 = new GruposDao();
        List lista8 = dao8.consultaGrupos();
        GruposBean bean8;
        for (int i = 0; i < lista8.size(); i++) {
            bean8 = (GruposBean) lista8.get(i);
            grupos.put(bean8.getGrupo_id(), String.valueOf(bean8.getGrado() + " " + bean8.getGrupo()));
        }
        setReloadList(new ArrayList<String>());
        System.err.println(lista1.size());
        System.out.println(grupos);
        return SUCCESS;
    }

    public String getJSON() {
        return execute();
    }

    /**
     * @return the clase_id
     */
    public int getClase_id() {
        return clase_id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the horario_id
     */
    public int getHorario_id() {
        return horario_id;
    }

    /**
     * @return the ubicacion_id
     */
    public int getUbicacion_id() {
        return ubicacion_id;
    }

    /**
     * @return the materia_id
     */
    public int getMateria_id() {
        return materia_id;
    }

    /**
     * @return the grupo_id
     */
    public int getGrupo_id() {
        return grupo_id;
    }

    /**
     * @return the hora
     */
    public Date getHora() {
        return hora;
    }

    /**
     * @return the dia
     */
    public String getDia() {
        return dia;
    }

    /**
     * @return the profesor
     */
    public String getProfesor() {
        return profesor;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @return the profesor_id
     */
    public int getProfesor_id() {
        return profesor_id;
    }

    /**
     * @return the periodo_id
     */
    public int getPeriodo_id() {
        return periodo_id;
    }

    /**
     * @return the edificio_id
     */
    public int getEdificio_id() {
        return edificio_id;
    }

    /**
     * @return the aula_id
     */
    public int getAula_id() {
        return aula_id;
    }

    /**
     * @return the cuatrimestre_id
     */
    public int getCuatrimestre_id() {
        return cuatrimestre_id;
    }

    /**
     * @return the especialidad_id
     */
    public int getEspecialidad_id() {
        return especialidad_id;
    }

    /**
     * @return the carrera_id
     */
    public int getCarrera_id() {
        return carrera_id;
    }

    /**
     * @return the profesores
     */
    public Map<Integer, String> getProfesores() {
        return profesores;
    }

    /**
     * @return the periodos
     */
    public Map<Integer, String> getPeriodos() {
        return periodos;
    }

    /**
     * @return the edificios
     */
    public Map<Integer, String> getEdificios() {
        return edificios;
    }

    /**
     * @return the aulas
     */
    public Map<Integer, String> getAulas() {
        return aulas;
    }

    /**
     * @return the cuatrimestres
     */
    public Map<Integer, String> getCuatrimestres() {
        return cuatrimestres;
    }

    /**
     * @return the especialidades
     */
    public Map<Integer, String> getEspecialidades() {
        return especialidades;
    }

    /**
     * @return the carreras
     */
    public Map<Integer, String> getCarreras() {
        return carreras;
    }

    /**
     * @return the grupos
     */
    public Map<Integer, String> getGrupos() {
        return grupos;
    }

    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @return the reloadList
     */
    public List getReloadList() {
        return reloadList;
    }

    /**
     * @param clase_id the clase_id to set
     */
    public void setClase_id(int clase_id) {
        this.clase_id = clase_id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param horario_id the horario_id to set
     */
    public void setHorario_id(int horario_id) {
        this.horario_id = horario_id;
    }

    /**
     * @param ubicacion_id the ubicacion_id to set
     */
    public void setUbicacion_id(int ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }

    /**
     * @param materia_id the materia_id to set
     */
    public void setMateria_id(int materia_id) {
        this.materia_id = materia_id;
    }

    /**
     * @param grupo_id the grupo_id to set
     */
    public void setGrupo_id(int grupo_id) {
        this.grupo_id = grupo_id;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Date hora) {
        this.hora = hora;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @param profesor_id the profesor_id to set
     */
    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    /**
     * @param periodo_id the periodo_id to set
     */
    public void setPeriodo_id(int periodo_id) {
        this.periodo_id = periodo_id;
    }

    /**
     * @param edificio_id the edificio_id to set
     */
    public void setEdificio_id(int edificio_id) {
        this.edificio_id = edificio_id;
    }

    /**
     * @param aula_id the aula_id to set
     */
    public void setAula_id(int aula_id) {
        this.aula_id = aula_id;
    }

    /**
     * @param cuatrimestre_id the cuatrimestre_id to set
     */
    public void setCuatrimestre_id(int cuatrimestre_id) {
        this.cuatrimestre_id = cuatrimestre_id;
    }

    /**
     * @param especialidad_id the especialidad_id to set
     */
    public void setEspecialidad_id(int especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    /**
     * @param carrera_id the carrera_id to set
     */
    public void setCarrera_id(int carrera_id) {
        this.carrera_id = carrera_id;
    }

    /**
     * @param profesores the profesores to set
     */
    public void setProfesores(Map<Integer, String> profesores) {
        this.profesores = profesores;
    }

    /**
     * @param periodos the periodos to set
     */
    public void setPeriodos(Map<Integer, String> periodos) {
        this.periodos = periodos;
    }

    /**
     * @param edificios the edificios to set
     */
    public void setEdificios(Map<Integer, String> edificios) {
        this.edificios = edificios;
    }

    /**
     * @param aulas the aulas to set
     */
    public void setAulas(Map<Integer, String> aulas) {
        this.aulas = aulas;
    }

    /**
     * @param cuatrimestres the cuatrimestres to set
     */
    public void setCuatrimestres(Map<Integer, String> cuatrimestres) {
        this.cuatrimestres = cuatrimestres;
    }

    /**
     * @param especialidades the especialidades to set
     */
    public void setEspecialidades(Map<Integer, String> especialidades) {
        this.especialidades = especialidades;
    }

    /**
     * @param carreras the carreras to set
     */
    public void setCarreras(Map<Integer, String> carreras) {
        this.carreras = carreras;
    }

    /**
     * @param grupos the grupos to set
     */
    public void setGrupos(Map<Integer, String> grupos) {
        this.grupos = grupos;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }

    /**
     * @param reloadList the reloadList to set
     */
    public void setReloadList(List reloadList) {
        this.reloadList = reloadList;
    }
}
