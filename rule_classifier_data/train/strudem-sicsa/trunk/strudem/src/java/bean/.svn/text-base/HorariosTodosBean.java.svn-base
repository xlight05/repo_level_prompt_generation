/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.HorariosTodosDao;
import dao.MiHorarioDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gilberto Tenorio
 */
public class HorariosTodosBean extends ActionSupport{
    
    
    
    private int clase_id;
    private String nombre;
    private int horario_id;
    private int ubicacion_id;
    private int materia_id;
    private int grupo_id;
    private String dia;
    private String grupo;
    private int grado;
    private String aula;
    private String edificio;
    private String hora_inicio;
    private String hora_fin;
    private List lista = new ArrayList();
    private List lista2 = new ArrayList();
    private int profesor_id;
    private String nombreProfesor;
    private String a_paterno;
    private String a_materno;
    

    /**
     * @return the clase_id
     */
    public int getClase_id() {
        return clase_id;
    }

    /**
     * @param clase_id the clase_id to set
     */
    public void setClase_id(int clase_id) {
        this.clase_id = clase_id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the horario_id
     */
    public int getHorario_id() {
        return horario_id;
    }

    /**
     * @param horario_id the horario_id to set
     */
    public void setHorario_id(int horario_id) {
        this.horario_id = horario_id;
    }

    /**
     * @return the ubicacion_id
     */
    public int getUbicacion_id() {
        return ubicacion_id;
    }

    /**
     * @param ubicacion_id the ubicacion_id to set
     */
    public void setUbicacion_id(int ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }

    /**
     * @return the materia_id
     */
    public int getMateria_id() {
        return materia_id;
    }

    /**
     * @param materia_id the materia_id to set
     */
    public void setMateria_id(int materia_id) {
        this.materia_id = materia_id;
    }

    /**
     * @return the grupo_id
     */
    public int getGrupo_id() {
        return grupo_id;
    }

    /**
     * @param grupo_id the grupo_id to set
     */
    public void setGrupo_id(int grupo_id) {
        this.grupo_id = grupo_id;
    }

    /**
     * @return the dia
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia = dia;
    }


    
    public String horariosTodos(){
        
        HorariosTodosDao dao = new HorariosTodosDao();
        
        
        setLista(dao.horariosTodosLista(getProfesor_id()));
        setLista2(dao.profesoresLista(getProfesor_id()));
        
        if(getProfesor_id() != 0){
            
            return SUCCESS;
        } else {
            return ERROR;
        }
    }



    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the grado
     */
    public int getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(int grado) {
        this.grado = grado;
    }

    /**
     * @return the aula
     */
    public String getAula() {
        return aula;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(String aula) {
        this.aula = aula;
    }

    /**
     * @return the edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * @param edificio the edificio to set
     */
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    /**
     * @return the lista2
     */
    public List getLista2() {
        return lista2;
    }

    /**
     * @param lista2 the lista2 to set
     */
    public void setLista2(List lista2) {
        this.lista2 = lista2;
    }

    /**
     * @return the hora_inicio
     */
    public String getHora_inicio() {
        return hora_inicio;
    }

    /**
     * @param hora_inicio the hora_inicio to set
     */
    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    /**
     * @return the hora_fin
     */
    public String getHora_fin() {
        return hora_fin;
    }

    /**
     * @param hora_fin the hora_fin to set
     */
    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    /**
     * @return the profesor_id
     */
    public int getProfesor_id() {
        return profesor_id;
    }

    /**
     * @param profesor_id the profesor_id to set
     */
    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    /**
     * @return the nombreProfesor
     */
    public String getNombreProfesor() {
        return nombreProfesor;
    }

    /**
     * @param nombreProfesor the nombreProfesor to set
     */
    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    /**
     * @return the a_paterno
     */
    public String getA_paterno() {
        return a_paterno;
    }

    /**
     * @param a_paterno the a_paterno to set
     */
    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    /**
     * @return the a_materno
     */
    public String getA_materno() {
        return a_materno;
    }

    /**
     * @param a_materno the a_materno to set
     */
    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
    }

}
