/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.CuatrimestreDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mily
 */
public class CuatrimestreBean extends ActionSupport {
    private int cuatrimestre_id;
    private int nivel;
    private List lista = new ArrayList();

    /**
     * @return the cuatrimestre_id
     */
    public int getCuatrimestre_id() {
        return cuatrimestre_id;
    }

    /**
     * @param cuatrimestre_id the cuatrimestre_id to set
     */
    public void setCuatrimestre_id(int cuatrimestre_id) {
        this.cuatrimestre_id = cuatrimestre_id;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
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
        CuatrimestreDao dao = new CuatrimestreDao();
        setLista(dao.consultaCuatrimestre());
        
        if(!getLista().isEmpty()){
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
