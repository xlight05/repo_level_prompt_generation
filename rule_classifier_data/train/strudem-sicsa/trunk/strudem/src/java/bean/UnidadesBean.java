/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.UnidadesDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TOSHIBA
 */
public class UnidadesBean extends ActionSupport {
    private int unidades_id;
    private String fecha_inicio;
    private int parcial;
    private int materia_id;
    private List lista = new ArrayList();

    /**
     * @return the unidades_id
     */
    public int getUnidades_id() {
        return unidades_id;
    }

    /**
     * @param unidades_id the unidades_id to set
     */
    public void setUnidades_id(int unidades_id) {
        this.unidades_id = unidades_id;
    }

    /**
     * @return the fecha_inicio
     */
    public String getFecha_inicio() {
        return fecha_inicio;
    }

    /**
     * @param fecha_inicio the fecha_inicio to set
     */
    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    /**
     * @return the parcial
     */
    public int getParcial() {
        return parcial;
    }

    /**
     * @param parcial the parcial to set
     */
    public void setParcial(int parcial) {
        this.parcial = parcial;
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

    
    
     
     
    public String consultas(){
        UnidadesDao dao = new UnidadesDao();
        setLista(dao.consultaUnidades());
        
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
