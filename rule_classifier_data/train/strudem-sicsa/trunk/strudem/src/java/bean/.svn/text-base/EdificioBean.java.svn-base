/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.EdificioDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mily
 */
public class EdificioBean extends ActionSupport {
    
    private int edificio_id;
    private String descripcion;
    private List lista = new ArrayList();

    /**
     * @return the edificio_id
     */
    public int getEdificio_id() {
        return edificio_id;
    }

    /**
     * @param edificio_id the edificio_id to set
     */
    public void setEdificio_id(int edificio_id) {
        this.edificio_id = edificio_id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     public String consultas(){
        EdificioDao dao = new EdificioDao();
        setLista(dao.consultaEdificio());
        
        if(!getLista().isEmpty()){
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
