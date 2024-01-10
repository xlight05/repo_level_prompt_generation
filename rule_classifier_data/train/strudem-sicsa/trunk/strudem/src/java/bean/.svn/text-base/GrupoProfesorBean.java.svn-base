/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.GrupoProfesorDao;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Gilberto Tenorio
 */
public class GrupoProfesorBean extends ActionSupport{
    private int grupo_id;
    private int grado;
    private String grupo;
    private List lista;
    private Map sesion;

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
    
    public String grupoProfesor(){
        GrupoProfesorDao dao = new GrupoProfesorDao();
        UsuarioBean usuario;
        
        
        setLista(dao.grupoProfesorLista());
        
        if(!lista.isEmpty()){
            return ERROR;
        } else {
            return SUCCESS;
        }
    }


    /**
     * @return the sesion
     */
    public Map getSesion() {
        return sesion;
    }

    /**
     * @param sesion the sesion to set
     */
    public void setSesion(Map sesion) {
        this.sesion = sesion;
    }
    
}
