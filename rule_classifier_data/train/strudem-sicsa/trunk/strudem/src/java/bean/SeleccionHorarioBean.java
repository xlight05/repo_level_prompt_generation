/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.opensymphony.xwork2.ActionSupport;
import dao.SeleccionHorarioDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gilberto Tenorio
 */
public class SeleccionHorarioBean extends ActionSupport{
    private int profesor_id;
    private String nombre;
    private String a_paterno;
    private String a_materno;
    private List lista = new ArrayList();

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
    
    public String seleccionHorario(){
        SeleccionHorarioDao dao = new SeleccionHorarioDao();
        setLista(dao.seleccionHorario());
        if(!lista.isEmpty()){
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
