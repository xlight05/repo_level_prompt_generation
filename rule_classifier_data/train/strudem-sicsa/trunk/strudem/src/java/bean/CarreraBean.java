/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.CarreraDao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TOSHIBA
 */
public class CarreraBean extends ActionSupport{
    private int carrera_id;
    private String nombre;
     private List lista = new ArrayList();

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

    
    public String consultas(){
        CarreraDao dao = new CarreraDao();
        setLista(dao.consultaCarrera());
        
        if(!getLista().isEmpty()){
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
    
    
    
    
}
