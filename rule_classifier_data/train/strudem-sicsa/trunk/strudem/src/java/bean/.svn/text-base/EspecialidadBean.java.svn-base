/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.EspecialidadDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TOSHIBA
 */
public class EspecialidadBean extends ActionSupport{
    private int especialidad_id;
    private String nombre;
    private int carrera_id;
    private String carrera;
    private List lista = new ArrayList();

    /**
     * @return the especialidad_id
     */
    public int getEspecialidad_id() {
        return especialidad_id;
    }

    /**
     * @param especialidad_id the especialidad_id to set
     */
    public void setEspecialidad_id(int especialidad_id) {
        this.especialidad_id = especialidad_id;
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
     * @return the carrera_id
     */
    public int getCarrera_id() {
        return carrera_id;
    }

    /**
     * @param carrera_id the carrera_id to set
     */
    public void setCarrera_id(int carrera_id) {
        this.carrera_id = carrera_id;
    }
    
    public String consultas(){
        EspecialidadDao dao = new EspecialidadDao();
        setLista(dao.consultaEspecialidad());
        
        if(!getLista().isEmpty()){
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    
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
     * @return the carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
}
